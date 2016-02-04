package fr.liris.cima.nscl.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represent a utility class in NSCL
 * @author madiallo
 *
 */
public class Utils {

	/**
	 * Extract IP address from an URI
	 * @param uri an URI
	 * @return an address IP
	 */
	public static String extractIpAdress(String uri) {
		String IPADDRESS_PATTERN =
				"(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(uri);
		if (matcher.find()) {
			return matcher.group();
		}
		else{
			return "0.0.0.0";
		}
	}

	public static Date StrToDate(String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
		try {
			return formatter.parse(dateFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String dateToStr(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
		return formatter.format(date);
	}


	public static void main(String args[]) {
		String str = "http://127.0.0.1:8080/om2m";
		System.out.println(extractIpAdress(str));


		String command = "sudo gedit /opt/hady.txt";
		Runtime runtime = Runtime.getRuntime();
		try
		{
			Process process = runtime.exec(command);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
				System.out.println(line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}



}
