package asset;

import tcpIp.SocketComm;

public class ContentProperty extends Property {

	private DeviceProperty parent;

	@Override
	public void readFunc(byte header, SocketComm sc) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getClassName() {
		return "ContentProperty";
	}

}
