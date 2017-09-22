package asset;

import java.io.Serializable;

import tcpIp.SocketComm;

public class ByteFile implements Serializable, Classifier {

	private byte[] file;
	private static final long serialVersionUID = 3L;

	public ByteFile(byte[] file){
		this.file = file;
	}

	@Override
	public void readFunc(byte header, SocketComm sc) {

	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}



}
