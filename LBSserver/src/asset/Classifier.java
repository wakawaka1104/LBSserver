package asset;

public interface Classifier {

		//受信した時の挙動を書く関数
		public void readFunc(byte header);
		public String toString();
}
