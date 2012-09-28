package nlp.src;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * Main Class 
 *
 * @author JUAN.
 */
public class NewsSummarizer {
	
	/**
	 * LEXICON
	 */
	private static String LEXICON = "input/lexicon.txt";
	/**
	 * NEWS
	 */
	private static String NEWS = "input/inputnews_Lansdowne_1.txt";
	
	/**
	 * Lexicon words
	 */
	private ArrayList<String> lexicon;
	/**
	 * Input separated by sentences
	 */
	private String newsTitle;
	/**
	 * Input separated by paragraphs
	 */
	private ArrayList<NLPParagraph> paragraphs;
	/**
	 * Tagger
	 */
	private MaxentTagger tagger = new MaxentTagger("models/left3words-wsj-0-18.tagger");
	/**
	 * Classifier
	 */
	private String serializedClassifier = "classifier/muc.7class.distsim.crf.ser.gz";
	private AbstractSequenceClassifier classifier;
		
	
	/**
	 * Main Method
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public NewsSummarizer() throws IOException, ClassNotFoundException
	{
		this.paragraphs = new ArrayList<NLPParagraph>();
		this.lexicon = new ArrayList<String>();
		this.classifier = CRFClassifier.getClassifierNoExceptions(this.serializedClassifier);
		readLexicon();
		//readInput();
	}
	/**
	 * Reads Input.txt 
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readInput() throws IOException, ClassNotFoundException
	{		
		// Open the file that is the first 
		// command line parameter
		FileInputStream fstream = new FileInputStream(NEWS);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		//Read File Line By Line
		int num = 0;
		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			if (num == 0) {
				this.newsTitle = strLine;
			}else{
			this.paragraphs.add(new NLPParagraph(strLine,this.tagger,this.lexicon, this.classifier,this.newsTitle));
			//System.out.println(strLine);
			}
			num++;
	}
		//Close the input stream
		in.close();	
	}
	
	/**
	 * Reads Input from GUI 
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readInputFromGUI(String input) throws IOException, ClassNotFoundException
	{		
		this.paragraphs = new ArrayList<NLPParagraph>();
		BufferedReader br = new BufferedReader(new StringReader(input));
		String strLine;
		int num = 0;
		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			if (num == 0) {
				this.newsTitle = strLine;
			}else{
			this.paragraphs.add(new NLPParagraph(strLine,this.tagger,this.lexicon, this.classifier,this.newsTitle));
			//System.out.println(strLine);
			}
			num++;
		}
	}
	
	/**
	 * Reads lexicon.txt
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readLexicon() throws IOException, ClassNotFoundException
	{
		// Open the file that is the first 
		// command line parameter
		FileInputStream fstream = new FileInputStream(LEXICON);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			this.lexicon.add(strLine);
			//System.out.println(strLine);
		}
		//Close the input stream
		in.close();	
	}
	/**
	 * Compiles a String with a report on the diferent information of the news text
	 *
	 * @return report
	 */
	public String returnsReport(){
		int numberOfParagraphs = 0;
		String paragraphsTogeather = "";
		String report = "";
		report +="TITLE: "+this.newsTitle+"\n";
		for (int i = 0; i < this.paragraphs.size(); i++) {
			String resumen = this.paragraphs.get(i).returnsResumen();
			paragraphsTogeather +=resumen;
			numberOfParagraphs = i+1;
		}
		report +="Number Of Paragraphs: "+numberOfParagraphs+"\n";
		report +=paragraphsTogeather;
		
		return report;
		
	}
	/**
	 * Compiles a String with a report on the diferent information of the news text
	 *
	 * @return report
	 */
	public String returnsReportHTML(){
		int numberOfParagraphs = 0;
		String paragraphsTogeather = "";
		String report = "<html>";
		report +="<font size=+1><b>TITLE: "+this.newsTitle+"</b></font><br>";
		for (int i = 0; i < this.paragraphs.size(); i++) {
			String resumen = this.paragraphs.get(i).returnsResumenHTML();
			paragraphsTogeather +=resumen;
			numberOfParagraphs = i+1;
		}
		report +="Number Of Paragraphs: "+numberOfParagraphs+"<br>";
		report +=paragraphsTogeather;
		
		return report;
		
	}
}
