package data;

public class UserEntry {
	public enum Offer {
		NO_OFFER,
		OFFER_MADE,
		OFFER_ACCEPTED,
		OFFER_REFUSED
	}
	
	String name;
	Offer offer;
	Long time;
	Double price;
	
	public UserEntry(String name, Offer offer, Long time, Double price){
		this.name = name;
		this.offer = offer;
		this.time = time;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}