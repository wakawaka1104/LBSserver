package udpIp;

import static udpIp.Constants.*;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpRecvThread implements Runnable{

	private String recv = "";
	private int port = -1;

	public UdpRecvThread(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			//UDPチャネル作成
			DatagramChannel channel = DatagramChannel.open();
			//port宛のUDPメッセージを受信
			channel.socket().bind(new InetSocketAddress(port));

			//受信用バッファ
			//溢れたデータは破棄
			ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
			buf.clear();

			while(true){
				//受信待機
				channel.receive(buf);
				//ByteBuffer->String
				buf.flip();
				byte[] data = new byte[buf.limit()];
				buf.get(data);
				recv = new String(data,"UTF-8");
				buf.clear();

				System.out.println("[" + recv + "]received");

				//無名インスタンス化、readFuncはswitch分岐
				new UwbRecvPacketFunction(recv).readFunc();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRecv(){
		return recv;
	}
}
