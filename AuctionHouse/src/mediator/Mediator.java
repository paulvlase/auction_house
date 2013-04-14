package mediator;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;

import data.LoginCred;
import data.Service;
import data.UserProfile;
import interfaces.Gui;
import interfaces.MediatorGui;
import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;
import interfaces.NetworkMediator;
import interfaces.WebClient;

public class Mediator implements MediatorGui, MediatorNetwork,
MediatorWeb{

	@Override
	public void registerWebClient(WebClient web) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeProfileNotify(UserProfile profile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InetSocketAddress getNetworkAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerNetwork(NetworkMediator net) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeServiceNotify(Service service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startTransfer(Service service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopTransfer(Service service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putOffer(Service service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Service getOffer(String serviceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, Service> getOffers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeOffer(String serviceName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerGui(Gui gui) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean logIn(LoginCred cred) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserProfile getUserProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setUserProfile(UserProfile profile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerUser(UserProfile profile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verifyUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Service> loadOffers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void publishService(Service service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publishServices(ArrayList<Service> services) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Service createService(String name, Long time, Double price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyNetwork(Service service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launchOffers(ArrayList<Service> services) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LoginCred getLoginCred() {
		// TODO Auto-generated method stub
		return null;
	}

}
