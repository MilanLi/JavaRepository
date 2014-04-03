
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Huffman {
	
	public static String readFile(String filename){
		String returnString = "";
		FileReader rd = null;
		try {
			rd = new FileReader(filename);
			BufferedReader buff = new BufferedReader(rd);
			String line = "";
			while((line = buff.readLine()) != null){
				returnString += line+"\n";
			}
			buff.close();
		} 
		catch (FileNotFoundException e) {
			throw new RuntimeException("File not found!");
		}
		catch(IOException e){
			throw new RuntimeException("IO error occured!");
		}finally{
			try {
				rd.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnString;
	}
	
	public static int[] countFrequency(String text){
		int[] freq = new int[128];
		int len = text.length();
		for(int i = 0; i < len; i++){
			++freq[(int)text.charAt(i)];
		}
//		for(int i = 0; i < freq.length; i++){
//			System.out.println(freq[i]);
//		}
		return freq;
	}
	
	public static int insertIntoPQ(PriorityQueue<TreeNode> pq, int[] freq){
		int count = 0;
		for(int i = 0; i < freq.length; i++){
			if(freq[i] != 0){
				String str = String.valueOf((char)i);
//				System.out.println("insert "+str+" in insertIntoPQ");
				pq.add(new TreeNode(str, freq[i]));
				count++;
			}
		}
		System.out.println("insertion into priority queue has been finished");
		return count;
	}
	
	public static TreeNode trimToTree(PriorityQueue<TreeNode> pq){
		while(pq.size() >= 2){
			TreeNode first = pq.poll();
			TreeNode second = pq.poll();
			pq.add(new TreeNode("", first.freq+second.freq, first, second));
		}
		return pq.poll();
	}
	
	public static void traverseTree(TreeNode root, String[] huffman, String current){
		if(root != null){
			if(root.getStr() != ""){
				char ch = root.getStr().charAt(0);
				huffman[(int)ch] = current;
//				System.out.println("assign "+String.valueOf(ch));
			}
			else{
				traverseTree(root.left, huffman, current+"0");
				traverseTree(root.right, huffman, current+"1");
			}
		}
	}
	
	public static void writeIntoFile(String filename, String text, String[] huffman){
		FileWriter fWriter = null;
		try {
			fWriter = new FileWriter(filename);
			BufferedWriter buff = new BufferedWriter(fWriter);
			for(int i = 0; i < text.length(); i++){
				char ch = text.charAt(i);
				buff.write(huffman[(int)ch]);
			}
			buff.close();
		} catch (IOException e) {
			throw new RuntimeException("Write file error.");
		}
		finally{
			try {
				fWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				
			}
			
		}
		
	}
	
	public static void writeSymbolTable(String filename, int[] freq, String[] huffman){
		FileWriter fWriter = null;
		try {
			fWriter = new FileWriter(filename);
			BufferedWriter buff = new BufferedWriter(fWriter);
			for(int i = 0; i < freq.length; i++){
				if(freq[i] != 0){
					char ch = (char)i;
					buff.write(ch+": ");
					buff.write(huffman[i]+"\n");
				}
				
			}
			buff.close();
		} catch (IOException e) {
			throw new RuntimeException("Write file error.");
		}
		finally{
			try {
				fWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				
			}
			
		}
	}
	
	public static void main(String[] args) {
		//Huffman huff = new Huffman();
		String testString = readFile("./src/test.txt");
		//System.out.println(testString);
		int[] freq = countFrequency(testString);
		Comparator<TreeNode> comparator = new HuffmanTreeComparator();
		PriorityQueue<TreeNode> pq = new PriorityQueue<>(10, comparator);
		System.out.println(insertIntoPQ(pq, freq)); 
		TreeNode tree = trimToTree(pq);
		System.out.println("Total frequency in root is "+tree.freq);
		System.out.println("Right Total frequency is "+testString.length());
		
		String[] huffman = new String[128];
		traverseTree(tree, huffman, "");
		for(int i = 0; i < freq.length; i++){
			if(freq[i] != 0){
				System.out.println((char)i+": "+huffman[i]);
			}
		}
		writeSymbolTable("./src/SymbolTable.txt", freq, huffman);
		// this may not be right because it should be written bit and bit but not string.
//		writeIntoFile("./src/testOut.txt", testString, huffman);
		
		
	}

}
