package asset;

import java.io.Serializable;

import server.SocketComm;

public class Property implements Classifier,Serializable{

	private static final long serialVersionUID = 2L;

	//member
	private IndoorLocation location;
	private String name;
	@Override
	public void readFunc(byte header, SocketComm sc) {
	}

	//constructor
	public Property(){
	}

	public Property(IndoorLocation lo,String name){
		this.location = lo;
		this.name = name;
	}

	public Property(Property prop){
		this.location = prop.getLocation();
		this.name = prop.getName();
	}

	//getter/setter
	public IndoorLocation getLocation() {
		return location;
	}
	public void setLocation(IndoorLocation lo) {
		this.location = lo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return "Location:"+location.toString()+"\n"+"name:"+name+"\n" ;
	}


}
