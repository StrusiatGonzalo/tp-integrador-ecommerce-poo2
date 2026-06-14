package ecommerce;

public class BooleanAtributte extends Atributte<Boolean>{

	@Override
	public boolean hasValue() {
		return getValue() != null;
	}

	@Override
	public String showValue() {
		return getValue() != null && getValue() ? "true" : "false" ;
	}

	@Override
	protected Boolean parseValue(String value) {
		return Boolean.parseBoolean(value);
	}
}
