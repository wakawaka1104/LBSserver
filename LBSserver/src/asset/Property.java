package asset;

import java.io.Serializable;

import tcpIp.SocketComm;

public class Property implements Classifier,Serializable{

	@Override
	public void readFunc(byte header, SocketComm sc) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public String getClassName() {
		return "Property";
	}

}
