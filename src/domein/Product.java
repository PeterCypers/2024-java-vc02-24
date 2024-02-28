package domein;

public class Product {
	
	//attributen
	private String naam;
	private int aantal;
	private boolean inStock;
	private double eenheidsprijs;
	private double totalePrijs;
	
	//constructor
	public Product(String naam, int aantal, boolean inStock, double eenheidsprijs, double totalePrijs) {
		setNaam(naam);
		setAantal(aantal);
		setInStock(inStock);
		setEenheidsprijs(eenheidsprijs);
		this.totalePrijs = totalePrijs;
	}
	
	//setters
	private void setNaam(String naam) {
		this.naam = naam;
	}
	
	private void setAantal(int aantal) {
		if (aantal < 0)
			throw new IllegalArgumentException("Aantal moet positief zijn");
		this.aantal = aantal;
	}
	
	private void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	
	private void setEenheidsprijs(double eenheidsprijs) {
		if (eenheidsprijs <= 0)
			throw new IllegalArgumentException("Eenheidsprijs moet strikt positief zijn");
		this.eenheidsprijs = eenheidsprijs;
	}
	
	public String toString() {
		return String.format("%s, %d, %s, %.2f, %.2f", naam, aantal, inStock, eenheidsprijs, totalePrijs);
	}

}
