package BookBoutique;

public class Livre {
	public String title, picture, authName, synopsis;
	public double price;
	
	public Livre(String[] args) {
		this.title = args[0];
		this.picture = args[1];
		this.authName = args[2];
		this.synopsis = args[3];
		this.price = Double.parseDouble(args[4]);
	}
}
