package ecommerce;

public class DoubleAtributte extends Atributte<Double>{
	
	public DoubleAtributte(String name, Double value) {
		this.value = value;
		this.name = name;
	}
	
	@Override
	public boolean hasValue() {
		return getValue() != null;
	}

	@Override
	public String showValue() {
		return getValue() != null ? getValue().toString() : "";
	}

	@Override
	protected Double parseValue(String value) {
		return Double.parseDouble(value); // 
	}
	
	@Override
	public boolean compareTo(String value) {
		return getValue().equals(parseValue(value));
	}
	
}
