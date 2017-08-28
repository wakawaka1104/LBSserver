package asset;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

import server.SocketComm;

public class BtDeviceProperty extends Property {

	//member
	LocalDevice local;
	LocalDevice remote;

	//constructor
	public BtDeviceProperty() {
	}
	public BtDeviceProperty(IndoorLocation location, String name, LocalDevice local){
		super(location,name);
		this.local = local;
	}

	@Override
	public void readFunc(byte header, SocketComm sc){

	}

	public void setRemote(LocalDevice remote){
		this.remote = remote;
	}

	//static method
	//自身のBt情報をPropertyに変換
	static BtDeviceProperty getLocalBtProperty(IndoorLocation location, String name){
		try {
			return new BtDeviceProperty(location,name,LocalDevice.getLocalDevice());
		} catch (BluetoothStateException e) {
			System.err.println("BtDeviceProperty:getLocalBtProperty[error]:the Bluetooth system could not be initialized\n");
			e.printStackTrace();
			return null;
		}
	}
}
