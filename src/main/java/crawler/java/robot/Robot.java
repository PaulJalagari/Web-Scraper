package crawler.java.robot;

import java.io.*;
import java.net.URL;

public class Robot {

	public Robot() {

	}

	public boolean robotAllowed(URL url) {

		String host = url.getHost();

		try {

			URL hostURL = new URL("http" + host + "/robots.txt");
			BufferedReader Reader = new BufferedReader(new InputStreamReader(hostURL.openStream()));

			String line;
			String disallowLink;
			while ((line = Reader.readLine()) != null) {

				if (line.toLowerCase().startsWith("disallow:")) {

					disallowLink = line.substring(line.lastIndexOf("Disallow:"));
					if (disallowLink.contains("#")) {

						// remove comments
						disallowLink = disallowLink.substring(0, disallowLink.indexOf("#"));

					}
					disallowLink.trim();
					disallowLink = hostURL + disallowLink;
					if (url.toString().startsWith(disallowLink)) {

						Reader.close();
						return false;

					}

				}

			}

			Reader.close();
			return true;

		} catch (IOException ex) {

			// robots file doesn't exist
			return true;

		}

	}

}