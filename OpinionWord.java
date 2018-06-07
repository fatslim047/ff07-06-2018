package stanford_1;

import java.io.IOException;

public class OpinionWord {
public String Aspect ; 	
public String word ;
public boolean  negation ;
public double value ; 
public String pos ;
public String sentiment;


	public OpinionWord( String Aspect , String word, String pos, boolean negation) throws IOException {
	SentiWordNetDemoCode senti = new SentiWordNetDemoCode("/home/emily/workspace/Extraction_2/sentiword.txt");
	this.word = word;
	this.negation = negation;
	this.Aspect= Aspect; 
	this.pos = pos;
	if (negation == false) {
		value = senti.extract(word, pos);
	}
	else
		value=-(senti.extract(word,pos));
	if (pos=="v" && value==0 )
	{
		pos="a";
	this.pos="a";
		if (negation==false)
			value=senti.extract(word,pos);
		else
		value = senti.extract(word,pos);
	}
	
	if(value<0 && value>-1)
		
		sentiment="negatif";
	else if(value<1 && value>0)
		
		sentiment="positif";
	else
		sentiment="neutre";
}

	public String getaspect() {
		return Aspect;
	}

	
   public String toString() {
	
	return "Opinionword [ Aspect= "+Aspect+" word=" + word + ", negation=" + negation + ",   pos=" + pos +" its value="+value+ ", sentiment="+sentiment+"]";
	
}

	public void setAspect(String aspect) {
		Aspect = aspect;
	}
	public String getSentiment(){
		return sentiment;
	}
}

