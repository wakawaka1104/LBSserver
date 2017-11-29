package asset;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientList{

	//singleton pattern
	private static ClientList instance = new ClientList();

	private ArrayList<Property> clientList = new ArrayList<>();


	private ClientList(){

	}

	public void add(Property prop){
		clientList.add(prop);
	}


	public static void listUpdate(String[] posPacket){
		//posPacket
		//[0]:"POS"
		//[1]:移動機シリアル番号
		//[2]:測位計算結果座標x
		//[3]:y
		//[4]:z
		//[5]:有効な測距結果を得た固定機数
		//[6]:測位年月日 yy/mm/dd
		//[7]:測位時刻 hh:mm::ss.msms
		//[8]:ignored data

		//name = シリアル番号
		//リスト中に同名のクライアントがあれば、それを更新
		//なければadd

		IndoorLocation loc = new IndoorLocation(Double.parseDouble(posPacket[2]),Double.parseDouble(posPacket[3]),Double.parseDouble(posPacket[4]));

		for(Iterator<Property> it = ClientList.getInstance().clientList.iterator();it.hasNext();){
			Property tmp = it.next();
			if(posPacket[1] == tmp.getName()){
				//更新
				tmp.setLocation(loc);
				return;
			}
		}
		//同名なしならadd
		ClientList.getInstance().add(new Property(loc,posPacket[1],new ArrayList<String>(),0));

	}

	public static void loadList(){
		try {
			XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream("config" + File.separator + "ClientList.xml")));
			ClientList.getInstance().clientList = ((ArrayList<Property>)d.readObject());
			d.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void writeList(){
		try {
			XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("config" + File.separator + "ClientListList.xml")));
			e.writeObject(ClientList.getInstance().clientList);
			e.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Iterator<Property> it = clientList.iterator(); it.hasNext();){
			sb.append(it.next().toString());
		}
		return sb.toString();
	}

	public static ClientList getInstance(){
		return instance;
	}
}
