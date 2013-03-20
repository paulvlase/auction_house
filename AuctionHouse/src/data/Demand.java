package data;

public class Demand {
	private String name;
	private long time;
	
	public Demand() {
		this.name = "demand";
		this.time = -1;
	}
	
	public Demand(String name, long time) {
		this.name = name;
		this.time = time;
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
}
