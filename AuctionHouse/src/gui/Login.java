package gui;

import interfaces.Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import config.GlobalConfig.UserType;
import config.GuiConfig;
import data.LoginCred;

/**
 * @author Ghennadi Procopciuc
 */

public class Login extends JFrame implements ActionListener {
	private static final long	serialVersionUID	= 1L;

	private Gui					gui;
	private JMenuBar			menuBar;
	private JMenu				languageMenu;
	private JPanel				formPanel;
	private JLabel				usernameLabel;
	private JTextField			usernameField;
	private JLabel				passwordLabel;
	private JPasswordField		passwordField;
	private JLabel				roleLabel;
	private JComboBox<String>	roleCb;
	private JPanel				bottomPannel;
	private JButton				signinButton;
	private ButtonGroup			buttonGroup;

	public Login(Gui gui) {
		this.gui = gui;
		initComponents();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initComponents() {
		menuBar = new JMenuBar();
		languageMenu = new JMenu();
		formPanel = new JPanel();
		usernameLabel = new JLabel();
		usernameField = new JTextField();
		passwordLabel = new JLabel();
		passwordField = new JPasswordField();
		roleLabel = new JLabel();
		roleCb = new JComboBox<String>();
		bottomPannel = new JPanel();
		signinButton = new JButton();
		buttonGroup = new ButtonGroup();

		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 15, 143, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 15, 0, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 1.0, 0.0, 1.0,
				1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0,
				1.0E-4 };

		signinButton.addActionListener(this);

		// roleCb
		{
			roleCb.addItem(GuiConfig.getValue(GuiConfig.BUYER));
			roleCb.addItem(GuiConfig.getValue(GuiConfig.SELLER));
		}

		// menuBar
		{

			// languageMenu
			{
				languageMenu.setText(GuiConfig.getValue(GuiConfig.LANGUAGE));

				for (int i = 0; i < GuiConfig.LANGUAGES.length; i++) {
					String[] language = GuiConfig.LANGUAGES[i];
					JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem();
					menuItem.setText(language[0]);
					menuItem.addActionListener(this);
					menuItem.setName("" + i);

					languageMenu.add(menuItem);
					buttonGroup.add(menuItem);

				}
			}
			menuBar.add(languageMenu);
		}
		setJMenuBar(menuBar);

		// formPanel
		{
			formPanel.setLayout(new GridBagLayout());
			((GridBagLayout) formPanel.getLayout()).columnWidths = new int[] { 0, 96, 0 };
			((GridBagLayout) formPanel.getLayout()).rowHeights = new int[] { 0, 0, 0, 0 };
			((GridBagLayout) formPanel.getLayout()).columnWeights = new double[] { 0.0, 1.0, 1.0E-4 };
			((GridBagLayout) formPanel.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0,
					1.0E-4 };

			// usernameLabel
			usernameLabel.setText(GuiConfig.getValue(GuiConfig.USERNAME));
			formPanel.add(usernameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));
			formPanel.add(usernameField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
					0));

			// passwordLabel
			passwordLabel.setText(GuiConfig.getValue(GuiConfig.PASSWORD));
			formPanel.add(passwordLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));
			formPanel.add(passwordField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
					0));

			// roleLabel
			roleLabel.setText(GuiConfig.getValue(GuiConfig.ROLE));
			formPanel.add(roleLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));
			formPanel.add(roleCb, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
					0));
		}
		contentPane.add(formPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// bottomPannel
		{
			bottomPannel.setLayout(new GridBagLayout());
			((GridBagLayout) bottomPannel.getLayout()).columnWidths = new int[] { 0, 0, 0 };
			((GridBagLayout) bottomPannel.getLayout()).rowHeights = new int[] { 15, 0, 0 };
			((GridBagLayout) bottomPannel.getLayout()).columnWeights = new double[] { 1.0, 0.0,
					1.0E-4 };
			((GridBagLayout) bottomPannel.getLayout()).rowWeights = new double[] { 0.0, 0.0, 1.0E-4 };

			// loginButton
			signinButton.setText(GuiConfig.getValue(GuiConfig.LOG_IN));
			bottomPannel.add(signinButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
					0));
		}

		contentPane.add(bottomPannel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		setTitle(GuiConfig.getValue(GuiConfig.LOGIN_TITLE));

		setResizable(false);
		setSize(310, 205);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		new Login(null).setVisible(true);
	}

	private void updateLanguage() {
		setTitle(GuiConfig.getValue(GuiConfig.LOGIN_TITLE));

		/* Update language menu */
		for (int i = 0; i < GuiConfig.LANGUAGES.length; i++) {
			languageMenu.getItem(i).setText(GuiConfig.LANGUAGES[i][GuiConfig.CURRENT_LANGUAGE]);

		}

		languageMenu.setText(GuiConfig.getValue(GuiConfig.LANGUAGE));

		usernameLabel.setText(GuiConfig.getValue(GuiConfig.USERNAME));
		passwordLabel.setText(GuiConfig.getValue(GuiConfig.PASSWORD));
		roleLabel.setText(GuiConfig.getValue(GuiConfig.ROLE));

		/* Update items in role combo box */
		roleCb.removeAllItems();
		roleCb.addItem(GuiConfig.getValue(GuiConfig.BUYER));
		roleCb.addItem(GuiConfig.getValue(GuiConfig.SELLER));

		signinButton.setText(GuiConfig.getValue(GuiConfig.LOG_IN));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UserType userType;

		if (e.getSource() instanceof JCheckBoxMenuItem) {
			JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
			Integer languageId = Integer.parseInt(menuItem.getName());

			GuiConfig.setLanguage(languageId);
			updateLanguage();
		}

		if (e.getSource() == signinButton) {
			if (usernameField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						GuiConfig.getValue(GuiConfig.EMPTY_USERNAME_ERROR),
						GuiConfig.getValue(GuiConfig.EMPTY_USERNAME), JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (passwordField.getPassword().length == 0) {
				JOptionPane.showMessageDialog(null,
						GuiConfig.getValue(GuiConfig.EMPTY_PASSWORD_ERROR),
						GuiConfig.getValue(GuiConfig.EMPTY_PASSWORD), JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (roleCb.getSelectedItem().toString().equals(GuiConfig.getValue(GuiConfig.BUYER))) {
				userType = UserType.BUYER;
			} else {
				userType = UserType.SELLER;
			}

			LoginCred cred = new LoginCred(usernameField.getText(), new String(
					passwordField.getPassword()), userType);
			gui.logIn(cred);
		}

	}

	public void showWindow() {
		setVisible(true);
	}

	public void hideWindow() {
		setVisible(false);
	}
}
