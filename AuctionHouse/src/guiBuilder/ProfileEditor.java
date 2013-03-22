/*
 * Created by JFormDesigner on Fri Mar 22 22:12:33 EET 2013
 */

package guiBuilder;

import java.awt.*;
import javax.swing.*;

/**
 * @author Ghennadi Procopciuc
 */
public class ProfileEditor extends JFrame {
	public ProfileEditor() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		topPanel = new JPanel();
		avatraLabel = new JLabel();
		nameLabel = new JLabel();
		separator1 = new JSeparator();
		middlePanel = new JPanel();
		usernameLabel = new JLabel();
		usernameLabelValue = new JLabel();
		firstnameLabel = new JLabel();
		firstnameField = new JTextField();
		newPasswordLabel = new JLabel();
		newPasswordField = new JPasswordField();
		lastnameLabel = new JLabel();
		lastnameField = new JTextField();
		newPasswordRetype = new JLabel();
		newPasswordRetypepasswordField = new JPasswordField();
		locationLabel = new JLabel();
		locationField = new JTextField();
		separator2 = new JSeparator();
		panel1 = new JPanel();
		cancelButton = new JButton();
		okButton = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {15, 0, 10, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {15, 0, 15, 0, 0, 0, 15, 0, 10, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//======== topPanel ========
		{
			topPanel.setLayout(new GridBagLayout());
			((GridBagLayout)topPanel.getLayout()).columnWidths = new int[] {105, 0, 0};
			((GridBagLayout)topPanel.getLayout()).rowHeights = new int[] {100, 0};
			((GridBagLayout)topPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
			((GridBagLayout)topPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//---- avatraLabel ----
			avatraLabel.setBorder(UIManager.getBorder("TitledBorder.border"));
			avatraLabel.setIcon(new ImageIcon(getClass().getResource("/resources/images/default_avatar.png")));
			topPanel.add(avatraLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- nameLabel ----
			nameLabel.setText("<html><b><font  size=\"15\" align=\"right\">Ghennadi<br/>Procopciuc</font></b></html>");
			nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			nameLabel.setToolTipText("Press image to change your profile picture.");
			topPanel.add(nameLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(topPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(separator1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//======== middlePanel ========
		{
			middlePanel.setLayout(new GridBagLayout());
			((GridBagLayout)middlePanel.getLayout()).columnWidths = new int[] {0, 0, 15, 0, 0, 0};
			((GridBagLayout)middlePanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
			((GridBagLayout)middlePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0, 1.0E-4};
			((GridBagLayout)middlePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

			//---- usernameLabel ----
			usernameLabel.setText("Username");
			middlePanel.add(usernameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- usernameLabelValue ----
			usernameLabelValue.setText("text");
			middlePanel.add(usernameLabelValue, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- firstnameLabel ----
			firstnameLabel.setText("First name");
			middlePanel.add(firstnameLabel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			middlePanel.add(firstnameField, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- newPasswordLabel ----
			newPasswordLabel.setText("New password");
			middlePanel.add(newPasswordLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			middlePanel.add(newPasswordField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- lastnameLabel ----
			lastnameLabel.setText("Last name");
			middlePanel.add(lastnameLabel, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			middlePanel.add(lastnameField, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- newPasswordRetype ----
			newPasswordRetype.setText("New password");
			middlePanel.add(newPasswordRetype, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			middlePanel.add(newPasswordRetypepasswordField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- locationLabel ----
			locationLabel.setText("Location");
			middlePanel.add(locationLabel, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			middlePanel.add(locationField, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(middlePanel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(separator2, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 70, 65, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//---- cancelButton ----
			cancelButton.setText("Cancel");
			panel1.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- okButton ----
			okButton.setText("OK");
			panel1.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel1, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		setSize(435, 305);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel topPanel;
	private JLabel avatraLabel;
	private JLabel nameLabel;
	private JSeparator separator1;
	private JPanel middlePanel;
	private JLabel usernameLabel;
	private JLabel usernameLabelValue;
	private JLabel firstnameLabel;
	private JTextField firstnameField;
	private JLabel newPasswordLabel;
	private JPasswordField newPasswordField;
	private JLabel lastnameLabel;
	private JTextField lastnameField;
	private JLabel newPasswordRetype;
	private JPasswordField newPasswordRetypepasswordField;
	private JLabel locationLabel;
	private JTextField locationField;
	private JSeparator separator2;
	private JPanel panel1;
	private JButton cancelButton;
	private JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
