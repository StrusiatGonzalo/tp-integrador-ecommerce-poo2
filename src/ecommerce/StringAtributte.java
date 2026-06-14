package ecommerce;

public class StringAtributte extends Atributte<String> {
	
	public StringAtributte(String name, String value) {
		this.value = value;
		this.name = name;
	}
	
	@Override
	public boolean hasValue() {
		return getValue() != null && !getValue().isBlank();
	}

	@Override
	public String showValue() {
		return getValue() != null ? getValue(): "";
	}

	@Override
	protected String parseValue(String value) {
		return getValue();
	}
}
