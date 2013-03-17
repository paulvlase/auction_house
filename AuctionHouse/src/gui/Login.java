package gui;

import java.awt.*;
import javax.swing.*;

/**
 * @author Ghennadi Procopciuc
 */
public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel formPanel;
	private JLabel usernameLabel;
	private JTextField usernameField;
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JLabel roleLabel;
	private JComboBox<String> roleCb;
	private JPanel bottomPannel;
	private JButton loginButton;
	
	public Login() {
		initComponents();
	}

	private void initComponents() {
		formPanel = new JPanel();
		usernameLabel = new JLabel();
		usernameField = new JTextField();
		passwordLabel = new JLabel();
		passwordField = new JPasswordField();
		roleLabel = new JLabel();
		roleCb = new JComboBox<String>();
		bottomPannel = new JPanel();
		loginButton = new JButton();

		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] {
				15, 143, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 15,
				0, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] {
				0.0, 0.0, 0.0, 1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] {
				0.0, 0.0, 0.0, 0.0, 1.0E-4 };

		// ======== formPanel ========
		{
			formPanel.setLayout(new GridBagLayout());
			((GridBagLayout) formPanel.getLayout()).columnWidths = new int[] {
					0, 96, 0 };
			((GridBagLayout) formPanel.getLayout()).rowHeights = new int[] { 0,
					0, 0, 0 };
			((GridBagLayout) formPanel.getLayout()).columnWeights = new double[] {
					0.0, 1.0, 1.0E-4 };
			((GridBagLayout) formPanel.getLayout()).rowWeights = new double[] {
					0.0, 0.0, 0.0, 1.0E-4 };

			// ---- usernameLabel ----
			usernameLabel.setText("Username");
			formPanel.add(usernameLabel, new GridBagConstraints(0, 0, 1, 1,
					0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
			formPanel.add(usernameField, new GridBagConstraints(1, 0, 1, 1,
					0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));

			// ---- passwordLabel ----
			passwordLabel.setText("Password");
			formPanel.add(passwordLabel, new GridBagConstraints(0, 1, 1, 1,
					0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
			formPanel.add(passwordField, new GridBagConstraints(1, 1, 1, 1,
					0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));

			// ---- roleLabel ----
			roleLabel.setText("Role");
			formPanel.add(roleLabel, new GridBagConstraints(0, 2, 1, 1, 0.0,
					0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			formPanel.add(roleCb, new GridBagConstraints(1, 2, 1, 1, 0.0,
					0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(formPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 5, 5), 0, 0));

		// ======== bottomPannel ========
		{
			bottomPannel.setLayout(new GridBagLayout());
			((GridBagLayout) bottomPannel.getLayout()).columnWidths = new int[] {
					0, 0, 0 };
			((GridBagLayout) bottomPannel.getLayout()).rowHeights = new int[] {
					15, 0, 0 };
			((GridBagLayout) bottomPannel.getLayout()).columnWeights = new double[] {
					1.0, 0.0, 1.0E-4 };
			((GridBagLayout) bottomPannel.getLayout()).rowWeights = new double[] {
					0.0, 0.0, 1.0E-4 };

			// ---- loginButton ----
			loginButton.setText("Sign in");
			bottomPannel.add(loginButton, new GridBagConstraints(1, 1, 1, 1,
					0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(bottomPannel, new GridBagConstraints(1, 2, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		setLocationRelativeTo(null);
	}
}
