
public class TreeNode {
	private String str;
	public int freq;
	public TreeNode left;
	public TreeNode right;
	
	public TreeNode(){
		str = "";
		freq = 0;
		left = null;
		right = null;
	}
	public TreeNode(String s, int f, TreeNode l, TreeNode r){
		str = s;
		freq = f;
		left = l;
		right = r;
	}
	
	public TreeNode(String s, int f){
		str = s;
		freq = f;
		left = null;
		right = null;
	}
	
	public String getStr(){
		return str;
	}
	
}
