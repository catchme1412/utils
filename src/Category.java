
public class Category {
	
	private String name;
	
	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return name.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
