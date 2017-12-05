package server;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import asset.IndoorLocation;
import asset.SlaveList;
import asset.TcpipDeviceProperty;
import gui.MainWindow;
import tcpIp.SocketServer;
import udpIp.Constants;
import udpIp.UdpRecvThread;
import udpIp.UdpSendThread;

public class MainApplication {

	public static SocketServer ts;
	public static SocketServer updateServer;
	public static UdpSendThread udpSend;



	public static void main(String[] args) {
		String addr;
		try {

			MainWindow mw = new MainWindow();
			mw.setVisible(true);

			//各種リストの取得
//			SlaveList.loadList();

			//write
			ArrayList<String> funcDisplay = new ArrayList<>();
			ArrayList<String> funcCamera = new ArrayList<>();
			ArrayList<String> funcClient = new ArrayList<>();


			funcDisplay.add("file send");
			funcClient.add("greeting");

			String windowsIP = "192.168.1.106";
			String macIP = "192.168.1.108";
			String tangoIP = "";
			String androidIP = "";

			int windowsPort = 12345;
			int macPort = 10101;
			int tangoPort = 0;
			int androidPort = 0;

			int cameraCla = 2;
			int screenCla = 1001;
			int clientCla = 5;

			//camera1(USB)
			SlaveList.add(new TcpipDeviceProperty(new IndoorLocation(4307,2126,1745),macIP,macPort,"cameraUSB",funcCamera,cameraCla));
			//camera2(inner)
//			SlaveList.add(new TcpipDeviceProperty(new IndoorLocation(5750,3200,1200),windowsIP,windowsPort,"cameraInner",funcCamera,cameraCla));
			//display1(big)
			SlaveList.add(new TcpipDeviceProperty(new IndoorLocation(4449,2513,1737),windowsIP,windowsPort,"bigDisplay",funcDisplay,screenCla));
			//display2(pc)
//			SlaveList.add(new TcpipDeviceProperty(new IndoorLocation(5750,3200,1200),macIP,macPort,"PCdisplay",funcDisplay,screenCla));

			//client1(tango)
			SlaveList.add(new TcpipDeviceProperty(new IndoorLocation(5750,3200,1200),tangoIP,tangoPort,"8001",funcClient,clientCla));
			//client2(android)
//			SlaveList.add(new TcpipDeviceProperty(new IndoorLocation(5750,3200,1200),androidIP,androidPort,"8002",funcClient,clientCla));

			//通信用TCPIPソケットサーバの確立
			addr = "localhost";
			ts = new SocketServer(addr, 11111);
			Thread serverThread = new Thread(ts);
			serverThread.start();

			updateServer = new SocketServer(addr, 11112);
			Thread listUpdateThread = new Thread(updateServer);
			listUpdateThread.start();


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
		MainApplication.updateServer.asyncSend(SlaveList.getInstance(), (byte)0);
	}
}

class BeaconSender extends TimerTask{
	@Override
	public void run() {
		MainApplication.udpSend.udpSend(Constants.RECV_BEACON_PACKET);
	}
}