/*
 * Created by JFormDesigner on Mon Mar 25 00:31:49 EET 2013
 */

package guiBuilder;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Ghennadi Procopciuc
 */
public class RegisterWindow extends JFrame {
	public RegisterWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		topPanel = new JPanel();
		avatarLabel = new JLabel();
		panel1 = new JPanel();
		usernameLabel = new JLabel();
		usernmaeField1 = new JTextField();
		checkButton = new JButton();
		middlePanel = new JPanel();
		firstnameLabel = new JLabel();
		firstnameField = new JTextField();
		locationLabel = new JLabel();
		locationField = new JTextField();
		lastnameLabel = new JLabel();
		lastnameField = new JTextField();
		emailLabel = new JLabel();
		emailField = new JTextField();
		passwordLabel = new JLabel();
		passwordField = new JTextField();
		passwordRetypeLabel = new JLabel();
		passwordRetypeField = new JTextField();
		panel2 = new JPanel();
		cancelButton = new JButton();
		registerButton = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {15, 0, 9, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {15, 0, 15, 0, 15, 0, 10, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//======== topPanel ========
		{
			topPanel.setLayout(new GridBagLayout());
			((GridBagLayout)topPanel.getLayout()).columnWidths = new int[] {105, 0, 0};
			((GridBagLayout)topPanel.getLayout()).rowHeights = new int[] {100, 0};
			((GridBagLayout)topPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
			((GridBagLayout)topPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//---- avatarLabel ----
			avatarLabel.setIcon(new ImageIcon("C:\\Users\\Ghennadi\\git\\auction_house\\AuctionHouse\\resources\\images\\default_avatar.png"));
			avatarLabel.setBorder(new LineBorder(Color.black));
			topPanel.add(avatarLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//======== panel1 ========
			{
				panel1.setLayout(new GridBagLayout());
				((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
				((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
				((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};

				//---- usernameLabel ----
				usernameLabel.setText("Username");
				panel1.add(usernameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				panel1.add(usernmaeField1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- checkButton ----
				checkButton.setText("Check");
				panel1.add(checkButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			topPanel.add(panel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(topPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//======== middlePanel ========
		{
			middlePanel.setLayout(new GridBagLayout());
			((GridBagLayout)middlePanel.getLayout()).columnWidths = new int[] {0, 0, 15, 0, 0, 0};
			((GridBagLayout)middlePanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
			((GridBagLayout)middlePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0, 1.0E-4};
			((GridBagLayout)middlePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

			//---- firstnameLabel ----
			firstnameLabel.setText("First name");
			middlePanel.add(firstnameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			middlePanel.add(firstnameField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- locationLabel ----
			locationLabel.setText("Location");
			middlePanel.add(locationLabel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			middlePanel.add(locationField, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- lastnameLabel ----
			lastnameLabel.setText("Last name");
			middlePanel.add(lastnameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			middlePanel.add(lastnameField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- emailLabel ----
			emailLabel.setText("Email");
			middlePanel.add(emailLabel, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			middlePanel.add(emailField, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- passwordLabel ----
			passwordLabel.setText("Password");
			middlePanel.add(passwordLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			middlePanel.add(passwordField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- passwordRetypeLabel ----
			passwordRetypeLabel.setText("Password");
			middlePanel.add(passwordRetypeLabel, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			middlePanel.add(passwordRetypeField, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(middlePanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//======== panel2 ========
		{
			panel2.setLayout(new GridBagLayout());
			((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 80, 76, 0};
			((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//---- cancelButton ----
			cancelButton.setText("Cancel");
			panel2.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- registerButton ----
			registerButton.setText("Register");
			panel2.add(registerButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel2, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		setSize(400, 300);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel topPanel;
	private JLabel avatarLabel;
	private JPanel panel1;
	private JLabel usernameLabel;
	private JTextField usernmaeField1;
	private JButton checkButton;
	private JPanel middlePanel;
	private JLabel firstnameLabel;
	private JTextField firstnameField;
	private JLabel locationLabel;
	private JTextField locationField;
	private JLabel lastnameLabel;
	private JTextField lastnameField;
	private JLabel emailLabel;
	private JTextField emailField;
	private JLabel passwordLabel;
	private JTextField passwordField;
	private JLabel passwordRetypeLabel;
	private JTextField passwordRetypeField;
	private JPanel panel2;
	private JButton cancelButton;
	private JButton registerButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
