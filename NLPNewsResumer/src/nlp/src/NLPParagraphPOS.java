package nlp.src;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class NLPParagraphPOS {
	
	/**
	 * Each of the paragraphs of the news
	 */
	private String paragraphItself;

	private ArrayList<String> money;

	/**
	 * Constructor necessary to build an object NLPParagraph
	 */
	MaxentTagger tagger;
	
	/**
	 * Main method
	 */
	public NLPParagraphPOS(String paragraph,   MaxentTagger inputTagger)
	{
		paragraphItself = paragraph;
		tagger = inputTagger;
	}
	

	/**
	 * Returns an Array of Verbs using POS Tagger (VBG, VBD, VBG, VBN, VBP, VBZ)
	 *
	 * @return verbs
	 */
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
	/**
	 * Returns an Array of Verbs using POS Tagger (JJ, JJR, JJS)
	 *
	 * @return adjectives
	 */
	public ArrayList<String> returnAdjectives()
	{
		ArrayList<String> adjectives = new ArrayList<String>();
		Reader reader = new StringReader(this.paragraphItself);
        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(reader);

		System.out.println(sentences);
        
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
	
	
	/**
	 * Returns Cardinal Numbers using POS Tagger (CD)
	 * 
	 * @return cardinalNumbers
	 */
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
	
	/**
	 * Returns all the words in the sentence given a lexicon
	 *
	 * @param lexicon
	 * @return wordsOnLexicon
	 */
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
	/**
	 * Returns bervs on Title present in the paragraph
	 *
	 * @param newsTitle
	 * @return wordsOnTitle
	 */
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
	/**
	 * Returns the paragraph numberd by words
	 *
	 * @param inputParagraph
	 * @return
	 */
	public String returnParagraphNumberded(String inputParagraph) {
		String paragraphNumberedString = "";
		String[] paragraphNumberedArray = inputParagraph.split(" ");
		for (int i = 0; i < paragraphNumberedArray.length; i++) {
			//System.out.println(paragraphNumberedArray[i]);
			String parsing = paragraphNumberedArray[i]+"/"+i+" ";
			paragraphNumberedString += ""+parsing;
		}
		return paragraphNumberedString;
	}


}
