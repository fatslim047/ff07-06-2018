package stanford_1;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class AspectM {
	 public  void main (String[]args) throws IOException
     {            
  String str= "Samsung";
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
}

		
	

