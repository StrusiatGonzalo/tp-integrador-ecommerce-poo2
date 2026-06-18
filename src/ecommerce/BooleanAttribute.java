package ecommerce;

public class BooleanAttribute extends Attribute<Boolean>{
	
	public BooleanAttribute(String name, Boolean value) {
		this.value = value;
		this.name = name;
	}
	
	@Override
	public boolean hasValue() {
		return getValue() != null;
	}

	@Override
	public String showValue() {
		return getValue() != null ? getValue().toString() : " " ;
	}

	@Override
	protected Boolean parseValue(String value) {
		return Boolean.parseBoolean(value);
	}
	
	@Override
	public boolean compareTo(String value) {
		return getValue().equals(parseValue(value));
	}
}
