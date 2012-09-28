package nlp.src;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;

public class NLPParagraphNER {

	/*
	 * 
	 */
	private String paragraphItself;
	/*
	 * The Classifier
	 */
	AbstractSequenceClassifier classifier;
	/*
	 * Possible categories of the entities in the sentence
	 */
	public static String LOCATION ="LOCATION";
	public static String TIME = "TIME";
	public static String PERSON = "PERSON";
	public static String ORGANIZATION = "ORGANIZATION";
	public static String MONEY = "MONEY";
	public static String PERCENT = "PERCENT";
	public static String DATE = "DATE";
	
	public NLPParagraphNER(String paragraph2, AbstractSequenceClassifier classifier2)
	{
		paragraphItself = paragraph2;
		classifier = classifier2;
	}
	
	/*
	 * Returns all the entities given a specific category in the sentence
	 */
	public ArrayList<String> returnEntities(String category)
	{
		ArrayList<String> entities = new ArrayList<String>();
		String s = classifier.classifyWithInlineXML(paragraphItself);
		Pattern p = Pattern.compile("(\\<\\b"+category+"\\b\\>)(.*?)(\\<\\/\\b"+category+"\\b\\>)");
        Matcher m = p.matcher(s);
        ArrayList<String> matchesNER = new ArrayList<String>();
        while (m.find()) {
        	matchesNER.add(m.group(2));
        }
        
        ArrayList<String> mathesNERWeight = new ArrayList<String>();
        String replaceParagraph = paragraphItself.replace(",","");
        String[] splitParagraph = replaceParagraph.split(" ");
        //ArrayList<String> splitParagraph = paragraphItself.split(" ");
			//splitParagraph[i] += "/"+i; 
        for (int i = 0; i < splitParagraph.length; i++) { 
        	for (int j = 0; j < matchesNER.size(); j++) {
				String[] matchesNERSplit = matchesNER.get(j).split(" ");    
					for (int k = 0; k < matchesNERSplit.length; k++) {
						
						try {
							if (splitParagraph[i].equalsIgnoreCase(matchesNERSplit[k])) {
								String keyWord = matchesNER.get(j);
								keyWord +="/"+i;
								mathesNERWeight.add(keyWord);
								//System.out.println(splitParagraph[i]+":"+matchesNERSplit[k]);
								i+=matchesNERSplit.length;
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						}
					}
				}
			
			
		
        //System.out.println(splitParagraph[0]);
        //System.out.println(matches_org);
		return mathesNERWeight;
	}
}
