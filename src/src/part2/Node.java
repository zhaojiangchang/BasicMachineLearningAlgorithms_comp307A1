package part2;
/**
 * @author JackyChang
 * ID:300282984
 *
 */
public interface Node {
	void report(String string);

	Node getLeft();
	Node getRight();
	String getAttName();

	int getClassName();

}
