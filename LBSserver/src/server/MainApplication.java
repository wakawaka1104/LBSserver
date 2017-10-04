package server;

import java.util.Timer;
import java.util.TimerTask;

import asset.SlaveList;
import tcpIp.SocketServer;

public class MainApplication {

	public static SocketServer ts;

	public static void main(String[] args) {
		String addr;
		try {
			SlaveList.loadList();
			System.out.println(SlaveList.getInstance().toString());
			addr = "localhost";
			ts = new SocketServer(addr, 11111);
			Thread serverThread = new Thread(ts);
			serverThread.start();

			Timer timer = new Timer();
			timer.schedule(new ListUpdater(), 0, 100000);

		}catch (Exception e1) {
			System.err.println("aaa");
			e1.printStackTrace();
		}

	}
}

class ListUpdater extends TimerTask{
	@Override
	public void run() {
		MainApplication.ts.asyncSend(SlaveList.getInstance(), (byte)0);
	}
}