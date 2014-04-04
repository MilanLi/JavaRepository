package patternMatchPkg;

import java.util.ArrayList;

public class NaivePatternMatch implements PatternMatch{
	
	private String pattern;
	private String word;
	
	public NaivePatternMatch(String _patternString, String _wordString){
		pattern = _patternString;
		word = _wordString;
	}
	
	public int matchPattern(){
		return matchPattern(0);
	}
	
	public int matchPattern(int start){
		int lenPat = pattern.length();
		int lenWord = word.length();
		int ret = start;

		for(int ind = start; ind <= lenWord - lenPat; ind++){
			int j = 0;
			int i = ind;
			while(j < lenPat && word.charAt(i) == pattern.charAt(j)){
				i++;
				j++;
			}
			if(j == lenPat){
				return i-j;
			}
		}
		
		return -1;
	}

	@Override
	public ArrayList<Integer> findAllMatch() {
		int start = 0;
		int m;
		ArrayList<Integer> retArrayList = new ArrayList<Integer>(); 
		
		while((m = matchPattern(start)) != -1){
			retArrayList.add(m);
			start = m+1;
		}
		return retArrayList;
	}
	
}
