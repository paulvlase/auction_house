package gui;

import interfaces.Command;

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
		Command c = (Command) e.getSource();
		
		c.execute();
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
