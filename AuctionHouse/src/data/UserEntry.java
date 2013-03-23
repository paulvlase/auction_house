package data;

public class UserEntry {
	public enum Offer {
		NO_OFFER,
		OFFER_MADE,
		OFFER_ACCEPTED,
		OFFER_REFUSED;
		
		public String toString() {
			switch (ordinal()) {
			case 0:
				return "No offer";
			case 1:
				return "Offer made";
			case 2:
				return "Offer accepted";
			case 3:
				return "Offer refused";
			default:
				break;
			}
			
			return "";
		};
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
	
	@Override
	public String toString() {
		return name;
	}
}