package server;

import java.util.Timer;
import java.util.TimerTask;

import asset.SlaveList;
import tcpIp.SocketServer;
import udpIp.Constants;
import udpIp.UdpRecvThread;
import udpIp.UdpSendThread;

public class MainApplication {

	public static SocketServer ts;
	public static UdpSendThread udpSend;

	public static void main(String[] args) {
		String addr;
		try {
			//各種リストの取得
			SlaveList.loadList();
			//通信用TCPIPソケットサーバの確立
			addr = "localhost";
			ts = new SocketServer(addr, 11111);
			Thread serverThread = new Thread(ts);
			serverThread.start();

			//クライアントへのリスト更新スケジューラ
			Timer timer = new Timer();
			timer.schedule(new ListUpdater(), 0, 1*1000);

			Thread udpRecvThread = new Thread(new UdpRecvThread(Constants.SERVER_RECV_PORT));
			udpRecvThread.start();

			//UDPブロードキャスト送信スレッド
			udpSend = new UdpSendThread("255.255.255.255", Constants.UWB_RECV_PORT);
			Thread udpSendThread  = new Thread(udpSend);
			udpSendThread.start();

			//UWB仕様書より↓

			//ＰＣから各固定機に定期的にビーコンパケットを送る必要があります。
			//各固定機はこのビーコンパケットが約１０分間届かないと再起動を行います。
			//これはＷｉＦｉ通信が何らかの理由で断線したときに対処するためです。
			//ビーコンパケットを受信した固定機はビーコン応答パケットを返します。
			Timer beaconTimer = new Timer();
			//1分おきにスケジュール
			beaconTimer.schedule(new BeaconSender(),0,10*1000);


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

class BeaconSender extends TimerTask{
	@Override
	public void run() {
		MainApplication.udpSend.udpSend(Constants.RECV_BEACON_PACKET);
	}
}