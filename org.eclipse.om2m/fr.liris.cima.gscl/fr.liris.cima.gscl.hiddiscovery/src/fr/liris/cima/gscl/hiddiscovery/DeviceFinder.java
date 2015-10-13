package fr.liris.cima.gscl.hiddiscovery;

import java.io.BufferedReader;
import java.util.regex.*;
import java.io.IOException;
import java.io.InputStreamReader;



public class DeviceFinder {

	
    
	 public DeviceFinder(){
		 
	 }
	 
	 public int FindAllNewUpdates(){
		 boolean keepReading = true;
		 String line;
		 try {
			 Process p = Runtime.getRuntime().exec("tail -f /var/log/syslog | grep \"New USB device found \" > usblog");
			 BufferedReader logInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			 while(keepReading){
				line=logInput.readLine();
				if(line==null){
					//Attendre une nouvelle connexion
					Thread.sleep(1000);
				}
				else{
					int deviceBus = -1;
					int deviceNumber = -1;
					if(line.contains("new full-speed USB") || line.contains("new high-speed USB")){
						
						deviceBus=GetBus(line);
						
						deviceNumber=getDeviceId(line);
						
						System.out.println(line);
						System.out.println("Le périphérique " + deviceNumber + " sur le host " + deviceBus + " a été détecté.");
						
                                                Thread.sleep(1000);
                                                
                                                HIDFetcher fetcher = new HIDFetcher();
                                                String outf = fetcher.fetch(deviceBus,deviceNumber);
                                                //System.out.println("\n\nOUT HIDFetcher:\n"+outf);

                                                Parser parse = new Parser();
                                                String outp = parse.parse(outf);

                                                System.out.println("Parser : "+outp);

                                                XMLizer xml = new XMLizer();
                                                String outx = xml.xmlize(outp);
                                                System.out.println("XMLizer : "+outx);
						
					}
					else if (line.contains("USB disconnect")){
						//Un périphérique a été déconnecté
						
						   deviceBus=GetBus(line);
						
						deviceNumber=getDeviceId(line);
						System.out.println("Le périphérique " + deviceNumber + " sur le host " + deviceBus + " a été retiré.");
						
						
						
					}
					
				
				}
			 }
				
				
			 
			p.destroy();
		 }
		 catch(IOException | InterruptedException e){
			 e.printStackTrace();
		 }
		
		 return 0;
		 
	 }
	 private int GetBus(String line){
		 int deviceBus = -1;
		 Pattern pat = Pattern.compile(".*].....(.).*");
		Matcher m = pat.matcher(line);
		if(m.find()){
			   deviceBus=Integer.parseInt(m.group(1));
			}
		return deviceBus;
	 }
	 private int getDeviceId(String line){
		 int deviceNumber = -1;
		 Pattern patx = Pattern.compile(".* number ([0-9]*)");
		 Matcher mx = patx.matcher(line);
		 if(mx.find()) {
				deviceNumber=Integer.parseInt(mx.group(1));
			}
		 return deviceNumber;
		 
	 }
	 public int FindNextNewDevice(){
		 return 0;
	 }
}
