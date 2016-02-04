package fr.liris.cima.gscl.commons;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 */
public class ExecuteShellComand {

	public static Set<String> getAllIpAddress(String command, String ipPrefix) {

		Set<String> listAddress = new HashSet<>(); 
		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";			
			while ((line = reader.readLine())!= null) {
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
					line = st.nextToken();
					if(line.contains(ipPrefix)) {
						listAddress.add(line);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listAddress;
	}
}