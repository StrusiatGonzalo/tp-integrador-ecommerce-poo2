package ecommerce;

public class StringAttribute extends Attribute<String> {
	
	public StringAttribute(String name, String value) {
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
		return value;
	}

	@Override
	public boolean compareTo(String value) {
		return getValue().equals(value);
	}
	
	
}

