package asset;

import java.io.Serializable;

import tcpIp.SocketComm;

public class Order implements Serializable, Classifier {

	private static final long serialVersionUID = 1L;
	private String order = "";
	private ByteFile file;

	public Order(String order){
		this.order = order;
	}
	public Order(Property prop){
		this.order = prop.getSelection();
	}

	@Override
	public void readFunc(byte header, SocketComm sc) {

	}

	public void setMessage(String order){
		this.order = order;
	}

	public void setFile(ByteFile file){
		this.file = file;
	}
	@Override
	public String getClassName() {
		return "Order";
	}
}
