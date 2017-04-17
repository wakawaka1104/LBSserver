package asset;

import java.net.InetAddress;

public class Property {

	//member
	private IndoorLocation lo;
	private InetAddress ip;
	private int port;
	private String name;

	//constructor
	public Property(){

	}
	public Property(Property prop){
		this.lo = prop.getLocation();
		this.ip = prop.getIp();
		this.port = prop.getPort();
		this.name = prop.getName();
	}

	//getter/setter
	public IndoorLocation getLocation() {
		return lo;
	}
	public void setLocation(IndoorLocation lo) {
		this.lo = lo;
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
		return "Location:"+lo.toString()+"\n"+"ip:"+ip.toString()+"\n"+"port:"+port+"\n"+"name:"+name+"\n" ;
	}

	//デバグ用main
	public static void main(String[] args) {

	}

}
