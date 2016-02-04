/*

Recupere les infos HID et enleve les info inutiles (lsusb -v = VERBEUX !)

*/
package fr.liris.cima.gscl.hiddiscovery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;

/**
 * Retrieve any HID information and delete useless information
 */
public class HIDFetcher {
    public HIDFetcher(){
        
    }
    public String fetch(int bus, int numPeriph){
        String out = "";
        String temp = "";
        try {
            Process p = Runtime.getRuntime().exec("lsusb -s "+bus+":"+numPeriph+" -v");
             
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            //BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
 
            
            while ((temp = stdInput.readLine()) != null) {
                out = out + temp+"\n";
            }
             //System.out.println(out);
        }
        catch (IOException e) {
            System.out.println("Exception : HIDFetcher:fetch:");
            e.printStackTrace();
            System.exit(-1);
        }
        out = filterRawOutput(out);
        return out;
    }
    public String filterRawOutput(String rawInput){//Supprime le debut de lsusb ( 0 -> "Device descriptor")
        String out = "";
        System.out.println("Beginning filtering...");
        String startSymbol = "Device Descriptor:";
        System.out.println("StartFilter : \'"+startSymbol+"\'");
        int i = 0;
        while(i < rawInput.length()-startSymbol.length()){
            //System.out.println("Current substring : "+rawInput.substring(i, i+startSymbol.length()));
            
            if(rawInput.substring(i, i+startSymbol.length()).equals(startSymbol)){
                System.out.println("Symbol find at : "+i);
                out = StrRemoveAt(rawInput,0,i);
                i = rawInput.length()+1;
            }
            i++;
        }
        
        
        return out;
    }
    
    public String StrRemoveAt(String str,int posDebut,int n){
        String temp;
        String out = str;
        temp = out.substring(posDebut, posDebut+n);
        //System.out.println("Will delete : "+temp);
        out = out.replace(temp,"");
        return out;
    }


}

