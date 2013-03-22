package gui;

import gui.spantable.MultiSpanCellTable;
import interfaces.Gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import org.omg.PortableInterceptor.INACTIVE;

import config.GuiConfig;
import data.Pair;
import data.Service;
import data.Service.Status;
import data.UserEntry;
import data.UserEntry.Offer;
import data.UserProfile.UserRole;

/**
 * @author Ghennadi Procopciuc
 */

public class MainWindow extends JFrame {

	private static final long	serialVersionUID	= 1L;

	/* Swing components */
	private JMenuBar			menuBar;
	private JMenu				menu;
	private JMenuItem			addServiceItem;
	private JMenuItem			profileItem;
	private JMenuItem			signOutItem;
	private JMenuItem			exitItem;
	private JPanel				mainPanel;
	private JPanel				topPanel;
	private JLabel				avatarLabel;
	private JLabel				usernameLabel;
	private JButton				signOutButton;
	private JScrollPane			scrollPanel;

	private JPopupMenu			popupMenu;
	private JMenuItem			launchRequestItem;
	private JMenuItem			dropRequestItem;
	private JSeparator			menuSeparator;
	private JMenuItem			acceptOfferItem;
	private JMenuItem			refusetOfferItem;
	private JMenuItem			makeOfferItem;
	private JMenuItem			dropAuctionItem;

	private Gui					gui;
	private String[]			tableColumns;
	private MultiSpanCellTable	table;
	private MySpanTableModel	model;

	private ArrayList<Service>	services;

	private MyTableCellRenderer	progressRenderer;
	private MainWindowListener	listener;

	public MainWindow(ArrayList<Service> services) {
		this.services = services;

		initComponents();
	}

	public MainWindow(Gui gui) {
		this.services = gui.loadOffers();
		this.gui = gui;
		initComponents();
	}

	private void initComponents() {
		/* Table init */
		tableColumns = new String[] { GuiConfig.getValue(GuiConfig.SERVICE),
				GuiConfig.getValue(GuiConfig.STATUS), GuiConfig.getValue(GuiConfig.SELLER),
				GuiConfig.getValue(GuiConfig.OFFER_MADE), GuiConfig.getValue(GuiConfig.TIME),
				GuiConfig.getValue(GuiConfig.PRICE) };
		model = new MySpanTableModel(services, new ArrayList<String>(Arrays.asList(tableColumns)));
		table = new MultiSpanCellTable(model, new MyTableCellRenderer());
		menuBar = new JMenuBar();
		menu = new JMenu();
		addServiceItem = new JMenuItem();
		profileItem = new JMenuItem();
		signOutItem = new JMenuItem();
		exitItem = new JMenuItem();
		mainPanel = new JPanel();
		topPanel = new JPanel();
		avatarLabel = new JLabel();
		usernameLabel = new JLabel();
		signOutButton = new JButton();
		scrollPanel = new JScrollPane();
		popupMenu = new JPopupMenu();
		launchRequestItem = new JMenuItem();
		dropRequestItem = new JMenuItem();
		menuSeparator = new JSeparator();
		acceptOfferItem = new JMenuItem();
		refusetOfferItem = new JMenuItem();
		makeOfferItem = new JMenuItem();
		dropAuctionItem = new JMenuItem();
		
		listener = new MainWindowListener(this);

		// JPopupMenu
		{
			launchRequestItem.setText("Launch Offer Request");
			dropRequestItem.setText("Drop Offer Request");
			acceptOfferItem.setText("Accept Offer");
			refusetOfferItem.setText("Refuse Offer");
			makeOfferItem.setText("Make Offer");
			dropAuctionItem.setText("Drop auction");

			popupMenu.add(launchRequestItem);
			popupMenu.add(dropRequestItem);
			popupMenu.add(menuSeparator);
			popupMenu.add(acceptOfferItem);
			popupMenu.add(refusetOfferItem);
			popupMenu.add(makeOfferItem);
			popupMenu.add(dropAuctionItem);
			table.addMouseListener(listener);
		}

		// Table
		{
			table.setCellSelectionEnabled(true);
			scrollPanel.getViewport().setBackground(Color.WHITE);
			table.setFillsViewportHeight(true);
			table.setIntercellSpacing(new Dimension());
			table.setShowGrid(false);

			table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		}

		// Listeners
		{
			signOutButton.addActionListener(listener);
			addServiceItem.addActionListener(listener);
			signOutItem.addActionListener(listener);
			profileItem.addActionListener(listener);
			exitItem.addActionListener(listener);
			
			launchRequestItem.addActionListener(listener);
			dropRequestItem.addActionListener(listener);
			acceptOfferItem.addActionListener(listener);
			refusetOfferItem.addActionListener(listener);
			makeOfferItem.addActionListener(listener);
			dropAuctionItem.addActionListener(listener);
		}

		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 15, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 14, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 1.0, 1.0, 1.0,
				1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0E-4 };

		// menuBar1
		{

			// menu
			{
				menu.setText(GuiConfig.getValue(GuiConfig.MENU));

				// addServiceItem
				addServiceItem.setText(GuiConfig.getValue(GuiConfig.ADD_SERVICE));
				menu.add(addServiceItem);

				// profileItem
				profileItem.setText(GuiConfig.getValue(GuiConfig.PROFILE));
				menu.add(profileItem);

				// logoutItem
				signOutItem.setText(GuiConfig.getValue(GuiConfig.LOG_OUT));
				menu.add(signOutItem);

				// exitItem
				exitItem.setText(GuiConfig.getValue(GuiConfig.EXIT));
				menu.add(exitItem);
			}
			menuBar.add(menu);
		}
		setJMenuBar(menuBar);

		// mainPanel
		{
			mainPanel.setLayout(new GridBagLayout());
			((GridBagLayout) mainPanel.getLayout()).columnWidths = new int[] { 0, 0 };
			((GridBagLayout) mainPanel.getLayout()).rowHeights = new int[] { 0, 0, 0 };
			((GridBagLayout) mainPanel.getLayout()).columnWeights = new double[] { 1.0, 1.0E-4 };
			((GridBagLayout) mainPanel.getLayout()).rowWeights = new double[] { 0.0, 0.0, 1.0E-4 };

			// topPanel
			{
				topPanel.setLayout(new GridBagLayout());
				((GridBagLayout) topPanel.getLayout()).columnWidths = new int[] { 0, 0, 0, 0, 0 };
				((GridBagLayout) topPanel.getLayout()).rowHeights = new int[] { 0, 0 };
				((GridBagLayout) topPanel.getLayout()).columnWeights = new double[] { 1.0, 0.0,
						0.0, 0.0, 1.0E-4 };
				((GridBagLayout) topPanel.getLayout()).rowWeights = new double[] { 0.0, 1.0E-4 };
				topPanel.add(avatarLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5),
						0, 0));

				// usernameLabel
				usernameLabel.setText("Ghennadi Procopciuc");
				topPanel.add(usernameLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5),
						0, 0));

				// logoutButton
				signOutButton.setText(GuiConfig.getValue(GuiConfig.LOG_OUT));
				topPanel.add(signOutButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0),
						0, 0));
			}
			mainPanel.add(topPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
					0));

			// scrollPane1
			{
				scrollPanel.setViewportView(table);
			}
			mainPanel.add(scrollPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
					0));
		}
		contentPane.add(mainPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		if (gui.getUserProfile().getRole() == UserRole.SELLER) {
			setTitle("Seller");
		} else {
			setTitle("Buyer");
		}

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void addServices(ArrayList<Service> services) {
		System.out.println("TODO");
	}

	public void addService(Service service) {
		model.addService(service);
	}

	public void removeService(Service service) {
		System.out.println("TODO");
	}

	public void removeServices(ArrayList<Service> services) {
		for (Service service : services) {
			addService(service);
		}
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

	public void showWindow() {
		setVisible(true);
	}

	public JMenuItem getAddServiceItem() {
		return addServiceItem;
	}

	public void setAddServiceItem(JMenuItem addServiceItem) {
		this.addServiceItem = addServiceItem;
	}

	public JMenuItem getProfileItem() {
		return profileItem;
	}

	public void setProfileItem(JMenuItem profileItem) {
		this.profileItem = profileItem;
	}

	public JMenuItem getSignOutItem() {
		return signOutItem;
	}

	public void setSignOutItem(JMenuItem signOutItem) {
		this.signOutItem = signOutItem;
	}

	public JMenuItem getExitItem() {
		return exitItem;
	}

	public void setExitItem(JMenuItem exitItem) {
		this.exitItem = exitItem;
	}

	public JButton getSignOutButton() {
		return signOutButton;
	}

	public void setSignOutButton(JButton signOutButton) {
		this.signOutButton = signOutButton;
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public JMenuItem getLaunchRequestItem() {
		return launchRequestItem;
	}

	public void setLaunchRequestItem(JMenuItem launchRequestItem) {
		this.launchRequestItem = launchRequestItem;
	}

	public JMenuItem getDropRequestItem() {
		return dropRequestItem;
	}

	public void setDropRequestItem(JMenuItem dropRequestItem) {
		this.dropRequestItem = dropRequestItem;
	}

	public JSeparator getMenuSeparator() {
		return menuSeparator;
	}

	public void setMenuSeparator(JSeparator menuSeparator) {
		this.menuSeparator = menuSeparator;
	}

	public JMenuItem getAcceptOfferItem() {
		return acceptOfferItem;
	}

	public void setAcceptOfferItem(JMenuItem acceptOfferItem) {
		this.acceptOfferItem = acceptOfferItem;
	}

	public JMenuItem getRefusetOfferItem() {
		return refusetOfferItem;
	}

	public void setRefusetOfferItem(JMenuItem refusetOfferItem) {
		this.refusetOfferItem = refusetOfferItem;
	}

	public JMenuItem getMakeOfferItem() {
		return makeOfferItem;
	}

	public void setMakeOfferItem(JMenuItem makeOfferItem) {
		this.makeOfferItem = makeOfferItem;
	}

	public JMenuItem getDropAuctionItem() {
		return dropAuctionItem;
	}

	public void setDropAuctionItem(JMenuItem dropAuctionItem) {
		this.dropAuctionItem = dropAuctionItem;
	}

	public Gui getGui() {
		return gui;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	public MySpanTableModel getModel() {
		return model;
	}

	public void setModel(MySpanTableModel model) {
		this.model = model;
	}

	public MainWindowListener getListener() {
		return listener;
	}

	public void setListener(MainWindowListener listener) {
		this.listener = listener;
	}

	public MultiSpanCellTable getTable() {
		return table;
	}

	public void setTable(MultiSpanCellTable table) {
		this.table = table;
	}

//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (Exception e) {
//			try {
//				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//			} catch (Exception e1) {
//			}
//		}
//
//		ArrayList<Service> services = new ArrayList<Service>();
//		for (int i = 0; i < 10; i++) {
//
//			Service service1 = new Service("service1");
//			Service service2 = new Service("service2", Status.ACTIVE);
//			Service service3 = new Service("service3", Status.TRANSFER_STARTED);
//
//			service2.addUserEntry(new UserEntry("Paul Vlase", Offer.NO_OFFER, 100L, 25.2));
//			service2.addUserEntry(new UserEntry("Ghennadi", Offer.OFFER_ACCEPTED, 101L, 28.7));
//			service2.addUserEntry(new UserEntry("Ana", Offer.OFFER_MADE, 102L, 29.9));
//
//			service3.addUserEntry(new UserEntry("Paul Vlase", Offer.OFFER_MADE, 100L, 25.2));
//
//			services.add(service1);
//			services.add(service2);
//			services.add(service3);
//		}
//		new MainWindow(services).setVisible(true);
//	}
}