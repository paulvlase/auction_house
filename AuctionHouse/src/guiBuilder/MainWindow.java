/*
 * Created by JFormDesigner on Sun Mar 17 19:16:20 EET 2013
 */

package guiBuilder;

import java.awt.*;
import javax.swing.*;

/**
 * @author Ghennadi Procopciuc
 */
public class MainWindow extends JFrame {
	public MainWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
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
		scrollPane1 = new JScrollPane();
		table1 = new JTable();

		//======== this ========
		setIconImage(new ImageIcon("C:\\Users\\Ghennadi\\Desktop\\icons\\Auction.png").getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {15, 0, 10, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {14, 0, 10, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

		//======== menuBar1 ========
		{

			//======== menu ========
			{
				menu.setText("Menu");

				//---- addServiceItem ----
				addServiceItem.setText("Add service");
				menu.add(addServiceItem);

				//---- profileItem ----
				profileItem.setText("Profile");
				menu.add(profileItem);

				//---- logoutItem ----
				logoutItem.setText("Logout");
				menu.add(logoutItem);

				//---- exitItem ----
				exitItem.setText("Exit");
				menu.add(exitItem);
			}
			menuBar1.add(menu);
		}
		setJMenuBar(menuBar1);

		//======== mainPanel ========
		{
			mainPanel.setLayout(new GridBagLayout());
			((GridBagLayout)mainPanel.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)mainPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
			((GridBagLayout)mainPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)mainPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

			//======== topPanel ========
			{
				topPanel.setLayout(new GridBagLayout());
				((GridBagLayout)topPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
				((GridBagLayout)topPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
				((GridBagLayout)topPanel.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)topPanel.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
				topPanel.add(avatarLabel, new GridBagConstraints(1, 0, 1, 3, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- usernameLabel ----
				usernameLabel.setText("Ghennadi Procopciuc");
				topPanel.add(usernameLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- logoutButton ----
				logoutButton.setText("Logout");
				logoutButton.setIcon(new ImageIcon("C:\\Users\\Ghennadi\\git\\auction_house\\AuctionHouse\\resources\\images\\logout.png"));
				topPanel.add(logoutButton, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));
			}
			mainPanel.add(topPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(table1);
			}
			mainPanel.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(mainPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu;
	private JMenuItem addServiceItem;
	private JMenuItem profileItem;
	private JMenuItem logoutItem;
	private JMenuItem exitItem;
	private JPanel mainPanel;
	private JPanel topPanel;
	private JLabel avatarLabel;
	private JLabel usernameLabel;
	private JButton logoutButton;
	private JScrollPane scrollPane1;
	private JTable table1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
