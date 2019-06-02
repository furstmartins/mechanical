package calculation;

public class ComboBoxKeyPair {

	private Double key;
	private Double a;
	private String value;

	public ComboBoxKeyPair(Double key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public ComboBoxKeyPair(Double key, Double a, String value) {
		this.key = key;
		this.a = a;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}

	public Double getKey() {
		return key;
	}

	public Double getA() {
		return a;
	}

	public void setA(Double a) {
		this.a = a;
	}

	public void setKey(Double key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
