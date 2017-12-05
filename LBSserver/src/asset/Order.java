package asset;

import java.io.Serializable;

import tcpIp.SocketClient;
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
		DeviceProperty tmpA;
		DeviceProperty tmpB;
		if(propA instanceof ContentProperty){
			tmpA = ((ContentProperty) propA).getParent();
		}else{
			tmpA = (DeviceProperty)propA;
		}
		if(propB instanceof ContentProperty){
			tmpB = ((ContentProperty) propB).getParent();
		}else{
			tmpB = (DeviceProperty)propB;
		}


//A:camera,B:display
		SocketClient propAsocket = new SocketClient(((TcpipDeviceProperty)tmpA).getIp(),((TcpipDeviceProperty)tmpA).getPort());
		Thread t = new Thread(propAsocket);
		t.start();
		propAsocket.asyncSend(this, (byte)0);
//		SocketClient propBsocket = ((TcpipDeviceProperty)tmpB).getSocketClient();
//		propBsocket.asyncSend(this, (byte)0);

	}

	public void setFile(ByteFile file){
		this.file = file;
	}

	@Override
	public String getClassName() {
		return "Order";
	}
}
