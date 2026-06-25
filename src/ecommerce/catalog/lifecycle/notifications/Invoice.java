package ecommerce.catalog.lifecycle.notifications;

public class Invoice {
	private double amount;
	private String address;
	
	public Invoice(double amount, String address) {
		this.amount = amount;
		this.address = address;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getAddress() {
		return address;
	}
}
