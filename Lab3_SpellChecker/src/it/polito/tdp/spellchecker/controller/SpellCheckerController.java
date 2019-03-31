package it.polito.tdp.spellchecker.controller;


import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tfp.spellchecker.model.Dictionary;
import it.polito.tfp.spellchecker.model.RichWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SpellCheckerController {
	
	ObservableList <String> languages=FXCollections.observableArrayList("English", "Italian"); 

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox tndLanguage;

    @FXML
    private TextArea txtWords;

    @FXML
    private Button btnCheck;

    @FXML
    private TextField txtResult;

    @FXML
    private Label txtErrori;

    @FXML
    private Button btnClear;

    @FXML
    private Label txtTime;
    
    private Dictionary model; 

    @FXML
    void doClear(ActionEvent event) {
    	txtResult.clear(); 
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	txtResult.clear();
    	int cont=0; 
    	double start=System.nanoTime(); 
    	String s=txtWords.getText().replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_'()\\[\\]\"]", " "); 
    	String parole[]=s.split(" "); 
    	List <String> par=new LinkedList<String>();
    	for(int i=0; i<parole.length; i++) {
    		if(parole[i].length()>0)
    			par.add(parole[i].toLowerCase()); 
    		}
    	String lingua_scelta=(String) tndLanguage.getValue(); 
    	model.loadDictionary(lingua_scelta);
    	List <RichWord> output=model.spellCheckText(par);
    	//List <RichWord> output=model.spellCheckTextLinear(par);
    	//List <RichWord> output=model.spellCheckTextDichotomic(par);
    	String op=""; 
    	for(int i=0; i<output.size(); i++) {
    		if(!output.get(i).isCorretta()) {
    			op+="\n "+output.get(i).getParola(); 
    			cont++; 
    		}
    	}
    	txtResult.setText(op);
    	double end=System.nanoTime();
    	double diff=end-start; 
    	txtErrori.setText("The text contains "+cont+" errors");
    	txtTime.setText("Spell check completed in "+diff+" seconds");
    }

    @FXML
    void initialize() {
        assert tndLanguage != null : "fx:id=\"tndLanguage\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtWords != null : "fx:id=\"txtWords\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnCheck != null : "fx:id=\"btnCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtTime != null : "fx:id=\"txtTime\" was not injected: check your FXML file 'SpellChecker.fxml'.";

        tndLanguage.setValue("English");
        tndLanguage.setItems(languages);
    }

	public void setModel(Dictionary model) {
		this.model=model; 
	}
}
