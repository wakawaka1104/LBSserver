package udpIp;

import static udpIp.Constants.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.LinkedList;

public class UdpSendThread implements Runnable{

	private String sendAddress = "";
	private int recvPort = -1;
	private LinkedList<String> sendData = new LinkedList<String>();

	public UdpSendThread(String address,int port) {
		this.sendAddress = address;
		this.recvPort = port;
	}

	@Override
	public void run() {

		try {
			//UDPチャネルの作成
			DatagramChannel channel = DatagramChannel.open();
			//Constants.SEND_PORTからUDPメッセージの送信
			channel.socket().bind(new InetSocketAddress(SEND_PORT));

			while(true){

				if(!sendData.isEmpty()){
					//送信処理
					ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
					buf.clear();
					buf.put(sendData.get(0).getBytes());
					buf.flip();

					int sendByte = channel.send(buf,new InetSocketAddress(sendAddress, recvPort));
					System.out.println("[" + sendData + "]:sendData");
					System.out.println(sendByte + "bytes sent");
					sendData.pop();

				}

				Thread.sleep(100);
				

			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void udpSend(String data){
		sendData.add(data);
	}

}
