package domein;

public class Product {
	
	//attributen
	private String naam;
	private int aantal;
	private Stock inStock;
	private double eenheidsprijs;
	private double totalePrijs;
	
	//constructor
	public Product(String naam, int aantal, Stock inStock, double eenheidsprijs) {
		setNaam(naam);
		setAantal(aantal);
		setInStock(inStock);
		setEenheidsprijs(eenheidsprijs);
		berekenTotalePrijs(aantal, eenheidsprijs);
	}
	
	private void berekenTotalePrijs(int aantal, double eenheidsprijs) {
		totalePrijs = aantal * eenheidsprijs;
	}
	
	//getters
	public double getTotalePrijs() {
		return totalePrijs;
	}

	//setters
	private void setNaam(String naam) {
		if(naam == null || !naam.matches("([A-z][a-z]+\\s?)+"))
			throw new IllegalArgumentException("Naam product incorrect");
		
		this.naam = naam;
	}
	

	private void setAantal(int aantal) {
		if (aantal < 0)
			throw new IllegalArgumentException("Aantal moet positief zijn");
		this.aantal = aantal;
	}
	
	private void setInStock(Stock inStock) {
		this.inStock = inStock;
	}
	
	private void setEenheidsprijs(Double eenheidsprijs) {
		if (eenheidsprijs <= 0)
			throw new IllegalArgumentException("Eenheidsprijs moet strikt positief zijn");
		this.eenheidsprijs = eenheidsprijs;
	}
	public Double getTotaalPrijs(){
		return aantal * eenheidsprijs;
	}
	public String getNaam() {
	    return naam;
	}

	public int getAantal() {
	    return aantal;
	}

	public double getEenheidsprijs() {
	    return eenheidsprijs;
	}
	
	public Stock isInStock() {
	    return inStock;
	}
	
	
	public String toString() {
		return String.format("%s, %d, %s, %.2f, %.2f", naam, aantal, inStock, eenheidsprijs, totalePrijs);
	}

}
