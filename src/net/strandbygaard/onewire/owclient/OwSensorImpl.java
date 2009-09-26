package net.strandbygaard.onewire.owclient;

public abstract class OwSensorImpl extends OwDeviceImpl implements OwSensor {

	public OwSensorImpl(String path, OwClient owc) {
		super(path, owc);
	}

	public OwSensorImpl(String path) {
		super(path);
	}

	public long getLastUpdate() {
		return 0;
	}

	public boolean canUpdate() {
		boolean state = false;
		if (owc != null) {
			state = true;
		}
		return state;
	}

	protected boolean shouldUpdate() {
		boolean state = false;
		long now = System.currentTimeMillis();
		if ((now - lastUpdate) > UPDATE_INTERVAL) {
			state = true;
		}
		return state;
	}

	public void update() {
		lastUpdate = System.currentTimeMillis();
	}

	public abstract double read(Reading r);

	public String getUnit(Unit u) {
		// TODO Auto-generated method stub
		return null;
	}
}
