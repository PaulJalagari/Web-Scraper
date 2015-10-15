package crawler.java.webcrawler;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import crawler.java.crawler.Crawler;
import crawler.java.pagemanager.PageManager;
import crawler.java.url.GlobalURL;

public class WebCrawler {

	private String url;

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		System.out.println("Please enter link which you're looking for:");
		Scanner Input = new Scanner(System.in);
		String Link = Input.nextLine();
		// this variable contains link which we are looking for.
		followLink(Link);

	}

	private static void followLink(String url) {

		// TODO Auto-generated method stub
		GlobalURL Global = new GlobalURL();

		// long heapSize = Runtime.getRuntime().maxMemory();
		// System.out.println("Heap Size = " + heapSize);
		try {

			File Roots = new File("roots.txt");
			FileInputStream File = new FileInputStream(Roots);
			DataInputStream Data = new DataInputStream(File);
			BufferedReader Reader = new BufferedReader(new InputStreamReader(Data));
			String line;
			ArrayList<String> links = new ArrayList<String>();
			PageManager pageManager = new PageManager();

			if (pageManager.isVerified(url)) {

				// we add different variations of URL which we want to look for
				Global.addLink(url);
				if (url.endsWith("/")) {

					Global.addLink(url.substring(0, url.length() - 1));

					if (url.contains("www")) {

						Global.addLink("http://" + url.substring(11));
						Global.addLink("http://" + url.substring(11, url.length() - 1));

					}

					else {

						Global.addLink("http://www." + url.substring(7));
						Global.addLink("http://www." + url.substring(7, url.length() - 1));

					}

				} else {

					Global.addLink(url + "/");

					if (url.contains("www")) {

						Global.addLink("http://" + url.substring(11));
						Global.addLink("http://" + url.substring(11) + "/");

					}

					else {

						Global.addLink("http://www." + url.substring(7));
						Global.addLink("http://www." + url.substring(7) + "/");

					}

				}

				URL x = new URL(url);
				String host = x.getHost();
				links.add("http://" + host);

			} else {

				System.out.println("Invalid URL");
				System.exit(0);

			}

			// add root nodes from file
			while ((line = Reader.readLine()) != null) {

				links.add(line);

			}

			// create thread for each root node

			for (int i = 0; i < links.size(); i++) {

				Runnable crawl = new Crawler(links.get(i));
				Thread thread = new Thread(crawl);
				thread.start();

				try {

					thread.join();

				} catch (Exception e) {
					System.out.println(e);

				}

			}

		} catch (IOException ex) {

			System.out.println(ex);

		}

	}

}
