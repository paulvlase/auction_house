package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import spantable.MultiSpanCellTable;

import config.GuiConfig;
import config.GlobalConfig.UserType;

import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

/**
 * @author Ghennadi Procopciuc
 */
public class MainWindow extends JFrame {

	private JMenuBar			menuBar;
	private JMenu				menu;
	private JMenuItem			addServiceItem;
	private JMenuItem			profileItem;
	private JMenuItem			logoutItem;
	private JMenuItem			exitItem;
	private JPanel				mainPanel;
	private JPanel				topPanel;
	private JLabel				avatarLabel;
	private JLabel				usernameLabel;
	private JButton				logoutButton;
	private JScrollPane			scrollPanel;

	private String[]			tableComuns;
	private MultiSpanCellTable	table;
	private MySpanTableModel	model;

	private ArrayList<Service>	services;

	public MainWindow(ArrayList<Service> services) {
		this.services = services;

		initComponents();
	}

	private void initComponents() {
		/* Table init */
		tableComuns = new String[] { GuiConfig.getValue(GuiConfig.SERVICE),
				GuiConfig.getValue(GuiConfig.STATUS), GuiConfig.getValue(GuiConfig.SELLER),
				GuiConfig.getValue(GuiConfig.OFFER_MADE), GuiConfig.getValue(GuiConfig.TIME),
				GuiConfig.getValue(GuiConfig.PRICE) };
		model = new MySpanTableModel(services, new ArrayList<String>(Arrays.asList(tableComuns)));
		table = new MultiSpanCellTable(model);
		table.setCellSelectionEnabled(true);

		menuBar = new JMenuBar();
		menu = new JMenu();
		addServiceItem = new JMenuItem();
		profileItem = new JMenuItem();
		logoutItem = new JMenuItem();
		exitItem = new JMenuItem();
		mainPanel = new JPanel();
		topPanel = new JPanel();
		avatarLabel = new JLabel();
		usernameLabel = new JLabel();
		logoutButton = new JButton();
		scrollPanel = new JScrollPane();

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
				logoutItem.setText(GuiConfig.getValue(GuiConfig.LOGOUT));
				menu.add(logoutItem);

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
				logoutButton.setText(GuiConfig.getValue(GuiConfig.LOGOUT));
				topPanel.add(logoutButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
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
		// try {
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		// } catch (Exception e) {
		// try {
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		// } catch (Exception e1) {
		// }
		// }

		ArrayList<Service> services = new ArrayList<Service>();
		for (int i = 0; i < 100; i++) {

			Service service1 = new Service("service1");
			Service service2 = new Service("service2");

			service2.addUserEntry(new UserEntry("Paul Vlase", Offer.NO_OFFER, 100L, 25.2));
			service2.addUserEntry(new UserEntry("Ghennadi", Offer.OFFER_ACCEPTED, 101L, 28.7));
			service2.addUserEntry(new UserEntry("Ana", Offer.OFFER_MADE, 102L, 29.9));

			services.add(service1);
			services.add(service2);
		}
		new MainWindow(services).setVisible(true);
	}
}
