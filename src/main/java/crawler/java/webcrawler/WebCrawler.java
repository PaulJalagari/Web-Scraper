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
		Scanner input = new Scanner(System.in);
		String url = input.nextLine();
		// this variable contains link which we are looking for.
		GlobalURL global = new GlobalURL();

		// long heapSize = Runtime.getRuntime().maxMemory();
		// System.out.println("Heap Size = " + heapSize);
		try {

			File roots = new File("roots.txt");
			FileInputStream fis = new FileInputStream(roots);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String line;
			ArrayList<String> links = new ArrayList<String>();
			PageManager pageManager = new PageManager();

			if (pageManager.isVerified(url)) {

				// we add different variations of URL which we want to look for
				global.addLink(url);
				if (url.endsWith("/")) {

					global.addLink(url.substring(0, url.length() - 1));

					if (url.contains("www")) {

						global.addLink("http://" + url.substring(11));
						global.addLink("http://" + url.substring(11, url.length() - 1));

					}

					else {

						global.addLink("http://www." + url.substring(7));
						global.addLink("http://www." + url.substring(7, url.length() - 1));

					}

				} else {

					global.addLink(url + "/");

					if (url.contains("www")) {

						global.addLink("http://" + url.substring(11));
						global.addLink("http://" + url.substring(11) + "/");

					}

					else {

						global.addLink("http://www." + url.substring(7));
						global.addLink("http://www." + url.substring(7) + "/");

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
			while ((line = br.readLine()) != null) {

				links.add(line);

			}

			// create thread for each root node
			/*
			 * for (int i = 0; i < links.size(); i++) {
			 * 
			 * Runnable crawl = new Crawler(links.get(i)); Thread t = new
			 * Thread(crawl); t.start();
			 * 
			 * try {
			 * 
			 * t.join();
			 * 
			 * } catch (Exception e) {
			 * 
			 * }
			 * 
			 * }
			 */

			/*
			 * Runnable crawl1 = new Crawler("http://www.uwo.ca/"); Thread t1 =
			 * new Thread(crawl1);
			 * 
			 * Runnable crawl2 = new Crawler("http://www.utoronto.ca"); Thread
			 * t2 = new Thread(crawl2);
			 * 
			 * t1.start(); t2.start();
			 * 
			 * try {
			 * 
			 * t1.join(); t2.join();
			 * 
			 * } catch (Exception e) {
			 * 
			 * System.out.println(e);
			 * 
			 * }
			 */

		} catch (IOException ex) {

			System.out.println(ex);

		}

	}

}