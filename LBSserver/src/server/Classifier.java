package server;

public enum Classifier {
	None((byte)0),
	Location((byte)1),
	Property((byte)2);

	private final byte id;

	//constructor
	Classifier(byte id){
		this.id = id;
	}

	//public func
	public byte to1Byte(){
		return id;
	}

	public static Classifier decodeType(byte a){
		switch(a){
		case (byte)1:
			return Location;
		case (byte)2:
			return Property;
		default:
			return None;
		}
	}
}
