package gui;

import interfaces.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import spantable.MultiSpanCellTable;
import config.GuiConfig;
import data.Service;
import data.Service.Status;
import data.UserEntry;
import data.UserEntry.Offer;

/**
 * @author Ghennadi Procopciuc
 */

public class MainWindow extends JFrame implements ActionListener {

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

	private Gui					gui;
	private String[]			tableComuns;
	private MultiSpanCellTable	table;
	private MySpanTableModel	model;

	private ArrayList<Service>	services;

	private MyTableCellRenderer	progressRenderer;

	public MainWindow(ArrayList<Service> services) {
		this.services = services;

		initComponents();
	}

	public MainWindow(Gui gui) {
		// this(gui.loadSerives());
	}

	private void initComponents() {
		/* Table init */
		tableComuns = new String[] { GuiConfig.getValue(GuiConfig.SERVICE),
				GuiConfig.getValue(GuiConfig.STATUS), GuiConfig.getValue(GuiConfig.SELLER),
				GuiConfig.getValue(GuiConfig.OFFER_MADE), GuiConfig.getValue(GuiConfig.TIME),
				GuiConfig.getValue(GuiConfig.PRICE) };
		model = new MySpanTableModel(services, new ArrayList<String>(Arrays.asList(tableComuns)));
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

		table.setCellSelectionEnabled(true);

		scrollPanel.getViewport().setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setIntercellSpacing(new Dimension());
		table.setShowGrid(false);
		
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		// Listeners
		{
			signOutButton.addActionListener(this);
			addServiceItem.addActionListener(this);
			signOutItem.addActionListener(this);
			profileItem.addActionListener(this);
			exitItem.addActionListener(this);
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
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} catch (Exception e1) {
			}
		}

		ArrayList<Service> services = new ArrayList<Service>();
		for (int i = 0; i < 10; i++) {

			Service service1 = new Service("service1");
			Service service2 = new Service("service2", Status.ACTIVE);
			Service service3 = new Service("service3", Status.TRANSFER_STARTED);

			service2.addUserEntry(new UserEntry("Paul Vlase", Offer.NO_OFFER, 100L, 25.2));
			service2.addUserEntry(new UserEntry("Ghennadi", Offer.OFFER_ACCEPTED, 101L, 28.7));
			service2.addUserEntry(new UserEntry("Ana", Offer.OFFER_MADE, 102L, 29.9));

			service3.addUserEntry(new UserEntry("Paul Vlase", Offer.OFFER_MADE, 100L, 25.2));

			services.add(service1);
			services.add(service2);
			services.add(service3);
		}
		new MainWindow(services).setVisible(true);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addServiceItem) {
			System.out.println("addServiceAction");
			addServiceAction();
			return;
		}
		if (e.getSource() == profileItem) {
			System.out.println("profileAction");
			profileAction();
			return;
		}
		if (e.getSource() == exitItem) {
			System.out.println("profileAction");
			exitAction();
			return;
		}
		if (e.getSource() == signOutButton || e.getSource() == signOutItem) {
			System.out.println("signOutAction");
			signOutAction();
			return;
		}
	}

	private void addServiceAction() {
		System.out.println("Add Service action");

		new AddNewService(this).setVisible(true);
		
		if(progressRenderer == table.getColumnModel().getColumn(2).getCellRenderer()){
			System.out.println("Same .. :(((((");
		} else {
			System.out.println("Rendereres are no the same ..");
		}
	}

	private void profileAction() {
		System.out.println("Profile action");
	}

	private void exitAction() {
		System.out.println("Exit Action");
	}

	private void signOutAction() {
		System.out.println("TODO signOutAction");
	}

	public void showWindow() {
		setVisible(true);
	}
}