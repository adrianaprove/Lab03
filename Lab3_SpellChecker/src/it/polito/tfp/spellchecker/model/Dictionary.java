package it.polito.tfp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Dictionary {
	
	Map <String, String> dizionario; 
	LinkedList <RichWord> parole; 
	
	public void loadDictionary(String language) {
		dizionario=new TreeMap <String, String>(); 
		try {
			FileReader fr=null; 
			if(language.compareTo("Italian")==0)
				fr=new FileReader("rsc/Italian.txt"); 
			else if(language.compareTo("English")==0)
				fr=new FileReader("rsc/English.txt"); 
			BufferedReader br=new BufferedReader(fr); 
			String word; 
			while((word=br.readLine())!=null) {
				dizionario.put(word, word); 
			}
			br.close();
			
		} catch(IOException e) {
			System.out.println("Errore nella lettura del file"); 
		}
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		parole=new LinkedList <RichWord>(); 
		for(int i=0; i<inputTextList.size(); i++) {
			RichWord rw=new RichWord(inputTextList.get(i)); 
			if(dizionario.containsKey(inputTextList.get(i))){
				//la parola è corretta
				rw.setCorretta(true);
			}
			parole.add(rw); 
		}
		return parole; 
	}

}