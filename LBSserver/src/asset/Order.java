package asset;

import java.io.Serializable;

import tcpIp.SocketComm;

public class Order implements Serializable, Classifier {

	private static final long serialVersionUID = 1L;
	private String order = "";
	private ByteFile file;

	private Property propA;
	private Property propB;

	public Order(String order,Property a, Property b){
		this.order = order;
		this.propA = a;
		this.propB = b;
	}

	@Override
	public void readFunc(byte header, SocketComm sc) {

	}

	public void setFile(ByteFile file){
		this.file = file;
	}

	@Override
	public String getClassName() {
		return "Order";
	}
}
