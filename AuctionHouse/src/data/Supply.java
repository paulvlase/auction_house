package data;

public class Supply {
	private String name;
	private long time;
	private double price;
	
	public Supply() {
		this.name = "supply";
		this.time = -1;
		this.price = -1;
	}
	
	public Supply(String name, long time, double price) {
		this.name = name;
		this.time = time;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
