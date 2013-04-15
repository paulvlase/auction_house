package gui;

import gui.items.AcceptOfferItem;
import gui.items.AddServiceItem;
import gui.items.DropAuctionItem;
import gui.items.DropRequestItem;
import gui.items.ExitItem;
import gui.items.LaunchRequestItem;
import gui.items.MakeOfferItem;
import gui.items.ProfileItem;
import gui.items.RefuseOfferItem;
import gui.items.RemoveServiceItem;
import gui.items.SignOutButton;
import gui.items.SignOutItem;
import gui.spantable.MultiSpanCellTable;
import interfaces.ClearWindow;
import interfaces.Gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import config.GuiConfig;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;
import data.UserProfile;
import data.UserProfile.UserRole;

/**
 * @author Ghennadi Procopciuc
 */

public class MainWindow extends JFrame implements ClearWindow {
	private static final long	serialVersionUID	= 1L;
	private static Logger logger = Logger.getLogger(MainWindow.class);

	/* Swing components */
	private JMenuBar			menuBar;
	private JMenu				menu;
	private AddServiceItem		addServiceItem;
	private ProfileItem			profileItem;
	private SignOutItem			signOutItem;
	private ExitItem			exitItem;
	private JPanel				mainPanel;
	private JPanel				topPanel;
	private JLabel				avatarLabel;
	private JLabel				usernameLabel;
	private SignOutButton		signOutButton;
	private JScrollPane			scrollPanel;

	private JPopupMenu			popupMenu;
	private LaunchRequestItem	launchRequestItem;
	private DropRequestItem		dropRequestItem;
	private JSeparator			menuSeparator;
	private AcceptOfferItem		acceptOfferItem;
	private RefuseOfferItem		refusetOfferItem;
	private MakeOfferItem		makeOfferItem;
	private DropAuctionItem		dropAuctionItem;
	private RemoveServiceItem	removeServiceItem;

	private Gui					gui;
	private String[]			tableColumns;
	private MultiSpanCellTable	table;
	private MySpanTableModel	model;

	private ArrayList<Service>	services;
	private MainWindowListener	listener;

	public MainWindow(Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.services = new ArrayList<Service>();
		this.gui = gui;
		initComponents();
		setAvatar(gui.getUserProfile().getAvatar());
		setName(gui.getUserProfile().getFirstName(), gui.getUserProfile().getLastName());
	}

	private void initComponents() {
		/* Don't move this. */
		listener = new MainWindowListener(this);

		/* Table init */
		tableColumns = new String[] {
				GuiConfig.getValue(GuiConfig.SERVICE),
				GuiConfig.getValue(GuiConfig.STATUS),
				gui.getUserProfile().getRole() == UserRole.SELLER ? GuiConfig
						.getValue(GuiConfig.BUYER) : GuiConfig.getValue(GuiConfig.SELLER),
				GuiConfig.getValue(GuiConfig.OFFER_STATUS), GuiConfig.getValue(GuiConfig.TIME),
				GuiConfig.getValue(GuiConfig.PRICE) };
		MyTableCellRenderer cellRenderer = new MyTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);

		model = new MySpanTableModel(services, new ArrayList<String>(Arrays.asList(tableColumns)));
		table = new MultiSpanCellTable(model, cellRenderer);
		menuBar = new JMenuBar();
		menu = new JMenu();
		addServiceItem = new AddServiceItem(this, gui);
		profileItem = new ProfileItem(this, gui);
		signOutItem = new SignOutItem(this, gui);
		exitItem = new ExitItem(this, gui);
		mainPanel = new JPanel();
		topPanel = new JPanel();
		avatarLabel = new JLabel();
		usernameLabel = new JLabel();
		signOutButton = new SignOutButton(this, gui);
		scrollPanel = new JScrollPane();
		popupMenu = new JPopupMenu();
		launchRequestItem = new LaunchRequestItem(this, gui);
		dropRequestItem = new DropRequestItem(this, gui);
		menuSeparator = new JSeparator();
		acceptOfferItem = new AcceptOfferItem(this, gui);
		refusetOfferItem = new RefuseOfferItem(this, gui);
		makeOfferItem = new MakeOfferItem(this, gui);
		dropAuctionItem = new DropAuctionItem(this, gui);
		removeServiceItem = new RemoveServiceItem(this, gui);

		// JPopupMenu
		{
			launchRequestItem.setText(GuiConfig.getValue(GuiConfig.LAUNCH_OFFER));
			launchRequestItem.setIcon(new ImageIcon(GuiConfig.LAUNCH_OFFER_ICON));
			dropRequestItem.setText(GuiConfig.getValue(GuiConfig.DROP_OFFER));
			dropRequestItem.setIcon(new ImageIcon(GuiConfig.DROP_OFFER_ICON));
			acceptOfferItem.setText(GuiConfig.getValue(GuiConfig.ACCEPT_OFFER));
			acceptOfferItem.setIcon(new ImageIcon(GuiConfig.ACCEPT_OFFER_ICON));
			refusetOfferItem.setText(GuiConfig.getValue(GuiConfig.REFUSE_OFFER));
			refusetOfferItem.setIcon(new ImageIcon(GuiConfig.REFUSE_OFFER_ICON));
			makeOfferItem.setText(GuiConfig.getValue(GuiConfig.MAKE_OFFER));
			makeOfferItem.setIcon(new ImageIcon(GuiConfig.MAKE_OFFER_ICON));
			dropAuctionItem.setText(GuiConfig.getValue(GuiConfig.DROP_AUCTION));
			dropAuctionItem.setIcon(new ImageIcon(GuiConfig.DROP_AUCTION_ICON));
			removeServiceItem.setText(GuiConfig.getValue(GuiConfig.REMOVE_SERVICE));
			removeServiceItem.setIcon(new ImageIcon(GuiConfig.EXIT_ICON));

			popupMenu.add(removeServiceItem);
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

			new Thread(new Runnable() {
				public void run() {
					while (true) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								table.repaint();
							}
						});
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
						}
					}
				}
			}).start();

		}

		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 15, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 14, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 0.0, 1.0, 0.0,
				1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0E-4 };

		// menuBar1
		{
			// menu
			{
				menu.setText(GuiConfig.getValue(GuiConfig.MENU));

				// addServiceItem
				addServiceItem.setText(GuiConfig.getValue(GuiConfig.ADD_SERVICE));
				addServiceItem.setIcon(new ImageIcon(GuiConfig.ADD_ICON16));
				menu.add(addServiceItem);

				// profileItem
				profileItem.setText(GuiConfig.getValue(GuiConfig.PROFILE));
				profileItem.setIcon(new ImageIcon(GuiConfig.PROFILE_ICON16));
				menu.add(profileItem);

				// logoutItem
				signOutItem.setText(GuiConfig.getValue(GuiConfig.LOG_OUT));
				signOutItem.setIcon(new ImageIcon(GuiConfig.LOGOUT_ICON));
				menu.add(signOutItem);

				// exitItem
				exitItem.setText(GuiConfig.getValue(GuiConfig.EXIT));
				exitItem.setIcon(new ImageIcon(GuiConfig.EXIT_ICON));
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
			((GridBagLayout) mainPanel.getLayout()).rowWeights = new double[] { 0.0, 1.0, 1.0E-4 };

			// topPanel
			{
				topPanel.setLayout(new GridBagLayout());
				((GridBagLayout) topPanel.getLayout()).columnWidths = new int[] { 0, 0, 0, 0, 0 };
				((GridBagLayout) topPanel.getLayout()).rowHeights = new int[] { 0, 0, 0, 0 };
				((GridBagLayout) topPanel.getLayout()).columnWeights = new double[] { 1.0, 0.0,
						0.0, 0.0, 1.0E-4 };
				((GridBagLayout) topPanel.getLayout()).rowWeights = new double[] { 1.0, 0.0, 1.0,
						1.0E-4 };

				avatarLabel.setBorder(UIManager.getBorder("TitledBorder.border"));
				topPanel.add(avatarLabel, new GridBagConstraints(1, 0, 1, 3, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5),
						0, 0));

				// usernameLabel
				usernameLabel.setText("");
				topPanel.add(usernameLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5),
						0, 0));

				// logoutButton
				signOutButton.setText(GuiConfig.getValue(GuiConfig.LOG_OUT));
				signOutButton.setIcon(new ImageIcon(GuiConfig.LOGOUT_ICON));
				signOutButton.setHorizontalTextPosition(SwingConstants.LEFT);
				topPanel.add(signOutButton, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0),
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
			setTitle(GuiConfig.getValue(GuiConfig.SELLER_TITLE));
		} else {
			setTitle(GuiConfig.getValue(GuiConfig.BUYER_TITLE));
		}

		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon(GuiConfig.AUCTION_ICON64).getImage());
		icons.add(new ImageIcon(GuiConfig.AUCTION_ICON32).getImage());
		icons.add(new ImageIcon(GuiConfig.AUCTION_ICON16).getImage());
		setIconImages(icons);

		setSize(new Dimension(700, 400));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public ActionListener getActionListener() {
		return listener;
	}

	public void addService(Service service) {
		if (gui.getUserProfile().getRole() == UserRole.BUYER && service.getUsers() != null) {
			logger.debug("-------------------");

			Offer offer = service.getUsers().get(0).getOffer();
			if (offer == Offer.TRANSFER_COMPLETE || offer == Offer.TRANSFER_FAILED
					|| offer == Offer.TRANSFER_IN_PROGRESS || offer == Offer.TRANSFER_STARTED) {
				UserEntry user = service.getUsers().get(0);
				service.getUsers().clear();
				service.getUsers().add(user);
			}
		}
		model.addService(service);
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

	public JButton getSignOutButton() {
		return signOutButton;
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public void setMenuSeparator(JSeparator menuSeparator) {
		this.menuSeparator = menuSeparator;
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

	public void setAvatar(byte[] avatar) {
		if (avatar == null) {
			setAvatar((ImageIcon) null);
		} else {
			setAvatar(new ImageIcon(avatar));
		}
	}

	public void setAvatar(ImageIcon avatar) {
		ImageIcon newAvatar;

		if (avatar == null) {
			logger.debug(GuiConfig.DEFAULT_AVATAR);
			avatar = new ImageIcon(GuiConfig.DEFAULT_AVATAR);
		}

		newAvatar = new ImageIcon(avatar.getImage().getScaledInstance(GuiConfig.MINI_AVATAR_HEIGHT,
				GuiConfig.MINI_AVATAR_WIDTH, Image.SCALE_DEFAULT));
		avatarLabel.setIcon(newAvatar);
	}

	private void setName(String firstName, String lastName) {
		usernameLabel.setText("<html><b>" + firstName + " " + lastName + "</b></html>");
	}

	public AddServiceItem getAddServiceItem() {
		return addServiceItem;
	}

	public void setAddServiceItem(AddServiceItem addServiceItem) {
		this.addServiceItem = addServiceItem;
	}

	public ProfileItem getProfileItem() {
		return profileItem;
	}

	public void setProfileItem(ProfileItem profileItem) {
		this.profileItem = profileItem;
	}

	public ExitItem getExitItem() {
		return exitItem;
	}

	public void setExitItem(ExitItem exitItem) {
		this.exitItem = exitItem;
	}

	public LaunchRequestItem getLaunchRequestItem() {
		return launchRequestItem;
	}

	public void setLaunchRequestItem(LaunchRequestItem launchRequestItem) {
		this.launchRequestItem = launchRequestItem;
	}

	public DropRequestItem getDropRequestItem() {
		return dropRequestItem;
	}

	public void setDropRequestItem(DropRequestItem dropRequestItem) {
		this.dropRequestItem = dropRequestItem;
	}

	public AcceptOfferItem getAcceptOfferItem() {
		return acceptOfferItem;
	}

	public void setAcceptOfferItem(AcceptOfferItem acceptOfferItem) {
		this.acceptOfferItem = acceptOfferItem;
	}

	public RefuseOfferItem getRefusetOfferItem() {
		return refusetOfferItem;
	}

	public void setRefusetOfferItem(RefuseOfferItem refusetOfferItem) {
		this.refusetOfferItem = refusetOfferItem;
	}

	public MakeOfferItem getMakeOfferItem() {
		return makeOfferItem;
	}

	public void setMakeOfferItem(MakeOfferItem makeOfferItem) {
		this.makeOfferItem = makeOfferItem;
	}

	public DropAuctionItem getDropAuctionItem() {
		return dropAuctionItem;
	}

	public void setDropAuctionItem(DropAuctionItem dropAuctionItem) {
		this.dropAuctionItem = dropAuctionItem;
	}

	public JSeparator getMenuSeparator() {
		return menuSeparator;
	}

	public RemoveServiceItem getRemoveServiceItem() {
		return removeServiceItem;
	}

	public void setRemoveServiceItem(RemoveServiceItem removeServiceItem) {
		this.removeServiceItem = removeServiceItem;
	}

	public void changeServiceNotify(Service service) {
		model.changeService(service.clone());
	}

	public void changeServicesNotify(List<Service> services) {
		for (Service service : services) {
			model.changeService(service.clone());
		}
	}

	public void changeProfileNotify(UserProfile profile) {
		setAvatar(profile.getAvatar());
		setName(profile.getFirstName(), profile.getLastName());
	}
	
	@Override
	public void clear() {
		
	}
}