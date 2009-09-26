package net.strandbygaard.onewire.owclient;

public interface OwSensor extends OwDevice {
	
	public static enum Reading {
		TEMP, HUM
	};

	public static enum Unit {
		TEMP, HUM
	};

	boolean canRead(Reading r);
	
	double read(Reading r);

	public String getUnit(Reading r);
	
	boolean canUpdate();

	public void update();
	
	long getLastUpdate();
	


}
