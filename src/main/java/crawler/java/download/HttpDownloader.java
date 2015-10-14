package crawler.java.download;

import java.io.IOException;

public class HttpDownloader {
	
	public static void download(String url) {
		String fileURL = url;
		String saveDir = "C:\\Users\\jalagarip\\Downloads\\Down";
		try {
			HttpDownloadUtility.downloadFile(fileURL, saveDir);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
