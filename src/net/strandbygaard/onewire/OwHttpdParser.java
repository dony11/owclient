package net.strandbygaard.onewire;

import java.io.IOException;
import java.util.ArrayList;

import org.owfs.ownet.OWNet;

public class OwHttpdParser {

	public static final String TEMP = "<TD><B>temperature</B></TD>";
	public static final String HUM = "<TD><B>humidity</B></TD>";
	public static final String END = "</TD>";
	public static final String BASEURL = "http://10.0.0.10:8080/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OWNet ownet = new OWNet("10.0.0.10", 8080);
		ArrayList<String> devices = new ArrayList<String>(5);
		try {
			ownet.Connect();
			String[] top = ownet.Dir("");
			
			for (String path : top) {
				if(path.startsWith("/1F."))
				{
				}
				if(path.startsWith("/26."))
				{
					devices.add(path);
				}
				if(path.startsWith("/10."))
				{
					devices.add(path);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static float getValue(String param, String page) {
		int start = page.indexOf(param);
		if (start == -1) {
			throw new IllegalArgumentException("The parameter was not found");
		}

		int end = page.indexOf(END, start + param.length());
		String val = page.substring(start + param.length() + END.length(), end);

		return Float.parseFloat(val.trim());
	}
}
