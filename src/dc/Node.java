package dc;

public interface Node {
	void report(String string);

	Node getLeft();
	Node getRight();
	String getAttName();

	int getClassName();

}
