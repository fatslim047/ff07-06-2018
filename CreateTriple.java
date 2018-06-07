package stanford_1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class CreateTriple {
	private String texte="";
	private String asp;
	private ArrayList<String> liste;
	private String noun;
	private ArrayList<String> ls;
	private String type;
	private String senti;
	public CreateTriple() throws IOException, InterruptedException{
		
		Scanner fr=new Scanner(new FileReader("essai.txt"));
		while(fr.hasNext()){
	     texte+=(fr.nextLine()+"--");
	     new FileReader("trial.txt").close();
		}
		 
		System.out.println(texte);
		asp=AspectMain.getAsp();
		noun =AspectMain.getnoun().get(0);
		senti=AspectMain.getSent();					
				
}
	
public String[] getTexte(){
	return texte.split("--");
}
public String[] getAsp(){
	return asp.split(" ");
}
public String getType() throws IOException {
	
	return noun;
	}
public String[] getSentis(){
	return senti.split(" ");
}

	
}
