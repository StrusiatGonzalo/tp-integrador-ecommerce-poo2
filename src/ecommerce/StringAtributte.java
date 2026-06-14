package ecommerce;

public class StringAtributte extends Atributte<String> {

	@Override
	public boolean hasValue() {
		return getValue() != null && !getValue().isBlank();
	}

	@Override
	public String showValue() {
		return getValue() != null ? getValue(): "";
	}

	@Override
	protected String parsearValue(String value) {
		return getValue();
	}
}
