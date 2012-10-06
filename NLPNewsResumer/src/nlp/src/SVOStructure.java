package nlp.src;

import java.util.ArrayList;

/**
 * This class finds  SUBJECT, VERB, OBJECT Structure and words surrownding the OBJECT
 *
 * @author JUAN.
 *         Created 21/02/2012.
 */
public class SVOStructure {

	/**
	 * Array of ACTORS: compound by keywords on Organizations, Words on Lexicon, People etc.
	 */
	ArrayList<String> actors;
	/**
	 * Array of VERBS
	 */
	ArrayList<String> verbs;
	/**
	 * Array of NUMBERS: compound by keywords on Cardinal Numbers, Money and Percent.
	 */
	ArrayList<String> numbers;
	/**
	 * The paragraph
	 */
	String paragraph;
	/**
	 * The paragraph Numbered
	 */
	String paragraphNumbered;
	/**
	 * Array of paragraphNumberd splited by (space) carefull with commas and dots.
	 */
	String[] paragraphNumberedSplit;
	
	/**
	 * This class finds  SUBJECT, VERB, OBJECT Structure and words surrownding the OBJECT
	 *
	 * @param ACTORS ArrayList
	 * @param VERBS ArrayList
	 * @param NUMBERS ArrayList
	 * @param P String the Paragraph
	 * @param PN Stirng the Paragraph Numbered
	 */
	public SVOStructure (ArrayList<String> ACTORS, ArrayList<String> VERBS, ArrayList<String> NUMBERS, String P, String PN) {
		this.actors = ACTORS;
		this.verbs = VERBS;
		this.numbers = NUMBERS;
		this.paragraph = P;
		this.paragraphNumbered = PN;
		this.paragraphNumberedSplit = PN.split(" ");
	}
	
	/**
	 * Gets the SUBJECT the VERB and OBJECT structure in an array kyword/osition value n
	 *
	 * @return SVO
	 */
	public String[] getSVOStructure() {
	
		//FIDING THE SUBJECT
		String subject = "null/0";
		String[] subjectSplit = subject.split("/");
		try{
			subject = this.actors.get(0);
			subjectSplit = this.actors.get(0).split("/");
		}catch (Exception e) {
			//
		}
		String subjectWeightSplit = subjectSplit[subjectSplit.length-1];
		int subjectWeight = Integer.parseInt(subjectWeightSplit);
		
		//FINDING THE VERB
		String verb = null;
		int verbWeight =  0;
		for (int i = 0; i < this.verbs.size(); i++) {
			String[] verbSplit = this.verbs.get(i).split("/");
			String verbWeightSplit = verbSplit[verbSplit.length-1];
			verbWeight =  Integer.parseInt(verbWeightSplit);
			if (verbWeight > subjectWeight) {
				//if the verb is : is, are, be
				if (verbSplit[0].equalsIgnoreCase("is") || verbSplit[0].equalsIgnoreCase("are") || verbSplit[0].equalsIgnoreCase("are")  ) {
					//get the next verb avalible verb
					verb = this.verbs.get(i+1);
				}else{
					//verb found
					verb = this.verbs.get(i);
				}
				i= i+1000;
			}
		}
		//FINDING THE OBJECT
		String object = null;
		for (int i = 0; i < this.actors.size(); i++) {
			String[] objSplit = this.actors.get(i).split("/");
			String objWeightSplit = objSplit[objSplit.length-1];
			int objWeight =  Integer.parseInt(objWeightSplit);
			if (objWeight > verbWeight) {
				object = this.actors.get(i);
				i= i+1000;
			}
		}
		
		//String sentence = subject+" "+verb+" "+object;
		String[] SVO = {subject,verb,object};
		return SVO;
	}

	/**
	 * Gets the surrowding words of the OBJECT
	 * @param threshold range of words to find
	 *
	 * @return wordsSurrownding
	 */
	public ArrayList<String> getObjectSurrownd(int threshold){
		ArrayList<String> wordsSurrownding = new ArrayList<String>();
		
		String[] SVO = this.getSVOStructure();
		System.out.println(SVO);
		//its posible that we dont find an object
		String[] verbSplit = SVO[SVO.length-2].split("/");
		int verbWeight = Integer.parseInt(verbSplit[verbSplit.length-1]);
		
		int objectWeight = verbWeight;
		//try to find a OVJECT
		try {
			String[] objectSplit = SVO[SVO.length-1].split("/");
			objectWeight = Integer.parseInt(objectSplit[objectSplit.length-1]);
			
		} catch (Exception e) {
			//
		}
		
		for (int i = 0; i < this.paragraphNumberedSplit.length; i++) {
			String[] spp = this.paragraphNumberedSplit[i].split("/");
			String spp_weight = spp[spp.length-1];
			int wrd = Integer.parseInt(spp_weight);
			if (wrd >= objectWeight-threshold && wrd <= objectWeight+threshold) {
				String w = this.paragraphNumberedSplit[i];
				wordsSurrownding.add(w);
			}
		}
		
		return wordsSurrownding;
		
	}


	/**
	 * Returns the Array NUMBERS
	 *
	 * @return numbers
	 */
	public ArrayList<String> returnNumbers() {
		
		return this.numbers;
	}

	/**
	 * Returns the Array ACTORS
	 *
	 * @return actors
	 */
	public ArrayList<String> returnActors()
	{
		return this.actors;
		
	}

	/**
	 * Returns the Array VERBS
	 *
	 * @return verbs
	 */
	public ArrayList<String> returnVerbs() {
		return this.verbs;
	}
}
