package net.strandbygaard.onewire.owclient;

import java.util.ArrayList;

public class OwTree {

	private OwClient owc = null;
	private ArrayList<String> deviceIdCache = null;

	public OwTree(OwClient owc) {

		this.owc = owc;
		reset();
	}

	public synchronized void reset() {
		this.deviceIdCache = new ArrayList<String>(5);
	}

	public synchronized void print() {
		if (deviceIdCache != null) {
			if (!deviceIdCache.isEmpty()) {
				reset();
			}
		} else {
			reset();
		}

		String[] str = { "/" };
		print(str);
	}

	public synchronized void print(String[] in) {
		// TODO 1 iterate over in and generate new String [] with paths
		String[] paths = owc.dir("/");

		ArrayList<String> next = new ArrayList<String>(10);

		for (String p : paths) {
			String id = p.substring(p.lastIndexOf("/") + 1);
			if (id.startsWith("10.") || id.startsWith("26.")
					|| id.startsWith("81.")) {
				if (!deviceIdCache.contains(id)) {
					System.out.println(p);
					deviceIdCache.add(id);
				}
			}
			if (id.startsWith("1F")) {
				if (!deviceIdCache.contains(id)) {
					deviceIdCache.add(id);
					System.out.println(p);
					next.add(p + "/main/");
				}

			}
		}
		if (!next.isEmpty()) {
			String str[] = new String[next.size()];
			next.toArray(str);
			print(str);
		}
	}

	public synchronized void list(String path) {
		ArrayList<String> devicePaths = new ArrayList<String>(5);

		System.err.println("list(" + path + ")");
		String[] paths = owc.dir(path);
		for (String p : paths) {
			while (p != null) {
				String id = p.substring(p.lastIndexOf("/") + 1);
				if (id.startsWith("10.") || id.startsWith("26.")) {
					devicePaths.add(p);
				}

				if (id.startsWith("1F")) {
					deviceIdCache.add(id);
				}

				System.out.println(p);
				System.out.println(id);
			}
		}
	}

	public String find(String path, String id) {
		String idPath = null;
		String[] paths = owc.dir(path);
		for (String p : paths) {
			if (p.startsWith("/1F")) {
				idPath = find(p + "/main/", id);
			} else {
				System.out.println(p);
			}
		}

		return idPath;
	}

}
