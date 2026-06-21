package ecommerce.catalog.lifecycle;

public class CreditNote {
	private double amount; // costo
	private String description;
	
	public CreditNote(double amount, String description) {
		this.amount = amount;
		this.description = description;
	}
	
	public double getProductCost() {
		return amount;
	}
	
	public String getDescription() {
		return description;
	}
}
