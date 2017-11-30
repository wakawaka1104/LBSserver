package udpIp;

public class Constants {
	public static final int SEND_PORT = 50052;
	public static final int SERVER_RECV_PORT = 50050;
	public static final int UWB_RECV_PORT = 50051;
	public static final int BUF_SIZE = 1024;

	public static final String SEND_QUERY_PACKET = "CMDQH";
	public static final String SEND_BEACON_PACKET = "CMDBK";
	public static final String SEND_LOCATION_CONFIGURATION_PACKET = "CMDSA";
	public static final String SEND_TIME_CONFIGURATION_PACKET = "CMDST";
	public static final String SEND_SERVERIP_CONFIGURATION_PAKCET = "CMDSC";
	public static final String SEND_OFFSET_CONFIGURATION_PACKET = "CMDSO";
	public static final String SEND_POWER_CONFIGURATION_PACKET = "CMDSW";
	public static final String SEND_RECEIVE_CIRCUIT_CONFIGURATION_PACKET = "CMDSR";


	public static final String RECV_INFO_PACKET = "ACKQH";
	public static final String RECV_BEACON_ACK_PACKET = "ACKBK";
	public static final String RECV_BEACON_PACKET = "BKN";
	public static final String RECV_LOCATION_CONFIGURATION_ACK_PACKET = "ACKSA";
	public static final String RECV_TIME_CONFIGURATION_ACK_PACKET = "ACKST";
	public static final String RECV_SERVERIP_CONFIGURATION_ACK_PACKET = "ACKSC";
	public static final String RECV_OFFSET_CONFIGURATION_ACK_PACKET = "ACKSO";
	public static final String RECV_POWER_CONFIGURATION_ACK_PACKET = "ACKSW";
	public static final String RECV_RECEIVE_CIRCUIT_CONFIGURATION_ACK_PACKET = "ACKSR";
	public static final String RECV_DISTANCE_PACKET = "DIS";
	public static final String RECV_POSITION_PACKET = "POS";

}
