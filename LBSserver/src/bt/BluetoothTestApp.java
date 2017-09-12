package bt;

import java.io.IOException;
import java.util.Vector;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

public class BluetoothTestApp {

	public static final Vector/* <RemoteDevice> */ devicesDiscovered = new Vector();

	public static void main(String[] args) throws IOException, InterruptedException {

		final Object inquiryCompletedEvent = new Object();

		devicesDiscovered.clear();

		DiscoveryListener listener = new DiscoveryListener() {

			public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
				System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
				devicesDiscovered.addElement(btDevice);
				try {
					System.out.println("     name " + btDevice.getFriendlyName(false));
				} catch (IOException cantGetDeviceName) {
				}
			}

			public void inquiryCompleted(int discType) {
				System.out.println("Device Inquiry completed!");
				synchronized (inquiryCompletedEvent) {
					inquiryCompletedEvent.notifyAll();
				}
			}

			public void serviceSearchCompleted(int transID, int respCode) {
			}

			public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
			}
		};

		synchronized (inquiryCompletedEvent) {
			boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC,
					listener);
			if (started) {
				System.out.println("wait for device inquiry to complete...");
				inquiryCompletedEvent.wait();
				System.out.println(devicesDiscovered.size() + " device(s) found");
				try {
					RemoteDevice[] preknownDevices = LocalDevice.getLocalDevice().getDiscoveryAgent()
							.retrieveDevices(DiscoveryAgent.PREKNOWN);
					RemoteDevice[] cachedDevices = LocalDevice.getLocalDevice().getDiscoveryAgent()
							.retrieveDevices(DiscoveryAgent.CACHED);

					if (preknownDevices != null) {
						System.out.println("preknownDevices\n");
						for (int i = 0; i < preknownDevices.length; i++) {
							System.out.println(preknownDevices[i].getBluetoothAddress() + ":"
									+ preknownDevices[i].getFriendlyName(true) + "\n");
						}
					}
					if (cachedDevices != null) {
						System.out.println("cachedDevices\n");
						for (int i = 0; i < cachedDevices.length; i++) {
							System.out.println(cachedDevices[i].getBluetoothAddress() + ":"
									+ cachedDevices[i].getFriendlyName(true) + "\n");
						}
					}
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

			}
		}
	}

}