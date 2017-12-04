package udpIp;

import static udpIp.Constants.*;

import com.sun.jmx.snmp.Timestamp;

import asset.SlaveList;

public class UwbRecvPacketFunction {

	//受信パケット情報
	private String recv = "";
	//カンマ区切りsplit後データ
	private String[] data;


	public UwbRecvPacketFunction(String recv){
		this.recv = recv;
		data = this.recv.split(",");
	}

	public void readFunc(){
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		switch(data[0]){
		case RECV_INFO_PACKET:
			System.out.println("["+ ts.toString() +"]" + "固定機情報パケット");
			break;
		case RECV_BEACON_ACK_PACKET:
			System.out.println("["+ ts.toString() +"]" + "ビーコン応答パケット");
			break;
		case RECV_BEACON_PACKET:
			System.out.println("["+ ts.toString() +"]" + "固定機ビーコンパケット");
			break;
		case RECV_LOCATION_CONFIGURATION_ACK_PACKET:
			System.out.println("["+ ts.toString() +"]" + "設置位置設定応答パケット");
			break;
		case RECV_TIME_CONFIGURATION_ACK_PACKET:
			System.out.println("["+ ts.toString() +"]" + "時刻設定応答パケット");
			break;
		case RECV_SERVERIP_CONFIGURATION_ACK_PACKET:
			System.out.println("["+ ts.toString() +"]" + "サーバIP設定応答パケット");
			break;
		case RECV_OFFSET_CONFIGURATION_ACK_PACKET:
			System.out.println("["+ ts.toString() +"]" + "オフセット距離設定応答パケット");
			break;
		case RECV_POWER_CONFIGURATION_ACK_PACKET:
			System.out.println("["+ ts.toString() +"]" + "固定機UWB送信出力設定応答パケット");
			break;
		case RECV_RECEIVE_CIRCUIT_CONFIGURATION_ACK_PACKET:
			System.out.println("["+ ts.toString() +"]" + "固定機UWB受信設定応答パケット");
			break;
		case RECV_DISTANCE_PACKET:
//			System.out.println("["+ ts.toString() +"]" + "距離パケット");
			break;
		case RECV_POSITION_PACKET:
			System.out.println("["+ ts.toString() +"]" + "測位計算結果パケット");
			SlaveList.listUpdate(data);
			System.out.println("ClientList Updated");
			break;

		default:
			System.out.println("[" + ts.toString() + "]" + data[0] + ":フォーマット外テキスト");
			break;


		}
	}

}
