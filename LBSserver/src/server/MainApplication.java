package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainApplication {

	public static void main(String[] args) {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			SocketServer ts = new SocketServer(addr, 11111);
			Thread serverThread = new Thread(ts);
			serverThread.start();


		} catch (UnknownHostException e1) {
			System.err.println("aaa");
			e1.printStackTrace();
		}

	}

}
