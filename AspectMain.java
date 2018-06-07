package stanford_1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class AspectMain {
	
	static NounsGenerator ng;

	public static ArrayList<String> getnoun() throws IOException{
		ng=new NounsGenerator("preprocessedFile","nounsFile");
		ng.generateAspects();
		return ng.getNounsListString();
	}
	
	 public static  void requete(String str) throws IOException
     {            
  //String str= "Samsung";
  String queryString = "PREFIX pr:<http://xmlns.com/foaf/0.1/>\n" +
          "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
          "SELECT DISTINCT ?phone ?label WHERE {" + 
          "?phone rdfs:label ?label . "+
          "?phone <http://dbpedia.org/property/type> <http://dbpedia.org/resource/Smartphone>"+
          "FILTER (lang(?label) = 'en') . "+
          "?label <bif:contains> \""+str+"\" ."+
          "}LIMIT 1";

  Query query = QueryFactory.create(queryString);        
  QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
  try
   {
     ResultSet results = qexec.execSelect();
     while(results.hasNext()){
    QuerySolution soln = results.nextSolution();
   
    System.out.println(soln);
         }
      }
  finally{
       qexec.close();
   }
     }
	/*public static String getreq(){
		String m="";
	for(int i=0;i<ng.getNounsListString().size();i++)
		m=ng.getNounsListString().toString();
	
	}*/

	 public static ArrayList<Aspect> getaspects() throws IOException, InterruptedException{
		   String a = "/home/emily/workspace/Extraction_2/essai.txt";
	      Preprocess preprocess = new Preprocess("essai.txt", "preprocessedFile");
			AspectsGenerator ag = new AspectsGenerator(getnoun(), 15);
			System.out.println(ag.getSuggestedAspects());

			ArrayList<String> finalAspects = new ArrayList<>();
			finalAspects.add("camera");
			finalAspects.add("colour");
			finalAspects.add("color");
			finalAspects.add("battery");
			finalAspects.add("screen");
			finalAspects.add("processor");
			finalAspects.add("price");
			finalAspects.add("apps");
			finalAspects.add("sound");
			finalAspects.add("radio");
			ag.finalizeAspects(finalAspects);

			// checking and debugging
			ArrayList<Aspect> ans;
			ans = ag.getAspects();

			for (Aspect aspect : ans) {
			//	System.out.println(aspect.getAspectName());
			}
			preprocess.finalPreprocess("preprocessedFile", "opinionatedReviews", ans);
			
					Aspect.setReviewFile("opinionatedReviews");
			ExecutorService pool = Executors.newFixedThreadPool(ans.size());
			for (int i = 0; i < ans.size(); i++) {
				pool.submit(ans.get(i));
			}
			
			pool.submit(ans.get(0));
			pool.shutdown();
			
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			
			return ans;}
	 
public static String getAsp() throws IOException, InterruptedException{
	
			String s="";
			for (Aspect aspect: AspectMain.getaspects()) {
				//System.out.println
				for(String asp:aspect.getAspect()){
					//if(aspect.getAspect().equals(asp));
					s+=asp+" ";
					}
			}
			return s;
	}
public static String getSent() throws IOException, InterruptedException{
	
	String s="";
	for (Aspect aspect: AspectMain.getaspects()) {
		//System.out.println
		for(String asp:aspect.getSenti()){
			//if(aspect.getAspect().equals(asp));
			s+=asp+" ";
			}
	}
	return s;
}

	public static void main (String[] args) throws IOException, InterruptedException {

		/*
		 * 1. FIRST STEP INVOLVES PREPROCESSING, i.e, REMOVAL OF ERRANT SYMBOLS,
		 * STOP WORD REMOVAL, SENTENCE SEGMENTATION, REMOVAL OF UNNECESSARY
		 * SPACES
		 */
		//String a = "C:\\Users\\Helmi\\eclipse-workspace\\stanford\\trial.txt ;
		Preprocess preprocess = new Preprocess("essai.txt", "preprocessedFile");

		/*
		 * 2. THE SECOND STEP INVOLVES GENERATION OF NOUNS, i.e, GENERATE ALL
		 * POSSIBLE NOUN FORMS, PARSE THE PROCESSED REVIEWS, TO GENERATE NOUNS
		 * THE NOUNS ARE RECORDED AS "nounsFile" AND "parsedFile" CAN BE USED
		 * FOR DEBUGGING ONE MAJOR IMPROVEMENT-->IF THE "parsedFile" IS ALREADY
		 * PRESENT THEN PARSING WILL NOT TAKE PLACE AGAIN..THE PREVIOUS NOUN
		 * FILE WILL BE USED TO RETURN THE NOUNS
		 */
		NounsGenerator ng = new NounsGenerator("preprocessedFile","nounsFile");
		ng.generateAspects();
		System.out.println("NounsList: "+ ng.getNounsListString()+"\n");

		/*
		 * 3. THE THIRD STEP INVOLVES GENERATION OF ASPECTS FROM THE NOUNS BASED
		 * ON THE FREQUENCY PARAMETER SPECIAL "Aspect" OBJECT IS CREATED TO
		 * HANDLE EACH ASPECT.......ASPECTS NEED TO BE FINALISED AND COMITTED TO
		 * A FILE "finalizedAspects"
		 */
		
		AspectsGenerator ag = new AspectsGenerator(ng.getNounsListString(), 15);
		System.out.println("ag.getSuggestedAspects" + ag.getSuggestedAspects());

		ArrayList<String> finalAspects = new ArrayList<>();
		finalAspects.add("camera");
		finalAspects.add("colour");
		finalAspects.add("color");
		finalAspects.add("battery");
		finalAspects.add("screen");
		finalAspects.add("processor");
		finalAspects.add("price");
		finalAspects.add("apps");
		finalAspects.add("sound");
		finalAspects.add("radio");
		ag.finalizeAspects(finalAspects);

		// checking and debugging
		ArrayList<Aspect> ans;
		ans = ag.getAspects();

		for (Aspect aspect : ans) {
			System.out.println("aspect: "+ aspect.getAspectName());
		}

		/*
		 * 4. THE FOUTRH STEP INVOLVES FINAL PROCESSING OF THE
		 * "preprocessedFile" HERE ONLY THE LINES WHICH WILL HAVE THE ASPECTS
		 * MENTIONED IN THEM WILL BE INCLUDED FOR FURTHER EXTRACTION PROCEDURE.
		 * NEW FILE WILL BE CREATED FOR SUCH REVIEWS , "opinionatedReviews"
		 */
		
		preprocess.finalPreprocess("preprocessedFile", "opinionatedReviews", ans);
		
		/*
		 * 5. "THE" STEP FIVE
		 */
		//IMPORTANT IMPORTANT
		Aspect.setReviewFile("opinionatedReviews");
		/*//TRIAL
		Thread t1 = new Thread(ans.get(0));
		Thread t2 = new Thread(ans.get(1));
		t1.start();
		t2.start();
		*/
		
		//EXE POOL
		ExecutorService pool = Executors.newFixedThreadPool(ans.size());
		
		for (int i = 0; i < ans.size(); i++) {
			pool.submit(ans.get(i));
		}
		
		pool.submit(ans.get(0));
		pool.shutdown();
		pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		
		/*
		//Basic Thread Method - 6:10
		ArrayList<Thread> execute = new ArrayList<>();
		for (Aspect aspect: ans) {
			execute.add(new Thread(aspect));
		}
		
		for (Thread t: execute) {
			t.start();
		}
		
		for (Thread t: execute) {
			t.join();
		}
		*/
	
	/*	
		for (Aspect aspect: ans) {
			System.out.println(aspect.getAspectName());
			System.out.println("----------------------------------");
			aspect.generateAspects();
		}
		*/
		
		for (Aspect aspect: ans) {
			System.out.println("AspectName::"+ aspect.getAspectName());
			System.out.println("----------------------------------\n");
			System.out.println("OpinionWords :" + aspect.getOpinionWords());
			System.out.println("Aspect :" + aspect.getAspect());
			System.out.println("Sentiment :" + aspect.getSenti());
			System.out.println("Score: "+ aspect.getScore());
			//OpinionWord a = new OpinionWord("processor","good","a",true);
			//System.out.println(a.value);
		}
	}
	/*public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println(AspectMain.getAsp());
		System.out.println(AspectMain.getSent());
	}*/
}