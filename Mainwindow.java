package stanford_1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mainwindow {
	static ArrayList <OAspect> Aspects = new ArrayList<OAspect>() ; 
	private static JFrame frmAspectBasedSentiment;
	private  static JPanel panel;
	private  static JTable table;

	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 * @throws IOException 
	 * 
	 */
	static NounsGenerator ng;

	public static ArrayList<String> getnoun() throws IOException{
		ng=new NounsGenerator("preprocessedFile","nounsFile");
		ng.generateAspects();
		return ng.getNounsListString();
	}
	


	 public static ArrayList<Aspect> getaspects() throws IOException, InterruptedException{
		   String a = "/home/emily/workspace/Extraction_2/trial.txt";
	      Preprocess preprocess = new Preprocess("trial.txt", "preprocessedFile");
			AspectsGenerator ag = new AspectsGenerator(getnoun());
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
			finalAspects.add("os");
			finalAspects.add("browser");
			finalAspects.add("Gps");
			finalAspects.add("bluetooth");
			finalAspects.add("wlan");
			finalAspects.add("cpu");
			finalAspects.add("radio");
			finalAspects.add("Technology");
			finalAspects.add("weight");
			finalAspects.add("sim");
			ag.finalizeAspects(finalAspects);

			// checking and debugging
			ArrayList<Aspect> ans;
			ans = ag.getAspects();

			for (Aspect aspect : ans) {
			//	System.out.println(aspect.getAspectName());
				//Aspects.add(new OAspect(aspect.getScore(), aspect.getAspectName()));
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
			for (Aspect aspect: Mainwindow.getaspects()) {
				
				for(String asp:aspect.getAspect()){
				
					s+=asp+" ";
					}
			}
			return s;
	}
public static String getSent() throws IOException, InterruptedException{
	
	String s="";
	for (Aspect aspect: Mainwindow.getaspects()) {
		
		for(String asp:aspect.getSenti()){
			
			s+=asp+" ";
			}
	}
	return s;
}
	 
	public static void emi() throws InterruptedException, IOException {
	
		/*
		 * 1. FIRST STEP INVOLVES PREPROCESSING, i.e, REMOVAL OF ERRANT SYMBOLS,
		 * STOP WORD REMOVAL, SENTENCE SEGMENTATION, REMOVAL OF UNNECESSARY
		 * SPACES
		 */
		//String a = "C:\\Users\\Helmi\\eclipse-workspace\\stanford\\trial.txt ;
		 String a = "/home/emily/workspace/Extraction_2/trial.txt";
		Preprocess preprocess = new Preprocess("trial.txt", "preprocessedFile");

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
		
		AspectsGenerator ag = new AspectsGenerator(ng.getNounsListString());
//		System.out.println("ag.getSuggestedAspects" + ag.getSuggestedAspects());

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
		finalAspects.add("os");
		finalAspects.add("browser");
		finalAspects.add("Gps");
		finalAspects.add("bluetooth");
		finalAspects.add("wlan");
		finalAspects.add("cpu");
		finalAspects.add("radio");
		finalAspects.add("Technology");
		finalAspects.add("weight");
		finalAspects.add("sim");
		
		
		ag.finalizeAspects(finalAspects);

		// checking and debugging
		ArrayList<Aspect> ans;
		ans = ag.getAspects();

		for (Aspect aspect : ans) {
			System.out.println("aspect.getAspectName: "+ aspect.getAspectName());
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
		
		
		//EXE POOL
		ExecutorService pool = Executors.newFixedThreadPool(ans.size());
		
		for (int i = 0; i < ans.size(); i++) {
			pool.submit(ans.get(i));
		}
		
		pool.submit(ans.get(0));
		pool.shutdown();
		pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		

		for (Aspect aspect: ans) {
			System.out.println("----------------------------------\n");
			System.out.println("AspectName::"+ aspect.getAspectName());
			System.out.println("OpinionWords :" + aspect.getOpinionWords());
			System.out.println("Score: "+ aspect.getScore());
			System.out.println("asp: "+ aspect.getAspect());
			System.out.println("sentiment: "+ aspect.getSenti());
			System.out.println("----------------------------------\n");
			Aspects.add(new OAspect(aspect.getScore(), aspect.getAspectName()));
			//OpinionWord a = new OpinionWord("processor","good","a",true);
			//System.out.println(a.value);
		//System.out.println(Aspects.size());
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainwindow window = new Mainwindow();
					window.frmAspectBasedSentiment.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mainwindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		frmAspectBasedSentiment = new JFrame();
		frmAspectBasedSentiment.setTitle("Smartphone Aspect Based Sentiment Analysis");
		frmAspectBasedSentiment.setBounds(100, 100, 753, 444);
		frmAspectBasedSentiment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		frmAspectBasedSentiment.getContentPane().setLayout(gridBagLayout);
		
		JButton Extract = new JButton("Extract");
		Extract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DefaultTableModel model = (DefaultTableModel) table.getModel();

				Object rawData[]= new Object [3];
				for (int i=0 ; i<Aspects.size() ; i++ ) {
					rawData[0]=Aspects.get(i).name;
					rawData[1]=Aspects.get(i).Score;
					rawData[2]=Aspects.get(i).sentivalue;
				System.out.println(Aspects.get(i).toString());
					
				model.addRow(rawData);
				}
			}
		});
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frmAspectBasedSentiment.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {

				{"Nom de l'aspect", "Score", "sentiment"},
			},
			new String[] {
				"Nom de l'aspect", "Score", "Sentiment "
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(117);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(125);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
		});
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel.add(table, gbc_table);
		GridBagConstraints gbc_Extract = new GridBagConstraints();
		gbc_Extract.gridx = 1;
		gbc_Extract.gridy = 1;
		frmAspectBasedSentiment.getContentPane().add(Extract, gbc_Extract);
	}
	public static void main (String[] args) throws InterruptedException, IOException{
		emi();
		initialize();
		ModeleRDF2 triple= new ModeleRDF2();
		triple.Createmodel();
}
}