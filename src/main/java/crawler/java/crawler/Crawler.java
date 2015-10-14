package crawler.java.crawler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import crawler.java.download.HttpDownloader;
import crawler.java.pagemanager.PageManager;
import crawler.java.robot.Robot;
import crawler.java.thread.CrawlerThread;
import crawler.java.url.GlobalURL;

import java.io.*;

public class Crawler implements Runnable {

	private String seed;
	private LinkedHashSet newLinks = new LinkedHashSet();// to preserve the
															// order of
															// insertion
	private HashSet oldLinks = new HashSet();
	private static ArrayList<HashSet> allLinks = new ArrayList<HashSet>();
	// this variable contains links having been found so far.
	private static HashSet foundLinks = new HashSet();
	private CrawlerThread crawlerThread = new CrawlerThread();
	Thread t;
	private static FileOutputStream output;
	private static PrintStream printer;

	public Crawler(String url) {

		seed = url;
		t = new Thread(this);
		t.start();

	}

	public void run() {

		allLinks.add(newLinks);
		allLinks.add(oldLinks);
		// this variable stores root node
		ArrayList<String> links = new ArrayList<String>();
		PageManager m = new PageManager();
		Robot robotCheck = new Robot();
		GlobalURL global = new GlobalURL();

		try {

			output = new FileOutputStream("foundLinks.txt");
			printer = new PrintStream(output);
			crawlerThread.addToList(newLinks, seed);

			// main loop
			while (!newLinks.isEmpty()) {

				String currentLink = crawlerThread.getNextLink(newLinks);

				URL u = new URL(currentLink);
				crawlerThread.addToList(oldLinks, currentLink);
				links = m.retrieveLinks(u);

				// for every links
				for (int i = 0; i < links.size(); i++) {

					// check to see if link that we are looking for exists in
					// the link list
					for (int h = 0; h < global.getLink().size(); h++) {

						// System.out.println(global.getLink().get(h).toString());
						if (links.get(i).equals(global.getLink().get(h).toString())) {

							if (crawlerThread.addToList(foundLinks, currentLink)) {

								System.out.println("Thread " + Thread.currentThread().getId() + ": " + currentLink
										+ " ---> " + global.getLink().get(h).toString());
								HttpDownloader.download(currentLink);
								synchronized (printer) {

									printer.append(currentLink + " ---> " + global.getLink().get(h).toString());
									printer.println();
								}

							}

						}

					}
					// check to see if links are new or not, and if they are
					// new, insert into newLinks
					if (crawlerThread.isNewLink(allLinks, links.get(i).toString())) {

						if (m.isVerified(links.get(i)) && robotCheck.robatAllowed(new URL(links.get(i)))) {

							crawlerThread.addToList(newLinks, links.get(i));
							// System.out.print(crawlerThread.inc());
							// System.out.println(links.get(i)+" "
							// +Thread.currentThread().getId());

						}

					}

				}

			}

		} catch (IOException ex) {

			System.out.println(ex);

		}

	}

}