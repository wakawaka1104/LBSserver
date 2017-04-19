package server;

import java.io.IOException;
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

public class TestServer implements Runnable {

	private int port;
	private Selector selector;

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
			channel.register(selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			System.err.println("TestServer:doAccept()[error]");
			e.printStackTrace();
		}
	}

	private void doRead(SocketChannel channel) {
		ArrayList<ByteBuffer> bufferList = new ArrayList<ByteBuffer>();
		bufferList.add(ByteBuffer.allocate(Constant.BUF_SIZE));
		Classifier classifier = null;
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
					classifier = Classifier.decodeType(bufferList.get(index).get());
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
			// -1はclassifier分のバイトを加味しない、という意味
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

			////////////////////////
			// ここでclassifierに基づいて、サーバーに何やらせるか分岐
			switch (classifier) {
			case Location:
				readLocation();
				return;
			case Property:
				readProperty();
				return;
			default:
				System.out.println("定義されていないclassifierです\n");
				return;
			}
			//////////////////////////

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

	private void readProperty() {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void readLocation() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
