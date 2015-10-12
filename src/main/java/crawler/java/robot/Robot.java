package crawler.java.robot;

import java.io.*;
import java.net.URL;

public class Robot {

	public Robot() {

	}

	public boolean robatAllowed(URL url) {

		String host = url.getHost();

		try {

			URL hostURL = new URL("http" + host + "/robots.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(hostURL.openStream()));

			String line;
			String disallowLink;
			while ((line = br.readLine()) != null) {

				if (line.toLowerCase().startsWith("disallow:")) {

					disallowLink = line.substring(line.lastIndexOf("Disallow:"));
					if (disallowLink.contains("#")) {

						// remove comments
						disallowLink = disallowLink.substring(0, disallowLink.indexOf("#"));

					}
					disallowLink.trim();
					disallowLink = hostURL + disallowLink;
					if (url.toString().startsWith(disallowLink)) {

						br.close();
						return false;

					}

				}

			}

			br.close();
			return true;

		} catch (IOException ex) {

			// robots file doesn't exist
			return true;

		}

	}

}