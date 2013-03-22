package states;

public class OfferMadeState implements State {
	public OfferMadeState() {
		
	}
	
	public boolean execute() {
		return false;
	}
	
	public String getName() {
		return "Offer Made";
	}
}
