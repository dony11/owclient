package net.strandbygaard.onewire.owclient;

public class DS2409 extends OwDeviceImpl {

	public static final String ID = "/id";
	public static final String ADDRESS = "/address";

	public DS2409(String path, OwClient owc) {
		super(path, owc);
	}

	@Override
	public double read() {
		return 0;
	}

	@Override
	public String toString() {
		return id;
	}
}
