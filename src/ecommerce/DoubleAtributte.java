package ecommerce;

public class DoubleAtributte extends Atributte<Double>{

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

}
