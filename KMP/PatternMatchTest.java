package patternMatchPkg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class PatternMatchTest {
	
	public String readStringFromFile(String filename){
		FileReader fReader = null;
		String string = "";
		try {
			fReader = new FileReader(filename);
			BufferedReader buff = new BufferedReader(fReader);
			String line = "";
			while((line = buff.readLine()) != null){
				string += line;
			}
			buff.close();
		} 
		catch (FileNotFoundException e){
			System.out.println("file does not exist.");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("file open error.");
			e.printStackTrace();
		}
		finally{
			try {
				fReader.close();
				
			} catch (IOException e) {
				System.out.println("file close error.");
				e.printStackTrace();
			}
		}
		return string;
	}
	
	public static void main(String[] args) {
		PatternMatchTest pt = new PatternMatchTest();
//		String patternString = "ABCDABD";
//		String wordString = "ABC ABCDABD ABCDABCDABDE";
		
		String patternString = "ATCG";
		String wordString = pt.readStringFromFile("./src/patternMatchPkg/input.txt");
		
		ArrayList<Integer> found = null;
		
		
		// time counter
		long tStart = System.currentTimeMillis();
		
		PatternMatch pm = new KMP(patternString, wordString);
		found = pm.findAllMatch();
		System.out.println("KMP method:");
		for(int i = 0; i < found.size(); i++){
			System.out.print(found.get(i)+" ");
		}
		System.out.println("");
		
//		int kmp1 = pm.matchPattern();
//		System.out.println("start index is: "+kmp1);
//		int kmp2 = pm.matchPattern(kmp1+1);
//		System.out.println("second index is: "+kmp2);
		
		long tEnd = System.currentTimeMillis();
		double elapsed = (tEnd-tStart)/1000.0;
		System.out.println("time elapsed for KMP is "+elapsed + " seconds.\n");
		
		
		tStart = System.currentTimeMillis();
		PatternMatch naivePM = new NaivePatternMatch(patternString, wordString);
		
		found = naivePM.findAllMatch();
		System.out.println("naive method:");
		for(int i = 0; i < found.size(); i++){
			System.out.print(found.get(i)+" ");
		}
		System.out.println("");
		
//		int naive1 = pm.matchPattern();
//		System.out.println("first index is: "+naive1);
//		int naive2 = pm.matchPattern(naive1+1);
//		System.out.println("second index is: "+naive2);
		
		tEnd = System.currentTimeMillis();
		elapsed = (tEnd-tStart)/1000.0;
		System.out.println("time elapsed for naive method is "+elapsed + " seconds.");
		
	}
	
}
