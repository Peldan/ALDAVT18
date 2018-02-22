package alda.tree;
//Arvid Peldán, arpe3186
/**
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) De ändringar som är tillåtna är dock
 * begränsade av följande:
 * <ul>
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * </ul>
 * 
 * @author henrikbe
 * @author Arvid Peldán
 * @param <T>
 */

public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	public boolean add(T data) {
		int compare = data.compareTo(this.data);
		if(contains(data)){
			return false;
		}
		if(compare > 0){
			if(right == null){
				right = new BinarySearchTreeNode<T>(data);
				return true;
			} else {
				return right.add(data);
			}
		}
		else if(compare < 0){
			if(left == null){
				left = new BinarySearchTreeNode<T>(data);
				return true;
			} else {
				return left.add(data);
			}
		}
		return false;
	}
	

	private T findMin() {
		if(this.left == null){
			return this.data;
		} else {
			return this.left.findMin();
		}
	}
	
	public BinarySearchTreeNode<T> remove(T data) {
		int compare = data.compareTo(this.data);		
		if(compare > 0 && right != null){
			right = right.remove(data);
		}
		else if(compare < 0 && left != null){
			left = left.remove(data);
		}
		else if(compare == 0){
			if(this.left != null && this.right != null){
				T min = right.findMin();
				this.data = min;
				right = right.remove(min);
			} else {
				return (left == null ? right : left);
			}
		}
		return this;
	}

	public boolean contains(T data) {
		if(data == null)
			return false;
		int compare = data.compareTo(this.data);
		if (compare > 0) {
			if (right != null) {
				if (right.contains(data)) {
					return true;
				}
			}
		}	
		else if (compare < 0) {
			if (left != null) {
				if (left.contains(data)) {
					return true;
				}
			}
		}
		else {
			return true;			
		}
		return false;
	}

	public int size() {
		if(left == null && right == null){
			return 1;
		}
		else if(left == null){
			return 1 + this.right.size();
		}
		else if(right == null){
			return 1 + this.left.size();
		}
		else {
			return (this.left.size() + this.right.size()) + 1;
		}
	}

	public int depth() {
		int ldepth = 0;
		int rdepth = 0;
		if(this.left != null){
			ldepth = 1 + left.depth();
		}
		if(this.right != null){
			rdepth = 1 + right.depth();
		}
		return ldepth > rdepth ? ldepth : rdepth;
	}
	

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		if(left != null){
			stringBuilder.append(left.toString() + ", ");
		}
		stringBuilder.append(this.data);
		if(right != null){
			stringBuilder.append(", " + right.toString());
		}
		return stringBuilder.toString();
	}
}
