package asset;

import java.io.Serializable;

import tcpIp.SocketComm;

public class GrandOrder implements Classifier,Serializable{

	private Property a;
	private Property b;


	public GrandOrder(Property a,Property b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void readFunc(byte header, SocketComm sc) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getClassName() {
		return "GrandOrder";
	}


}
