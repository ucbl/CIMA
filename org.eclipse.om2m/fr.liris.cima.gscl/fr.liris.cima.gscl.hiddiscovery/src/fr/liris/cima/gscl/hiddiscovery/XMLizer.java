/*

Recoit le code prestructuré et le transforme en XML

*/
package fr.liris.cima.gscl.hiddiscovery;

import java.util.ArrayList;
import static sun.security.krb5.Confounder.intValue;


public class XMLizer {
    private ArrayList<String> data = new ArrayList<>();
    private String xmlOut = "";
    
    public XMLizer(){
        
    }
    public String xmlize(String entre)
    {
        
        addStartingTag();//On ajoute les formalités xml
        
        String[] dataTemp = entre.split("\n");
        for(String e : dataTemp){
            data.add(e);
        }
        
        int i;
        int profondeur = 1;
        int profOld = profondeur;
        
        ArrayList<tagInfo> pile = new ArrayList<>();
        
        for(i = 0;i < data.size();i++)
        {
            profondeur = getProfondeur(data.get(i));
            xmlOut+=profondeur+"\n";
            //System.out.println("Depth : "+profondeur);
            if(profondeur == profOld)//Depile, empile (meme niveau donc on ferme la prec et on ouvre la suiv)
            {
                if(i == 0)//Premiere ligne donc on depile pas
                {
                    String[] res = getKeyValue(data.get(i));
                    addOpenTag(res[0],profondeur);
                    pile.add(new tagInfo(res[0],profondeur));
                }
                else
                {
                    addCloseTag(pile.get(pile.size()-1).getTagName(),profondeur);//Ferme le precedent
                    String[] res = getKeyValue(data.get(i));
                    addOpenTag(res[0],profondeur);//Ouvre l'actuelle
                    pile.add(new tagInfo(res[0],profondeur));
                }
            }
            else if(profondeur == profOld + 1)//empile (creer la balise de la ligne i-1)
            {
                String[] res = getKeyValue(data.get(i));//fetch info
                pile.add(new tagInfo(res[0],profondeur));//empile
                addOpenTag(res[0],profondeur);//ajout a la sortie
            }
            else if(profondeur == profOld -1)//depile, depile, empile (on ferme la balise empilé, on ferme la 
                //balise "pere" concernée, et on ouvre la prochaine que l'on empile.
            {
                
            }
            else
            {
                System.out.println("$$$$$$$Cas non traité$$$$$$$$");
            }

            profOld = profondeur;
        }
        
        return xmlOut;
    
    }
    
    public int toFile()
    {
        return -1;
    }
    
    public void addStartingTag(){
        xmlOut = xmlOut + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    }
    public void addOpenTag(String name){
        xmlOut = xmlOut + "<"+name+">\n";
    }
    public void addCloseTag(String name){
        xmlOut = xmlOut + "</"+name+">\n";
    }
    
    public void addOpenTag(String name,int profondeur){
        xmlOut =  xmlOut + genSpaces(4*profondeur)+"<"+name+">\n";
    }
    public void addCloseTag(String name,int profondeur){
        xmlOut =  xmlOut + genSpaces(4*profondeur)+"</"+name+">\n";
    }
    
    
    public void addValueTag(String tag,String value,int profondeur){
        xmlOut = xmlOut +genSpaces(4*profondeur)+"<"+tag+">"+value+"</"+tag+">\n";
    }
    public String[] getKeyValue(String line){
        String key = "";
        String value = "";
        int cpt;
        for(cpt = 0;cpt < line.length(); cpt++){
            if(line.charAt(cpt) == ' '){
                key = line.substring(0, cpt);
                value = line.substring(cpt,line.length());
                cpt = line.length()+1;
            }
        }
        for(cpt = 0;cpt < value.length();cpt++){
            if(value.charAt(cpt) != ' '){
                value = value.substring(cpt,value.length());
                cpt = value.length()+1;
            }
        }
        value.replaceFirst(" ", "");
        return new String[] {key,value};
    }
    
    public String genSpaces(int nb){
        String out = "";
        int i;
        for(i = 0 ; i < nb ; i++){
            out = out + " ";
        }
        return out;
    }
    public int getProfondeur(String line){
        if(line.substring(0,3).equals("-><") || line.substring(0,3).equals("<-<") || line.substring(0,3).equals("--<"))
            {
                //Remplacer les nb d'espaces par des profondeurs
                int j = 3;//3 caracteres avant
                while(line.charAt(j) != '>' && j < line.length())
                {//Juste compteur
                    j++;
                }
                String nbSpace = line.substring(3,j);
                
                int out = Integer.parseInt(nbSpace);
                return out;
            }
        
            return -1;
    }

    class tagInfo{//Class qui gere les tag et leur info
        private String tagName;
        private int tagProfondeur;
        public tagInfo(String name,int pro){
            tagName = name;
            tagProfondeur = pro;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public int getTagProfondeur() {
            return tagProfondeur;
        }

        public void setTagProfondeur(int tagProfondeur) {
            this.tagProfondeur = tagProfondeur;
        }
        public String toString(){
            return tagName+" : "+tagProfondeur;
        }
    }

}


/*
public String xmlize(String entre)
    {
        addStartingTag();//On ajoute les formalités xml
        
        String[] dataTemp = entre.split("\n");
        for(String e : dataTemp){
            data.add(e);
        }
        
        int i;
        int profondeurOld = 1;
        int profondeur = 1;
        
        ArrayList<tagInfo> tagTab = new ArrayList<>();
        
        for(i = 0;i < data.size();i++)
        {
            profondeur = getProfondeur(data.get(i));
            //System.out.println("Depth : "+profondeur);
            if(profondeur > profondeurOld && i > 0){//Detection de balise ouvrante
                String tagTitle = data.get(i-1);
                if(tagTitle.endsWith(":")){
                    //System.out.println("Creation de balise");
                    String current = data.get(i);
                    tagInfo currentTag;
                    if(profondeur >= 10){
                        tagTitle = tagTitle.substring(6, tagTitle.length()-1);//decalage de 6
                        current = current.substring(6,current.length());
                    }
                    else{
                        tagTitle = tagTitle.substring(5, tagTitle.length()-1);//Decalage de 5
                        current = current.substring(5,current.length());
                    }
                    //System.out.println(tagTitle);
                    tagTab.add(new tagInfo(tagTitle,profondeur-1));//-1 car on veut la balise qui ouvre
                    
                    addOpenTag(tagTitle,profondeur);
                    String[] info = getKeyValue(current);
                    addValueTag(info[0],info[1],profondeur+1);
                }
                
            }
            else if(profondeur == profondeurOld){
                String current = data.get(i);
                    if(profondeur >= 10){
                        current = current.substring(6,current.length());
                    }
                    else{
                        current = current.substring(5,current.length());
                    }
                    String[] info = getKeyValue(current);
                    addValueTag(info[0],info[1],profondeur+1);
            }
            else if(profondeur < profondeurOld){//Element apres vu qu'on redescent dans l'archi.
                
            }
            
            profondeurOld = profondeur;
        }
        
        System.out.println("TagTab : ");
        for( tagInfo e : tagTab){
            System.out.println(e.toString());
        }
        
        
        return xmlOut;
    }

*/
