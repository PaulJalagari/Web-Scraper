package crawler.java.download;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HttpDownloader {
	
	public static void download(String url) {
		Properties prop = new Properties();
		try {
		InputStream input = new FileInputStream("Configurations.properties");
		prop.load(input);
		String fileURL = url;
		String saveDir = prop.getProperty("path");
			HttpDownloadUtility.downloadFile(fileURL, saveDir);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
