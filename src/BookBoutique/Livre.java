package BookBoutique;

/**
 * Livre - class
 * Defines an instance of a book.
 * This class is mainly supposed to
 * be used a a data structure to hold
 * book information.
 */
public class Livre {
	public String title, picture, authName, synopsis, genre[];
	public double price;
	
	public Livre(String[] args) {
		this.title = args[0];
		this.picture = args[1];
		this.authName = args[2];
		this.synopsis = args[3];
		this.genre = args[4].split("/");
		this.price = Double.parseDouble(args[5]);
	}
}
