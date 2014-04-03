import java.util.Comparator;


public class HuffmanTreeComparator implements Comparator<TreeNode> {

	@Override
	public int compare(TreeNode o1, TreeNode o2) {
		if(o1.freq < o2.freq){
			return -1;
		}
		else if(o1.freq > o2.freq){
			return 1;
		}
		return 0;
	}

}
