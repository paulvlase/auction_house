package gui;

import interfaces.Gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.GuiConfig;
import data.UserProfile;

/**
 * @author Ghennadi Procopciuc
 */
public class RegisterWindow extends JFrame implements ActionListener, MouseListener {
	private static final long	serialVersionUID	= 1L;
	private JPanel				topPanel;
	private JLabel				avatarLabel;
	private JPanel				panel1;
	private JLabel				usernameLabel;
	private JTextField			usernameField;
	private JButton				checkButton;
	private JPanel				middlePanel;
	private JLabel				firstnameLabel;
	private JTextField			firstnameField;
	private JLabel				locationLabel;
	private JTextField			locationField;
	private JLabel				lastnameLabel;
	private JTextField			lastnameField;
	private JLabel				emailLabel;
	private JTextField			emailField;
	private JLabel				passwordLabel;
	private JPasswordField		passwordField;
	private JLabel				passwordRetypeLabel;
	private JPasswordField		passwordRetypeField;
	private JPanel				panel2;
	private JButton				cancelButton;
	private JButton				registerButton;

	private UserProfile			userProfile;
	private Gui					gui;

	public RegisterWindow(Gui gui) {
		userProfile = new UserProfile();
		userProfile.setAvatar(getBytesFromImageIcon(new ImageIcon(GuiConfig.DEFAULT_AVATAR)));
		this.gui = gui;
		initComponents();
	}

	private void initComponents() {
		topPanel = new JPanel();
		avatarLabel = new JLabel();
		panel1 = new JPanel();
		usernameLabel = new JLabel();
		usernameField = new JTextField();
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
		passwordField = new JPasswordField();
		passwordRetypeLabel = new JLabel();
		passwordRetypeField = new JPasswordField();
		panel2 = new JPanel();
		cancelButton = new JButton();
		registerButton = new JButton();

		// this
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 15, 0, 9, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 15, 0, 15, 0, 15, 0, 10,
				0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 0.0, 1.0, 0.0,
				1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 1.0E-4 };

		// topPanel
		{
			topPanel.setLayout(new GridBagLayout());
			((GridBagLayout) topPanel.getLayout()).columnWidths = new int[] { 105, 0, 0 };
			((GridBagLayout) topPanel.getLayout()).rowHeights = new int[] { 100, 0 };
			((GridBagLayout) topPanel.getLayout()).columnWeights = new double[] { 0.0, 1.0, 1.0E-4 };
			((GridBagLayout) topPanel.getLayout()).rowWeights = new double[] { 0.0, 1.0E-4 };

			// avatarLabel
			avatarLabel.addMouseListener(this);
			avatarLabel.setBorder(UIManager.getBorder("TitledBorder.border"));
			avatarLabel.setIcon(new ImageIcon(GuiConfig.DEFAULT_AVATAR));
			avatarLabel.setBorder(new LineBorder(Color.black));
			topPanel.add(avatarLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));

			// panel1
			{
				panel1.setLayout(new GridBagLayout());
				((GridBagLayout) panel1.getLayout()).columnWidths = new int[] { 0, 0, 0 };
				((GridBagLayout) panel1.getLayout()).rowHeights = new int[] { 0, 0, 0, 0 };
				((GridBagLayout) panel1.getLayout()).columnWeights = new double[] { 0.0, 1.0,
						1.0E-4 };
				((GridBagLayout) panel1.getLayout()).rowWeights = new double[] { 1.0, 0.0, 0.0,
						1.0E-4 };

				// usernameLabel
				usernameLabel.setText(GuiConfig.getValue(GuiConfig.USERNAME));
				panel1.add(usernameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5),
						0, 0));
				panel1.add(usernameField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0),
						0, 0));

				// checkButton
				checkButton.setText(GuiConfig.getValue(GuiConfig.CHECK));
				checkButton.addActionListener(this);
				panel1.add(checkButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5),
						0, 0));
			}
			topPanel.add(panel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
					0));
		}
		contentPane.add(topPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// middlePanel
		{
			middlePanel.setLayout(new GridBagLayout());
			((GridBagLayout) middlePanel.getLayout()).columnWidths = new int[] { 0, 0, 15, 0, 0, 0 };
			((GridBagLayout) middlePanel.getLayout()).rowHeights = new int[] { 0, 0, 0, 0 };
			((GridBagLayout) middlePanel.getLayout()).columnWeights = new double[] { 0.0, 1.0, 0.0,
					0.0, 1.0, 1.0E-4 };
			((GridBagLayout) middlePanel.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0,
					1.0E-4 };

			// firstnameLabel
			firstnameLabel.setText(GuiConfig.getValue(GuiConfig.FIRST_NAME));
			middlePanel.add(firstnameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));
			middlePanel.add(firstnameField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));

			// locationLabel
			locationLabel.setText(GuiConfig.getValue(GuiConfig.LOCATION));
			middlePanel.add(locationLabel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));
			middlePanel.add(locationField, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
					0));

			// lastnameLabel
			lastnameLabel.setText(GuiConfig.getValue(GuiConfig.LAST_NAME));
			middlePanel.add(lastnameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));
			middlePanel.add(lastnameField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));

			// emailLabel
			emailLabel.setText(GuiConfig.getValue(GuiConfig.EMAIL));
			middlePanel.add(emailLabel, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));
			middlePanel.add(emailField, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
					0));

			// passwordLabel
			passwordLabel.setText(GuiConfig.getValue(GuiConfig.PASSWORD));
			middlePanel.add(passwordLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));
			middlePanel.add(passwordField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));

			// passwordRetypeLabel
			passwordRetypeLabel.setText(GuiConfig.getValue(GuiConfig.PASSWORD));
			middlePanel.add(passwordRetypeLabel, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));
			middlePanel.add(passwordRetypeField, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
					0));
		}
		contentPane.add(middlePanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// panel2
		{
			panel2.setLayout(new GridBagLayout());
			((GridBagLayout) panel2.getLayout()).columnWidths = new int[] { 0, 80, 76, 0 };
			((GridBagLayout) panel2.getLayout()).rowHeights = new int[] { 0, 0 };
			((GridBagLayout) panel2.getLayout()).columnWeights = new double[] { 1.0, 0.0, 0.0,
					1.0E-4 };
			((GridBagLayout) panel2.getLayout()).rowWeights = new double[] { 0.0, 1.0E-4 };

			// cancelButton
			cancelButton.setText(GuiConfig.getValue(GuiConfig.CANCEL));
			cancelButton.addActionListener(this);
			panel2.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));

			// registerButton
			registerButton.setText(GuiConfig.getValue(GuiConfig.REGISTER));
			registerButton.addActionListener(this);
			panel2.add(registerButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
					0));
		}
		contentPane.add(panel2, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		


		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon(GuiConfig.REGISTER_ICON16).getImage());
		icons.add(new ImageIcon(GuiConfig.REGISTER_ICON32).getImage());
		icons.add(new ImageIcon(GuiConfig.REGISTER_ICON64).getImage());
		setIconImages(icons);
		
		setSize(400, 300);
		setLocationRelativeTo(getOwner());
	}

	private void checkUsername() {
		System.out.println("Check username ...");
		if (usernameField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, GuiConfig.getValue(GuiConfig.EMPTY_USERNAME_ERROR),
					GuiConfig.getValue(GuiConfig.EMPTY_USERNAME), JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!gui.verifyUsername(usernameField.getText())) {
			JOptionPane.showMessageDialog(null, GuiConfig.getValue(GuiConfig.USERNAME_ERROR),
					GuiConfig.getValue(GuiConfig.USERNAME), JOptionPane.WARNING_MESSAGE);
			return;
		}

		userProfile.setUsername(usernameField.getText());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registerButton) {
			System.out.println("Register");
			if (userProfile.getUsername().isEmpty()) {
				checkUsername();
			}

			if (passwordField.getPassword().length == 0) {
				JOptionPane.showMessageDialog(null, GuiConfig.getValue(GuiConfig.PASSWORD_ERROR),
						GuiConfig.getValue(GuiConfig.PASSWORD), JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (!new String(passwordField.getPassword()).equals(new String(passwordRetypeField
					.getPassword()))) {
				JOptionPane.showMessageDialog(null,
						GuiConfig.getValue(GuiConfig.PASSWORD_MATCH_ERROR),
						GuiConfig.getValue(GuiConfig.PASSWORD), JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (firstnameField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, GuiConfig.getValue(GuiConfig.FIRST_NAME_ERROR),
						GuiConfig.getValue(GuiConfig.FIRST_NAME), JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (lastnameField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, GuiConfig.getValue(GuiConfig.LAST_NAME_ERROR),
						GuiConfig.getValue(GuiConfig.LAST_NAME), JOptionPane.WARNING_MESSAGE);
				return;
			}

			userProfile.setPassword(new String(passwordField.getPassword()));
			userProfile.setLocation(locationField.getText());
			userProfile.setFirstName(firstnameField.getText());
			userProfile.setLastName(lastnameField.getText());

			gui.registerUser(userProfile);
			setVisible(false);
			dispose();
		}

		if (e.getSource() == cancelButton) {
			setVisible(false);
			dispose();
		}

		if (e.getSource() == checkButton) {
			checkUsername();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				GuiConfig.getValue(GuiConfig.FILE_CHOOSER_TILE), "jpg", "gif", "png");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			ImageIcon avatar = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());

			ImageIcon newAvatar = new ImageIcon(avatar.getImage().getScaledInstance(
					GuiConfig.AVATAR_WIDTH, GuiConfig.AVATAR_WIDTH, Image.SCALE_DEFAULT));

			setAvatar(newAvatar);
		}
	}

	private byte[] getBytesFromImageIcon(ImageIcon imageIcon) {

		Image image = imageIcon.getImage();
		RenderedImage rendered = null;

		if (image instanceof RenderedImage) {
			rendered = (RenderedImage) image;
		} else {
			BufferedImage bufferred = new BufferedImage(imageIcon.getIconWidth(),
					imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferred.createGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			rendered = bufferred;
		}
		File tempFile = null;
		try {
			tempFile = File.createTempFile("temp-file-name", ".tmp");
			ImageIO.write(rendered, "JPEG", tempFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			byte[] byteArray;
			RandomAccessFile file = new RandomAccessFile(tempFile, "r");
			byteArray = new byte[(int) file.length()];
			file.readFully(byteArray);
			file.close();

			return byteArray;
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			tempFile.delete();
		}

		return null;
	}

	private void setAvatar(ImageIcon avatar) {
		avatarLabel.setIcon(avatar);
		userProfile.setAvatar(getBytesFromImageIcon(avatar));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
