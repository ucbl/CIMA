/*
Gere la "profondeur" des informations, prepare le travail pour XMLizer

Mets des symbole differents suivant la profondeur de l'info dans les données brut, sert a prestructuer
l'info

Ajoute aussi les informations en plus si informations il y a...
2-1.1:1.0
*/
package fr.liris.cima.gscl.hiddiscovery;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


/**
 * Manages the "depth" of the information, prepare work for XMLizer
 */
public class Parser {
    private String[]dataList;
    private ArrayList<Integer> spacePresent = new ArrayList<>();
    private Hashtable<Integer,Integer> spaceToDeep = new Hashtable<Integer,Integer>();
    
    public Parser(){
        
    }
    
    public String parse(String rawData){//Mets tout au meme niveau avec symbol pour aider XMLizer
        
      /*
        On lit ligne par ligne
        On compte les espaces present, si a la ligne suivante :
            Plus d'espce => Creation nouveau bloc plus profond
            Moins d'espace => On remonte d'un niveau
        */
        if(rawData == null || rawData.length() < 1){
            return "$error$";
        }
        dataList = rawData.split("\n");
        int i = 0;
        int nbSpace = 0;
        int nbSpaceOld = nbSpace;
        while(i < dataList.length){
    
            while(dataList[i].charAt(0) == ' ')
            {
                dataList[i] = dataList[i].replaceFirst(" ","");
                nbSpace++;
            }
            //System.out.print(nbSpace+":");
            if(!spacePresent.contains(new Integer(nbSpace))){
                spacePresent.add(new Integer(nbSpace));
                //System.out.println("Number of space added");
            }
            
            
            if(nbSpace > nbSpaceOld)
            {
                //System.out.println("Old:"+nbSpaceOld +"Current:"+nbSpace);
                //System.out.println("Creation Noeud");
                dataList[i] = "-><"+nbSpace +">" + dataList[i];
                //System.out.println(dataList[i]);
                //System.out.println("->("+nbSpace+")"+dataList[i]);
            }
            else if(nbSpace < nbSpaceOld)
            {
                //System.out.println("Old:"+nbSpaceOld +"Current:"+nbSpace);
                //System.out.println("Sortie du noeud en cours");
                //System.out.println("<-("+nbSpace+")"+dataList[i]);
                dataList[i] = "<-<"+nbSpace+">"+dataList[i];
                //System.out.println(dataList[i]);
            }
            else
            {
                //System.out.println("On reste dans le meme noeud");
                //System.out.println("--("+nbSpace+")"+dataList[i]);
                dataList[i] = "--<"+nbSpace+">"+dataList[i];
                //System.out.println(dataList[i]);
            }
            
            nbSpaceOld = nbSpace;
            nbSpace = 0;
            //System.out.println("Out : "+dataList[i]);
            i++;
        }
        
        /*int j = 0;
        while(j < spacePresent.size()){
            System.out.println(""+spacePresent.get(j).intValue());
            j++;
        }*/
        
        spaceMapper();
        dataList = toPreFinal(dataList);
        
        String out = "";
        for(String str : dataList)
        {
            out = out + str + "\n";
            //System.out.println(str);
        }
        
        return out;
    }
    
    private void spaceMapper(){
//         spacePresent.sort(null);//trie croissant
         int i = 0;
         while(i<spacePresent.size())
         {
             spaceToDeep.put(spacePresent.get(i).intValue(),i+1);
             i++;
         }
         /*System.out.println("SpaceMapper:");//Affichage des correspondances dans la Map
         Set<Integer> set = spaceToDeep.keySet();
         Iterator<Integer> it = set.iterator();
         Integer temp;
         while(it.hasNext()){
             temp = it.next();
             System.out.println(temp+":"+spaceToDeep.get(temp));
         }*/
    }
    
    private String[] toPreFinal(String[] data){
        /*
        Mets les profondeur au bon endroit avec les bonnes profondeur !
        Se preparer pour envoyer vers XMLizer
        */
        System.out.println("ToPreFinal : ");
        int i = 0;
        while(i<data.length)
        {
            if(data[i].substring(0,3).equals("-><") || data[i].substring(0,3).equals("<-<") || data[i].substring(0,3).equals("--<"))
            {
                //Remplacer les nb d'espaces par des profondeurs
                int j = 3;//3 caracteres avant
                while(data[i].charAt(j) != '>' && j < data[i].length())
                {//Juste compteur
                    j++;
                }
                String nbSpace = data[i].substring(3,j);//On recup le nb d'espace
                
                int nbProfondeur = spaceToDeep.get(new Integer(nbSpace)).intValue();//On recupe la profondeur !
                //System.out.println("Avant : "+nbSpace+", apres : "+nbProfondeur);
                
                dataList[i] = dataList[i].replaceFirst("<"+nbSpace+">","<"+nbProfondeur+">");//On la mets la
                
            }
            else
                System.out.println("Erreur sur la ligne : "+i);
                
            
            i++;
        }
        return data;
    }
    
}
