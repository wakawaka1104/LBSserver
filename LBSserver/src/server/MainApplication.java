package server;

public class MainApplication {

	public static void main(String[] args) {
		String addr;
		try {
			addr = "localhost";
			SocketServer ts = new SocketServer(addr, 11111);
			Thread serverThread = new Thread(ts);
			serverThread.start();


		} catch (Exception e1) {
			System.err.println("aaa");
			e1.printStackTrace();
		}

	}

}
