package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import data.Pair;
import data.Service;
import data.UserEntry;
import data.Service.Status;
import data.UserEntry.Offer;
import data.UserProfile.UserRole;

public class MainWindowListener implements ActionListener, WindowListener, MouseListener {
	private MainWindow	mainWindow;

	public MainWindowListener(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainWindow.getAddServiceItem()) {
			addServiceAction();
			return;
		}
		
		if (e.getSource() == mainWindow.getProfileItem()) {
			profileAction();
			return;
		}
		
		if (e.getSource() == mainWindow.getExitItem()) {
			exitAction();
			return;
		}
		
		if (e.getSource() == mainWindow.getSignOutButton()
				|| e.getSource() == mainWindow.getSignOutItem()) {
			logOutAction();
			return;
		}
		
		if(e.getSource() == mainWindow.getLaunchRequestItem()){
			launchOffer();
			return;
		}
		
		if(e.getSource() == mainWindow.getDropRequestItem()){
			dropRequest();
			return;
		}
		
		if(e.getSource() == mainWindow.getAcceptOfferItem()){
			acceptRequest();
			return;
		}
		
		if(e.getSource() == mainWindow.getRefusetOfferItem()){
			refuseRequest();
			return;
		}
		
		if(e.getSource() == mainWindow.getMakeOfferItem()){
			makeOffer();
			return;
		}

		if(e.getSource() == mainWindow.getDropAuctionItem()){
			dropAuction();
			return;
		}
	}

	private void dropAuction() {
		System.out.println("Drop Auction");
	}

	private void makeOffer() {
		System.out.println("Make offer");
	}

	private void refuseRequest() {
		System.out.println("Refuse Request");
	}

	private void acceptRequest() {
		System.out.println("Accept Request");
	}

	private void dropRequest() {
		System.out.println("Drop Request");
	}

	private void launchOffer() {
		System.out.println("Launch Offer");		
	}

	private void addServiceAction() {
		System.out.println("Add Service action");

		new AddNewService(mainWindow).setVisible(true);
	}

	private void profileAction() {
		System.out.println("Profile action");
	}

	private void exitAction() {
		System.out.println("Exit Action");
	}

	private void logOutAction() {
		System.out.println("TODO signOutAction");
		mainWindow.getGui().logOut();
	}

	private void showPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			int row = mainWindow.getTable().rowAtPoint(e.getPoint());
			int column = mainWindow.getTable().columnAtPoint(e.getPoint());
			System.out.println("Selected Row : " + row + " Column : " + column);
			buildPopupMenu(mainWindow.getModel().getServiceFromRow(row), column);
			mainWindow.getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
		}

	}

	private void buildPopupMenu(Pair<Service, Integer> pair, Integer column) {
		Service service = pair.getKey();
		Boolean accepted = false;
		if (service.getUsers() != null) {
			for (UserEntry userEntry : service.getUsers()) {
				if (userEntry.getOffer() == Offer.OFFER_ACCEPTED) {
					accepted = true;
					break;
				}
			}
		}

		if (mainWindow.getGui().getUserProfile().getRole() == UserRole.BUYER) {
			if (service.getStatus() == Status.INACTIVE) {
				mainWindow.getLaunchRequestItem().setVisible(true);
			} else {
				mainWindow.getLaunchRequestItem().setVisible(false);
			}

			if (service.getStatus() != Status.INACTIVE && !accepted) {
				if (column >= 2) {
					mainWindow.getDropRequestItem().setVisible(true);
					mainWindow.getMenuSeparator().setVisible(true);
					mainWindow.getAcceptOfferItem().setVisible(true);
					mainWindow.getRefusetOfferItem().setVisible(true);
				} else {
					mainWindow.getDropRequestItem().setVisible(true);
					mainWindow.getMenuSeparator().setVisible(false);
					mainWindow.getAcceptOfferItem().setVisible(false);
					mainWindow.getRefusetOfferItem().setVisible(false);
				}
			} else {
				mainWindow.getDropRequestItem().setVisible(false);
				mainWindow.getMenuSeparator().setVisible(false);
				mainWindow.getAcceptOfferItem().setVisible(false);
				mainWindow.getRefusetOfferItem().setVisible(false);
			}

			mainWindow.getMakeOfferItem().setVisible(false);
			mainWindow.getDropAuctionItem().setVisible(false);
		}

		if (mainWindow.getGui().getUserProfile().getRole() == UserRole.SELLER) {
			mainWindow.getLaunchRequestItem().setVisible(false);
			mainWindow.getDropAuctionItem().setVisible(false);
			mainWindow.getMenuSeparator().setVisible(false);
			mainWindow.getAcceptOfferItem().setVisible(false);
			mainWindow.getRefusetOfferItem().setVisible(false);

			if (service.getStatus() != Status.INACTIVE && !accepted && column >= 2) {
				mainWindow.getMakeOfferItem().setVisible(true);
				mainWindow.getDropAuctionItem().setVisible(true);
			} else {
				mainWindow.getMakeOfferItem().setVisible(false);
				mainWindow.getDropAuctionItem().setVisible(false);
			}
		}

	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		mainWindow.getGui().logOut();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		showPopup(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		showPopup(e);
	}
}
