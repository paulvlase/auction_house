package states;

public class NoOfferState implements State {
	public NoOfferState() {
		
	}
	
	public boolean execute() {
		return false;
	}
	
	public String getName() {
		return "No Offer";
	}
}
