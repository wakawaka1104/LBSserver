package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import asset.Classifier;
import asset.IndoorLocation;
import asset.Property;
import asset.SlaveList;

public class TestServer implements Runnable {

	private int port;
	private Selector selector;

	private static byte[] sendData;
	private static boolean sendFlag = false;

	public TestServer(InetAddress addr, int port) {
		this.port = port;
	}

	public void run() {
		open();
	}

	public void open() {
		ServerSocketChannel serverChannel = null;
		try {
			selector = Selector.open();
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(new InetSocketAddress(port));
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("TestServerが起動しました(port=" + serverChannel.socket().getLocalPort() + ":[connect]");

			while (selector.select() > 0) {
				for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext();) {
					SelectionKey key = (SelectionKey) it.next();
					it.remove();
					if (key.isAcceptable()) {
						doAccept((ServerSocketChannel) key.channel());
					} else if (key.isReadable()) {
						doRead((SocketChannel) key.channel());
					} else if (key.isWritable() && sendFlag){
						doWrite((SocketChannel) key.channel());
					}
				}
			}
		} catch (IOException e) {
			System.err.println("TestServer:open()[error]");
			e.printStackTrace();
		}
	}

	private void doAccept(ServerSocketChannel serverChannel) {

		try {
			SocketChannel channel = serverChannel.accept();
			String remoteAddress = channel.socket().getRemoteSocketAddress().toString();
			System.out.println("[server]:" + remoteAddress + ":[connect]");
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
		} catch (IOException e) {
			System.err.println("TestServer:doAccept()[error]");
			e.printStackTrace();
		}
	}

	private void doRead(SocketChannel channel) {
		ArrayList<ByteBuffer> bufferList = new ArrayList<ByteBuffer>();
		bufferList.add(ByteBuffer.allocate(Constant.BUF_SIZE));
		byte header = 0x00;
		Charset charset = Charset.forName("UTF-8");
		String remoteAddress = channel.socket().getRemoteSocketAddress().toString();

		int bufSize = 0;

		try {
			for (int index = 0;; index++) {
				int readSize = channel.read(bufferList.get(index));
				if (readSize < 0) {
					return;
				}
				bufferList.get(index).flip();
				// パケット先頭1バイトはClassifier
				if (index == 0) {
					header = bufferList.get(index).get();
				}


				/////////////////////////debug用/////////////////////////////////
				System.out
						.println("[server]:" + remoteAddress + ":" + charset.decode(bufferList.get(index)).toString());
				bufferList.get(index).flip();
				if(index==0){
					bufferList.get(index).position(1);
				}
				//////////////////////////////////////////////////////////////////


				if (readSize == Constant.BUF_SIZE) {
					// BUF_SIZEを超えるデータが現れたとき、格納するByteBufferを追加
					bufferList.add(ByteBuffer.allocate(Constant.BUF_SIZE));
					continue;
				}
				break;
			}

			for (Iterator<ByteBuffer> it = bufferList.iterator(); it.hasNext();) {
				bufSize += it.next().limit();
			}
			// -1はheader分のバイトを加味しない、という意味
			bufSize -= 1;
			System.out.println("bufSize:" + bufSize + "\n");
			ByteBuffer contents = ByteBuffer.allocate(bufSize);
			for(Iterator<ByteBuffer> it = bufferList.iterator(); it.hasNext();){
				contents.put(it.next());
			}
			contents.flip();

			/////////////////////////debug用//////////////////////
			System.out
			.println("[server]:" + remoteAddress + ":" + charset.decode(contents).toString());
			contents.flip();
			/////////////////////////////////////////////////////

			Classifier cl = (Classifier) deserialize(contents);
			cl.readFunc(header);

		} catch (

		IOException e) {
			System.err.println("TestServer:doRead()[error]");
			e.printStackTrace();
		} finally {
			System.out.println("[server]:" + remoteAddress + ":[disconnect]");
			try {
				channel.close();
			} catch (IOException e) {
				System.err.println("TestServer:doRead():close()[error]");
				e.printStackTrace();
			}
		}
	}

	private void doWrite(SocketChannel channel){
		ByteBuffer writeBuffer = ByteBuffer.allocate(sendData.length);
		writeBuffer.put(sendData);
		if(writeBuffer.hasRemaining()){
			try {
				channel.write(writeBuffer);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}finally{
				writeBuffer.clear();
				sendFlag = false;
			}

		}
	}

	public static void doSend(byte[] data){
		sendData = data;
		sendFlag = true;
	}

	public static byte[] serialize(Classifier cl) {
		try {
			byte[] tmp;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutput oo = new ObjectOutputStream(baos);
			oo.writeObject(cl);
			tmp = baos.toByteArray();
			return tmp;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;

	}

	public Object deserialize(ByteBuffer buf){
		try {
			byte[] bufArray;
			bufArray = buf.array();
			ByteArrayInputStream bais = new ByteArrayInputStream(bufArray);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object tmp = ois.readObject();
			bais.close();
			ois.close();
			return tmp;
		} catch (IOException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}

	}


	//デバグ用main
	public static void main(String[] args){
		try {
			Property prop = new Property(new IndoorLocation(1, 1, 2),InetAddress.getLocalHost(),10101,"test");
			Property prop2 = new Property(new IndoorLocation(13, 11, 22),InetAddress.getLocalHost(),13101,"test2");
			byte[] bufArray;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutput oo = new ObjectOutputStream(baos);
			oo.writeObject(prop);
			bufArray = baos.toByteArray();
			ByteBuffer buf = ByteBuffer.allocate(bufArray.length);
			buf.put(bufArray);

			TestServer ts = new TestServer(InetAddress.getLocalHost(), 1010);
			Classifier cl = (Classifier)ts.deserialize(buf);
			cl.readFunc((byte)0);

			oo.flush();
			buf.clear();
			baos.reset();

			ObjectOutput oo2 = new ObjectOutputStream(baos);

			byte[] bufArray2;
			oo2.writeObject(prop2);
			bufArray2 = baos.toByteArray();
			ByteBuffer buf2 = ByteBuffer.allocate(bufArray2.length);
			buf2.put(bufArray2);

			Classifier cl2
			 = (Classifier)ts.deserialize(buf2);
			cl2.readFunc((byte)0);

			System.out.println(SlaveList.getInstance().toString());

			oo.flush();
			oo.close();
			oo2.flush();
			oo2.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
