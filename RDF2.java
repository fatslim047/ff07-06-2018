package stanford_1;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.tdb.TDBFactory;
public class RDF2 {
	Aspect asp;
	static String texteURI="http://texte/";
	static String relationURI1="http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	static String  relationURI2="http://www.example.org/tn#";
	static String directory = "/home/emily/workspace/Extraction_2/database"; 
	static  String namedModelURI = "http://modele/";
	
public void Createmodel() throws IOException, InterruptedException{
	
	
	// Create an empty Model
	 Dataset dataset = TDBFactory.createDataset(directory) ;
	Model model = dataset.getNamedModel(namedModelURI);
	
	Resource texte = null;
	Resource aspect=null;
	Resource sentiment=null;
	Resource type=null;
	// Create a Resource
	CreateTriple cr=new CreateTriple();
	for(int i=0;i<cr.getAsp().length;i++){
	 texte = model.createResource(texteURI+"/"+(cr.getTexte())[i]);
     aspect= model.createResource(texteURI+"/"+(cr.getAsp())[i]);
	 sentiment = model.createResource(texteURI+"/"+(cr.getSentis())[i]);
	 type = model.createResource(texteURI+"/"+cr.getType());
	 
	Property has= model.createProperty(relationURI1 , "has");
	Property prop = model.createProperty(relationURI2 , "prop");	
	
	
	// Can also create properties directly . . .
	
	//statement
	Statement stmt1 = model.createStatement(texte ,  has   , type);
	Statement stmt2 = model.createStatement(texte  , has   , aspect);
	Statement stmt3 = model.createStatement(aspect  , prop , sentiment);
	
	//add statement
	model.add(stmt1);
	model.add(stmt2);
	model.add(stmt3);
	//add property
	model.setNsPrefix("relation", relationURI2);
	
	}
	  model.write(new FileWriter("essai.rdf"));
	   model.write(new FileWriter("essai.rdf"), "RDF/XML-ABBREV");
	   
	  
	model.close();
	dataset.close();
}

	private void testQuerymodele(){
		 Dataset dataset = TDBFactory.createDataset(directory) ;
			Model model = dataset.getNamedModel(namedModelURI);
			
			Property has= model.createProperty(relationURI1 , "has");
			Property prop = model.createProperty(relationURI2 , "prop");	
			
	 ResIterator iter = model.listSubjectsWithProperty(has);

    while (iter.hasNext()) {
       Resource rsc = iter.nextResource();  // obtenir la prochaine déclaration
       System.out.println(rsc.getURI());
        }
		model.close();	            
	}


	public static void main (String[] args) throws IOException, InterruptedException{
		RDF2 triple= new RDF2();
		triple.Createmodel();
		triple.testQuerymodele();

	}
}
