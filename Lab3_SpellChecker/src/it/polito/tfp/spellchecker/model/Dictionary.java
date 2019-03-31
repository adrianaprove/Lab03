package it.polito.tfp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Dictionary {
	
	Map <String, String> dizionario; 
	LinkedList <RichWord> parole; 
	
	//LinkedList <String> dizionario_lista; 
	ArrayList <String> dizionario_lista; 
	
	public void loadDictionary(String language) {
		dizionario=new TreeMap <String, String>(); 
		//dizionario_lista=new LinkedList<String>(); 
		dizionario_lista=new ArrayList <String>(); 
		
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
				dizionario_lista.add(word); 
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
	
	//ricerca lineare
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		boolean trovato; 
		parole=new LinkedList <RichWord>(); 
	
		for(int i=0; i<inputTextList.size(); i++) {
			trovato=false; 
			RichWord rw=new RichWord(inputTextList.get(i)); 		
			for(int j=0; j<dizionario_lista.size() && !trovato; j++) {
				if(inputTextList.get(i).compareTo(dizionario_lista.get(j))==0) {
					trovato=true; 
					rw.setCorretta(true);}
				}
			parole.add(rw); 
			}
		return parole; 
		}
	
	
	//ricerca dicotomica
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		parole=new LinkedList <RichWord>(); 
		boolean trovato; 
		for(int i=0; i<inputTextList.size(); i++) {
			trovato=false; 
			RichWord rw=new RichWord(inputTextList.get(i)); 
			int meta=dizionario_lista.size()/2; 
			
			if(rw.getParola().compareTo(dizionario_lista.get(meta))==0) {
				trovato=true; 
				rw.setCorretta(true);
			} else if(rw.getParola().compareTo(dizionario_lista.get(meta))>0) {
				// vuol dire che viene prima la parola inserita dell'utente
				// ossia fa parte della prima metà del dizionario -> cerco solo nella prima metà 
				for(int j=0; j<meta && !trovato; j++) {
					if(inputTextList.get(i).compareTo(dizionario_lista.get(j))==0) {
						trovato=true; 
						rw.setCorretta(true);}
					}
				} else {
				// vuol dire che viene dopo la parola inserita dell'utente
				// ossia fa parte della seconda metà del dizionario -> cerco solo nella seconda metà 
				for(int j=meta; j<dizionario_lista.size() && !trovato; j++) {
					if(inputTextList.get(i).compareTo(dizionario_lista.get(j))==0) {
						trovato=true; 
						rw.setCorretta(true);
						}
					}
				}
			parole.add(rw);
		}
		return parole; 
	}

}