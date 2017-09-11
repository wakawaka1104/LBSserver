package asset;

import java.io.Serializable;
import java.util.ArrayList;

import server.SocketComm;

public class Property implements Classifier,Serializable{

	private static final long serialVersionUID = 2L;

	//member
	private IndoorLocation location;
	private String name;
	private ArrayList<String> function;
	private String selection ="";


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

	public ArrayList<String> getFunction() {
		return function;
	}

	public void setFunction(ArrayList<String> function) {
		this.function = function;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
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
