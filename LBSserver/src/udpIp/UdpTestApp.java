package udpIp;

public class UdpTestApp {

	public static void main(String[] args) {

		UdpRecvThread urt =  new UdpRecvThread(11111);
		UdpSendThread ust =  new UdpSendThread("localhost", 11111);
		Thread udpRecv = new Thread(urt);
		Thread udpSend = new Thread(ust);

		udpRecv.start();
		udpSend.start();

		ust.udpSend("testtesttesttesttest");
	}

}
