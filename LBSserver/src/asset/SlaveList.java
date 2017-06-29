package asset;

import java.util.ArrayList;
import java.util.Iterator;

public class SlaveList {

	//singleton pattern
	private static SlaveList sl = new SlaveList();
	private static ArrayList<Property> slaveList = new ArrayList<Property>();

	//constructor
	private SlaveList(){
	}

	//public func
	//IndoorLocationに最も近いSlaveを検索
	//返り値は相当するSlaveのProperty
	//存在しないときはnullをreturn
	public static Property slaveSearch(IndoorLocation locate){
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

	public static SlaveList getInstance(){
		return sl;
	}

	public void slaveAdd(Property a){
		slaveList.add(a);
		System.out.println(toString());
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Iterator<Property> it = slaveList.iterator(); it.hasNext();){
			sb.append(it.next().toString());
		}
		return sb.toString();
	}
}
