package crawler.java.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * A utility that downloads a file from a URL.
 * 
 * @author www.codejava.net
 *
 */
public class HttpDownloadUtility {
	private static final int BUFFER_SIZE = 4096;
	static Logger log=Logger.getLogger(HttpDownloadUtility.class);

	/**
	 * Downloads a file from a URL
	 * 
	 * @param FileURL
	 *            HTTP URL of the file to be downloaded
	 * @param SaveDir
	 *            path of the directory to save the file
	 * @throws IOException
	 */
	public static void downloadFile(String FileURL, String SaveDir) throws IOException {
		URL url = new URL(FileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = FileURL.substring(FileURL.lastIndexOf("/") + 1, FileURL.length());
			}

			log.info("Content-Type = " + contentType);
			log.info("Content-Disposition = " + disposition);
			log.info("Content-Length = " + contentLength);
			log.info("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = SaveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			log.info("File downloaded");
		} else {
			log.fatal("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

	
}
