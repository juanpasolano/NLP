package nlp.src;

import java.io.IOException;
import java.util.ArrayList;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * This class manages all the methods for each paragraph. 
 * This class handles the POS Tagger of Stanford
 * This class uses the class NLPParagrapherNER to handle the NER Lybrary of Stanford (Because of incompatibility with the tow libraries)
 * For example return the verbs of the paragraph, or the words on the lexicon
 *
 * @author juan.
 */
public class NLPParagraph {


	private String paragraphNumbered;
	private ArrayList<String> verbs;
	private ArrayList<String> adjectives;
	private ArrayList<String> cardinalNumbers;
	private ArrayList<String> organizations;
	private ArrayList<String> dates;
	private ArrayList<String> people;
	private ArrayList<String> percent;
	private ArrayList<String> money;
	private ArrayList<String> location;
	private ArrayList<String> time;
	private ArrayList<String> wordsOnLexicon;
	private ArrayList<String> wordsOnTitle;

	/**
	 * Array of ACTORS and NUMBERS for SVO Structure
	 */
	private ArrayList<String> ACTORS;
	private ArrayList<String> NUMBERS;
	private String[] svoResult;
	private ArrayList<String> surrowndings;
	
	/**
	 * The paragrapf as a String
	 */
	private String paragraphItself;
	
	/**
	 * Constructor necessary to build an object NLPParagraph
	 */
	MaxentTagger tagger;
	/**
	 * Object used because the NER Library cannot work along the POS Tagger
	 */
	NLPParagraphNER nLPParagraphNER;
	/**
	 * Object used because the NER Library cannot work along the POS Tagger
	 */
	NLPParagraphPOS nLPParagraphPOS;
	/**
	 * Constructor of the Object NLPParagraph created for each of the paragraph of the news
	 *
	 * @param inputParagraph
	 * @param inputTagger
	 * @param lexicon
	 * @param classifier2
	 * @param newsTitle
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public NLPParagraph(String inputParagraph,  MaxentTagger inputTagger, ArrayList<String> lexicon,  AbstractSequenceClassifier<?> classifier2, String newsTitle) throws IOException, ClassNotFoundException
	{
		this.paragraphItself = inputParagraph;
		this.tagger = inputTagger; 
		
		//Creating object to handle NER lybrary
		this.nLPParagraphNER = new NLPParagraphNER(this.paragraphItself, classifier2);
		this.organizations = this.nLPParagraphNER.returnEntities(NLPParagraphNER.ORGANIZATION);
		this.dates = this.nLPParagraphNER.returnEntities(NLPParagraphNER.DATE);
		this.people = this.nLPParagraphNER.returnEntities(NLPParagraphNER.PERSON);
		this.percent = this.nLPParagraphNER.returnEntities(NLPParagraphNER.PERCENT);
		this.money =  this.nLPParagraphNER.returnEntities(NLPParagraphNER.MONEY);
		this.location = this.nLPParagraphNER.returnEntities(NLPParagraphNER.LOCATION);
		this.time = this.nLPParagraphNER.returnEntities(NLPParagraphNER.TIME);
		
		//Getting other values with the POS Tagger
		this.nLPParagraphPOS = new NLPParagraphPOS(this.paragraphItself, inputTagger);
		this.verbs = nLPParagraphPOS.returnVerbs();
		this.adjectives = nLPParagraphPOS.returnAdjectives();
		this.cardinalNumbers = nLPParagraphPOS.returnCardinalNumbers();
		this.wordsOnLexicon = nLPParagraphPOS.returnWordsOnLexicon(lexicon);
		this.wordsOnTitle = nLPParagraphPOS.reurnsWordsOnTitle(newsTitle);
		this.paragraphNumbered = nLPParagraphPOS.returnParagraphNumberded(inputParagraph);
		
		//Combaining arrays into an ordered array for SVO Structure
		this.ACTORS = order4Arrays(this.organizations,this.people,this.wordsOnLexicon,this.location);
		this.NUMBERS = order3Arrays (this.cardinalNumbers,this.money,this.percent);
		
		SVOStructure svo = new SVOStructure(this.ACTORS,this.verbs,this.NUMBERS,this.paragraphItself, this.paragraphNumbered);
		
		this.svoResult = svo.getSVOStructure();
		this.surrowndings = svo.getObjectSurrownd(3);
		
		
		
	}
	
	
	/**
	 * Prints a report of all mayor data extracted from the paragraph
	 * @return resumen
	 *
	 */
	public String returnsResumen() {
		
		String resumen = "";
		resumen +="\n ---***--- \n";
		resumen +="PARAGRAPH\n\n";
		
		resumen +=this.paragraphItself+"\n\n";
		
		resumen +=this.paragraphNumbered+"\n";
		
		resumen +="---Class SVO Structure---\n\n";
		
		resumen +="ACTORS: "+this.ACTORS +"\n";
		resumen +="VERBS: "+this.verbs +"\n";
		resumen +="NUMBERS: "+this.NUMBERS +"\n\n";
		
		resumen +="SVO STRUCTURE: "+this.svoResult[0]+" "+this.svoResult[1]+" "+this.svoResult[2]+"\n\n";
		resumen +="SURROWNDING: "+this.surrowndings+"\n\n";
		
		resumen +="---Keywords--- \n\n";
		resumen +="Words on Title: "+this.wordsOnTitle+"\n";
		resumen +="Words on Lexicon: "+this.wordsOnLexicon+"\n";
		resumen +="Verbs: "+this.verbs+"\n";
		resumen +="Organizations: "+this.organizations+"\n";
		resumen +="Cardinal Numbers: "+this.cardinalNumbers+"\n";
		resumen +="People: "+this.people+"\n";
		resumen +="Money: "+this.money+"\n";
		resumen +="Percentages: "+this.percent+"\n";
		resumen +="Dates: "+this.dates+"\n";
		resumen +="Locations: "+this.location+"\n";
		resumen +="Time: "+this.time+"\n";
		resumen +="Adjectives: "+this.adjectives+"\n";
		resumen +="\n";
		
		return resumen;
	}


	/**
	 * Prints a report of all mayor data extracted from the paragraph
	 * @return resumen
	 *
	 */
	public String returnsResumenHTML() {
		
		String resumen = "";
		resumen +="<br> ---***--- <br>";
		resumen +="<font size=+1><b>PARAGRAPH</b></font><br><br>";
		
		resumen +="<b>"+this.paragraphItself+"</b><br><br>";
		
		resumen +=this.paragraphNumbered+"<br><br>";
		
		resumen +="<b>---Class SVO Structure---</b><br><br>";
		
		resumen +="<font size=+0><b>ACTORS: </b></font>"+this.ACTORS +"<br>";
		resumen +="<font size=+0><b>VERBS: </b></font>"+this.verbs +"<br>";
		resumen +="<font size=+0><b>NUMBERS: </b></font>"+this.NUMBERS +"<br><br>";
		
		resumen +="<font size=+0 color=Red><b>SVO STRUCTURE: "+this.svoResult[0]+" "+this.svoResult[1]+" "+this.svoResult[2]+"</b></font><br><br>";
		resumen +="<font size=+0 color=Blue><b>SURROWNDING: "+this.surrowndings+"</b></font><br><br>";
		
		resumen +="---Keywords--- <br><br>";
		resumen +="<font size=+0><b>Words on Title: </b></font>"+this.wordsOnTitle+"<br>";
		resumen +="<font size=+0><b>Words on Lexicon: </b></font>"+this.wordsOnLexicon+"<br>";
		resumen +="<font size=+0><b>Verbs: </b></font>"+this.verbs+"<br>";
		resumen +="<font size=+0><b>Organizations: </b></font>"+this.organizations+"<br>";
		resumen +="<font size=+0><b>Cardinal Numbers: </b></font>"+this.cardinalNumbers+"<br>";
		resumen +="<font size=+0><b>People: </b></font>"+this.people+"<br>";
		resumen +="<font size=+0><b>Money: </b></font>"+this.money+"<br>";
		resumen +="<font size=+0><b>Percentages: </b></font>"+this.percent+"<br>";
		resumen +="<font size=+0><b>Dates: </b></font>"+this.dates+"<br>";
		resumen +="<font size=+0><b>Locations: </b></font>"+this.location+"<br>";
		resumen +="<font size=+0><b>Time: </b></font>"+this.time+"<br>";
		resumen +="<font size=+0><b>Adjectives: </b></font>"+this.adjectives+"<br>";
		resumen +="<br>";
		
		return resumen;
	}


	/**
	 * Returns an Array of Verbs using POS Tagger (VBG, VBD, VBG, VBN, VBP, VBZ)
	 *
	 * @return verbs
	 */
	/*
	public ArrayList<String> returnVerbs()
	{
		ArrayList<String> verbs = new ArrayList<String>();
		Reader reader = new StringReader(this.paragraphItself);
        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(reader);

		
        String paragraphNumbered = returnParagraphNumberded(this.paragraphItself);
        String[] prw = paragraphNumbered.split(" ");
		  
        for (List<HasWord> sentence : sentences) {
          ArrayList<TaggedWord> tSentence = this.tagger.tagSentence(sentence);
          ////System.out.println(Sentence.listToString(tSentence, false));
          for (int i = 0; i < tSentence.size(); i++) {
        	  String taggedWord = tSentence.get(i)+"";        	 
        	  if(taggedWord.endsWith("/VB")||taggedWord.endsWith("/VBD")||taggedWord.endsWith("/VBG")||taggedWord.endsWith("/VBN")||taggedWord.endsWith("/VBP")||taggedWord.endsWith("/VBZ"))
        	  {
        		  for (int j = 0; j < prw.length; j++) {
        			  String[] w = prw[j].split("/");
        			  String[] t = taggedWord.split("/");
        			  if (t[0].equalsIgnoreCase(w[0])) {
                		  verbs.add(taggedWord+"/"+w[w.length-1]);
					}
        			  
					
				}
        	  }
			}
        }
		return verbs;
	}
	*/
	/**
	 * Returns an Array of Verbs using POS Tagger (JJ, JJR, JJS)
	 *
	 * @return adjectives
	 */
	/*
	public ArrayList<String> returnAdjectives()
	{
		ArrayList<String> adjectives = new ArrayList<String>();
		Reader reader = new StringReader(this.paragraphItself);
        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(reader);
        
        String paragraphNumbered = returnParagraphNumberded(this.paragraphItself);
        String[] prw = paragraphNumbered.split(" ");
        
        for (List<HasWord> sentence : sentences) {
          ArrayList<TaggedWord> tSentence = this.tagger.tagSentence(sentence);

          for (int i = 0; i < tSentence.size(); i++) {
        	  String taggedWord = tSentence.get(i)+"";
        	  if(taggedWord.endsWith("/JJ")||taggedWord.endsWith("/JJR")||taggedWord.endsWith("/JJS"))
        	  {
        		  for (int j = 0; j < prw.length; j++) {
        			  String[] w = prw[j].split("/");
        			  String[] t = taggedWord.split("/");
        			  if (t[0].equalsIgnoreCase(w[0])) {
        				  adjectives.add(taggedWord+"/"+w[w.length-1]);
					}
        		 }
        	  }
			}
        }
		return adjectives;
	}
	*/
	
	/**
	 * Returns Cardinal Numbers using POS Tagger (CD)
	 * 
	 * @return cardinalNumbers
	 */
	/*
	public ArrayList<String> returnCardinalNumbers()
	{
		ArrayList<String> cardinalNumbers = new ArrayList<String>();
		ArrayList<String> cardinalNumbersOut = new ArrayList<String>();
		Reader reader = new StringReader(this.paragraphItself);
        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(reader);
        for (List<HasWord> sentence : sentences) {
          ArrayList<TaggedWord> tSentence = this.tagger.tagSentence(sentence);
          ////System.out.println(Sentence.listToString(tSentence, false));
          for (int i = 0; i < tSentence.size(); i++) {
        	  String taggedWord = tSentence.get(i)+"";
        	  if(taggedWord.endsWith("/CD"))
        	  {
        		  cardinalNumbers.add(taggedWord.replace("/CD",""));
        		 // //System.out.println(taggedWord);
        	  }
			}
        }
        String moneyString = "";
        for (int i = 0; i < this.money.size(); i++) {
			moneyString += this.money.get(i);
		}
        for (int i = 0; i < cardinalNumbers.size(); i++) {
        	
        	Reader reader1 = new StringReader(moneyString);
            List<List<HasWord>> sentences1 = MaxentTagger.tokenizeText(reader1);
            for (List<HasWord> sentence : sentences1) {
              ArrayList<TaggedWord> tSentence = this.tagger.tagSentence(sentence);
              ////System.out.println(Sentence.listToString(tSentence, false));
              for (int j = 0; j < tSentence.size(); j++) {
            	  String taggedWord = tSentence.get(j)+"";
            	  
            	  for (int k = 0; k < cardinalNumbers.size(); k++) {
            		  if (taggedWord.equalsIgnoreCase(cardinalNumbers.get(k))) {
      					//nothing happens
                  	  }else{
                  		  cardinalNumbersOut.add(cardinalNumbers.get(k));
                  	  }
				}
            	  
            	  
            	 
    			}
            }
		}
		return cardinalNumbers;
		//return cardinalNumbersOut;
	}
	*/
	/**
	 * Returns all the words in the sentence given a lexicon
	 *
	 * @param lexicon
	 * @return wordsOnLexicon
	 */
	/*
	public ArrayList<String> returnWordsOnLexicon(ArrayList<String> lexicon)
	{
		ArrayList<String> wordsOnLexicon = new ArrayList<String>();
		Morphology mp = new Morphology();
        String noCommasSample = this.paragraphItself.replaceAll(",","");
        String noPointsSample = noCommasSample.replaceAll("\\.",""); 
        String[] arrayWordsforStem = noPointsSample.split(" ");
        for (int i = 0; i < arrayWordsforStem.length; i++) {
        	////System.out.println(arrayWordsforStem[i]);
        	for (int j = 0; j < lexicon.size(); j++) {
        		String stem = mp.stem(arrayWordsforStem[i]);
        		if(stem != null)
        		{
	        		if(mp.stem(arrayWordsforStem[i]).equalsIgnoreCase(lexicon.get(j)))
	        		{
	        			wordsOnLexicon.add(arrayWordsforStem[i]+"/"+i);
	        			//System.out.println(lexicon.get(j));
	        			break;
	        		}
        		}
			}
      }
       return wordsOnLexicon;
	}
	*/
	/**
	 * Returns bervs on Title present in the paragraph
	 *
	 * @param newsTitle
	 * @return wordsOnTitle
	 */
	/*
	public ArrayList<String> reurnsWordsOnTitle(String newsTitle){
		ArrayList<String> wordsOnTitle = new ArrayList<String>();
		Morphology mp = new Morphology();
		
		//Deliting commas and points from title
        String noCommasSample = newsTitle.replaceAll(",","");
        String noPointsSample = noCommasSample.replaceAll("\\.",""); 
        String[] arrayWordsTitle = noPointsSample.split(" ");
        
        //Deliting commas and points from paragraphs
        noCommasSample = this.paragraphItself.replaceAll(",","");
        noPointsSample = noCommasSample.replaceAll("\\.",""); 
        String[] paragraphWords = noPointsSample.split(" ");
		
		for (int i = 0; i < arrayWordsTitle.length; i++) {
        	////System.out.println(arrayWordsforStem[i]);
			String titleStemed = mp.stem(arrayWordsTitle[i]);
			
        	for (int j = 0; j < paragraphWords.length; j++) {
        		
    			String paragraphStemed = mp.stem(paragraphWords[j]);
    			
        		if(titleStemed.equalsIgnoreCase(paragraphStemed))
        		{
        			wordsOnTitle.add(paragraphStemed);
        			//System.out.println(lexicon.get(j));
        			//break;
        		}
        		
			}
		}
		
		return wordsOnTitle;
		
	}
	*/
	/**
	 * Returns the paragraph numberd by words
	 *
	 * @param inputParagraph
	 * @return
	 */
	/*
	private String returnParagraphNumberded(String inputParagraph) {
		String paragraphNumberedString = "";
		String[] paragraphNumberedArray = inputParagraph.split(" ");
		for (int i = 0; i < paragraphNumberedArray.length; i++) {
			//System.out.println(paragraphNumberedArray[i]);
			String parsing = paragraphNumberedArray[i]+"/"+i+" ";
			paragraphNumberedString += ""+parsing;
		}
		return paragraphNumberedString;
	}
*/

	/**
	 * Returns one Array from 3 others. It also orders the 3 arrays 
	 * with structure Keyword/PositionValue according to the Position Value
	 *
	 * @param A1
	 * @param A2
	 * @param A3
	 * @return joinOrdered
	 */
	private ArrayList<String> order3Arrays(ArrayList<String> A1,ArrayList<String> A2, ArrayList<String> A3) {
		
		ArrayList<String> join = new ArrayList<String>();
		for (int i = 0; i < A1.size(); i++) {
			join.add(A1.get(i));
		}
		for (int i = 0; i < A2.size(); i++) {
			join.add(A2.get(i));
		}
		for (int i = 0; i < A3.size(); i++) {
			join.add(A3.get(i));
		}
		ArrayList<String> joinOrdered = new ArrayList<String>();
		String[] m = this.paragraphItself.split(" ");
		
		for (int i = 0; i < m.length; i++) {
			String n = i+"";
			for (int j = 0; j < join.size(); j++) {
			String[] joinSplit = join.get(j).split("/");
				if (joinSplit[joinSplit.length-1].equalsIgnoreCase(n)) {
				joinOrdered.add(join.get(j));
				}
			}
		} 
		return joinOrdered;
	}


	/**
	 * Returns one Array from 4 others. It also orders the 4 arrays 
	 * with structure Keyword/PositionValue according to the Position Value
	 * 
	 * @param A1
	 * @param A2
	 * @param A3
	 * @param A4
	 * @return joinOrdered
	 */
	private ArrayList<String> order4Arrays(ArrayList<String> A1,ArrayList<String> A2, ArrayList<String> A3, ArrayList<String> A4) {
		
		ArrayList<String> join = new ArrayList<String>();
		for (int i = 0; i < A1.size(); i++) {
			join.add(A1.get(i));
		}
		for (int i = 0; i < A2.size(); i++) {
			join.add(A2.get(i));
		}
		for (int i = 0; i < A3.size(); i++) {
			join.add(A3.get(i));
		}
		for (int i = 0; i < A4.size(); i++) {
			join.add(A4.get(i));
		}
		ArrayList<String> joinOrdered = new ArrayList<String>();
		String[] m = this.paragraphItself.split(" ");
		
		for (int i = 0; i < m.length; i++) {
			String n = i+"";
			for (int j = 0; j < join.size(); j++) {
			String[] joinSplit = join.get(j).split("/");
				if (joinSplit[joinSplit.length-1].equalsIgnoreCase(n)) {
				joinOrdered.add(join.get(j));
				}
			}
		} 
		return joinOrdered;
	}


	/**
	 * Gets the minimal value of an array
	 *
	 * @param numbers
	 * @return minValue
	 */
	public static int getMinValue(int[] numbers){
		  int minValue = numbers[0];
		  for(int i=1;i<numbers.length;i++){
		    if(numbers[i] < minValue){
			  minValue = numbers[i];
			}
		  }
		  return minValue;
		}
	
	
}
