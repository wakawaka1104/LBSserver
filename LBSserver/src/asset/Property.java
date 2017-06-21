package asset;

import java.io.Serializable;
import java.net.InetAddress;

import server.SocketComm;

public class Property implements Classifier,Serializable{

	private static final long serialVersionUID = 1L;

	//member
	private IndoorLocation location;
	private InetAddress ip;
	private int port;
	private String name;

	//public func
	@Override
	public void readFunc(byte header, SocketComm sc) {
		SlaveList.getInstance().slaveAdd(this);
		System.out.println(SlaveList.getInstance().toString());
	}

	//constructor
	public Property(){

	}

	public Property(IndoorLocation lo,InetAddress ip,int port,String name){
		this.location = lo;
		this.ip = ip;
		this.port = port;
		this.name = name;
	}

	public Property(Property prop){
		this.location = prop.getLocation();
		this.ip = prop.getIp();
		this.port = prop.getPort();
		this.name = prop.getName();
	}

	//getter/setter
	public IndoorLocation getLocation() {
		return location;
	}
	public void setLocation(IndoorLocation lo) {
		this.location = lo;
	}
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
		return "Location:"+location.toString()+"\n"+"ip:"+ip.toString()+"\n"+"port:"+port+"\n"+"name:"+name+"\n" ;
	}
}
