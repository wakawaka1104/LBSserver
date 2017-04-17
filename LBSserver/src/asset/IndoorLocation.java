package asset;

//物理位置を示すクラス
public class IndoorLocation {

	//member
	private double x;
	private double y;
	private double z;

	//constructor
	public IndoorLocation(double x,double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	//public func
	public double dist(IndoorLocation a){
		return Math.sqrt( Math.pow( (x-a.getX()) ,2) + Math.pow( (y-a.getY()) ,2) + Math.pow( (z-a.getZ()) ,2) );
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}

	public String toString(){
		return "x:[" + x + "],y:[" + y + "],z:[" + z + "]";
	}

}