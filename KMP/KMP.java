package patternMatchPkg;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class KMP implements PatternMatch{
	
	private Hashtable<Character, Integer> hashtable;
	private int[][] DFA;
	private boolean DFAExisted;
	private int rows;
	private int cols;
	private String pattern;
	private String word;
	private int ACstate;
	
	public KMP(String _pattern, String _word){
		pattern = _pattern;
		word = _word;
		hashtable = new Hashtable<Character, Integer>();
		
		rows = searchForDistinct(pattern);
		cols = pattern.length();
		ACstate = cols;
		DFA = new int[rows][cols];
		DFAExisted = false;
	}

	private int searchForDistinct(String s){
		int len = s.length();
		int ind = 0;
		for(int i = 0; i < len; i++){
			char ch = s.charAt(i);
			if(!hashtable.containsKey(ch)){
				hashtable.put(ch, ind);
				ind++;
			}
		}
//		System.out.println("Search for distinct has been done.");
		return ind;
	}
	
	private int[] getRow(char ch){
		return DFA[hashtable.get(ch)];
	}
	
	private void printDFA(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				System.out.print(DFA[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	private void constructDFA(){
		
		char ch = pattern.charAt(0);
		getRow(ch)[0] = 1;
		int curr = 1;
		int prev = 0;
		
		int len = pattern.length();
		while(curr < len){
			
			// change according to the response of prev
			Iterator it = hashtable.keySet().iterator();
			while(it.hasNext()){
				char r = (char)it.next();
				getRow(r)[curr] = getRow(r)[prev];
			}
			
			// match transition
			ch = pattern.charAt(curr);
			getRow(ch)[curr] = curr+1;
			
			// update prev and curr
//			System.out.println(prev+", "+curr);
			
			prev = getRow(ch)[prev];
			curr++;
		}
		DFAExisted = true;
//		printDFA();
		
	}
	
	private int matchWithDFA(int start){
		int len = word.length();
		int state = 0;
		for(int i = start; i < len; i++){
			char ch = word.charAt(i);
			if(!hashtable.containsKey(ch)){
				state = 0;
			}
			else{
				state = getRow(ch)[state];
				if(state == ACstate){
					return i-cols+1;
				}
			}
		}
		return -1;
	}
	
	
	@Override
	public int matchPattern(){
		constructDFA();
		return matchWithDFA(0);
	}
	
	@Override
	public int matchPattern(int start){
		if(!DFAExisted){
			constructDFA();
		}
		return matchWithDFA(start);
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
