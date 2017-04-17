package asset;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

public class SlaveList {

	//singleton pattern
	private static SlaveList sl = new SlaveList();
	private ArrayList<Property> slaveList = new ArrayList<Property>();

	//constructor
	private SlaveList(){
	}

	//public func
	//IndoorLocationに最も近いSlaveを検索
	//返り値は相当するSlaveのProperty
	//存在しないときはnullをreturn
	public Property slaveSearch(IndoorLocation locate){
		Property nearest = null;
		//遠すぎる場合は見ない
		double distMin = Constant.THRETHOLD;
		for(Iterator<Property> it = slaveList.iterator(); it.hasNext();){
			Property tmp = it.next();
			double dist = tmp.getLocation().dist(locate);
			if(distMin > dist ){
				//最小値を更新したとき
				nearest = tmp;
				distMin = dist;
			}
		}
		return nearest;
	}

	public SlaveList getInstance(){
		return sl;
	}

	public void slaveAdd(Property a){
		slaveList.add(a);
	}


	//デバグ用main
	public static void main(String[] args) {
		Property a =new Property();
		Property b = new Property();
		Property c = new Property();
		try {
			a.setIp(InetAddress.getLocalHost());
			b.setIp(InetAddress.getLocalHost());
			c.setIp(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		a.setLocation(new IndoorLocation(10, 20, 30));
		b.setLocation(new IndoorLocation(-10, 10, 100));
		c.setLocation(new IndoorLocation(60, 50, 200));

		a.setName("a");
		b.setName("b");
		c.setName("c");

		a.setPort(11111);
		b.setPort(22222);
		c.setPort(44444);

		SlaveList sl = new SlaveList();
		sl.slaveAdd(a);
		sl.slaveAdd(b);
		sl.slaveAdd(c);
		Property tmp = sl.slaveSearch(new IndoorLocation(11,50, 200));
		if(tmp != null){
			System.out.println(tmp.toString());
		}else{
			System.out.println("みつからないよ");
		}
	}

}
