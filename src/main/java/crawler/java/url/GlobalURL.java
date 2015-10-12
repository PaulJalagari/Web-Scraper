package crawler.java.url;

import java.util.ArrayList;

public class GlobalURL {

	private static ArrayList<String> link = new ArrayList<String>();

	public GlobalURL() {

	}

	public void addLink(String linkStr) {

		link.add(linkStr);

	}

	public ArrayList<String> getLink() {

		return link;

	}

}