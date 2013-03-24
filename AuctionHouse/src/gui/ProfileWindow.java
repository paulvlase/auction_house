package gui;

import interfaces.Gui;
import interfaces.Window;

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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.GuiConfig;

/**
 * @author Ghennadi Procopciuc
 */
public class ProfileWindow extends JFrame implements ActionListener, MouseListener, Window {

	private static final long	serialVersionUID	= 1L;

	private JPanel				topPanel;
	private JLabel				avatarLabel;
	private JLabel				nameLabel;
	private JSeparator			separator1;
	private JPanel				middlePanel;
	private JLabel				usernameLabel;
	private JLabel				usernameLabelValue;
	private JLabel				firstnameLabel;
	private JTextField			firstnameField;
	private JLabel				newPasswordLabel;
	private JPasswordField		newPasswordField;
	private JLabel				lastnameLabel;
	private JTextField			lastnameField;
	private JLabel				newPasswordRetype;
	private JPasswordField		newPasswordRetypePasswordField;
	private JLabel				locationLabel;
	private JTextField			locationField;
	private JSeparator			separator2;
	private JPanel				panel1;
	private JButton				cancelButton;
	private JButton				okButton;

	private Gui					gui;

	public ProfileWindow() {
		initComponents();
	}

	public ProfileWindow(Gui gui) {
		this.gui = gui;
		initComponents();
		initData();
	}

	private void initData() {
		System.out.println(gui.getUserProfile());
		if (gui.getUserProfile().getAvatar() != null) {
			setAvatar(new ImageIcon(gui.getUserProfile().getAvatar()));
		}

		setName(gui.getUserProfile().getFirstName(), gui.getUserProfile().getLastName());
		setLocation(gui.getUserProfile().getLocation());
		setUsername(gui.getUserProfile().getUsername());
	}

	private void initComponents() {
		topPanel = new JPanel();
		avatarLabel = new JLabel();
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
		newPasswordRetypePasswordField = new JPasswordField();
		locationLabel = new JLabel();
		locationField = new JTextField();
		separator2 = new JSeparator();
		panel1 = new JPanel();
		cancelButton = new JButton();
		okButton = new JButton();

		// this
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 15, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 15, 0, 15, 0, 0, 0, 15,
				0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 0.0, 1.0, 0.0,
				1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4 };

		// topPanel
		{
			topPanel.setLayout(new GridBagLayout());
			((GridBagLayout) topPanel.getLayout()).columnWidths = new int[] { 105, 0, 0 };
			((GridBagLayout) topPanel.getLayout()).rowHeights = new int[] { 100, 0 };
			((GridBagLayout) topPanel.getLayout()).columnWeights = new double[] { 0.0, 1.0, 1.0E-4 };
			((GridBagLayout) topPanel.getLayout()).rowWeights = new double[] { 0.0, 1.0E-4 };

			// avatraLabel
			avatarLabel.addMouseListener(this);
			avatarLabel.setBorder(UIManager.getBorder("TitledBorder.border"));
			avatarLabel.setIcon(new ImageIcon(GuiConfig.DEFAULT_AVATAR));
			avatarLabel.setToolTipText(GuiConfig.getValue(GuiConfig.CHANGE_PICTURE));

			topPanel.add(avatarLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));

			// nameLabel
			nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			topPanel.add(nameLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
					0));
		}
		contentPane.add(topPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(separator1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
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

			// usernameLabel
			usernameLabel.setText(GuiConfig.getValue(GuiConfig.USERNAME));
			middlePanel.add(usernameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));

			// usernameLabelValue
			usernameLabelValue.setText("");
			middlePanel.add(usernameLabelValue, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));

			// firstnameLabel
			firstnameLabel.setText(GuiConfig.getValue(GuiConfig.FIRST_NAME));
			middlePanel.add(firstnameLabel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));
			middlePanel.add(firstnameField, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
					0));

			// newPasswordLabel
			newPasswordLabel.setText(GuiConfig.getValue(GuiConfig.NEW_PASSWORD));
			middlePanel.add(newPasswordLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));
			middlePanel.add(newPasswordField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));

			// lastnameLabel
			lastnameLabel.setText(GuiConfig.getValue(GuiConfig.LAST_NAME));
			middlePanel.add(lastnameLabel, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0,
					0));
			middlePanel.add(lastnameField, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
					0));

			// newPasswordRetype
			newPasswordRetype.setText(GuiConfig.getValue(GuiConfig.NEW_PASSWORD));
			middlePanel.add(newPasswordRetype, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));
			middlePanel.add(newPasswordRetypePasswordField, new GridBagConstraints(1, 2, 1, 1, 0.0,
					0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

			// locationLabel
			locationLabel.setText(GuiConfig.getValue(GuiConfig.LOCATION));
			middlePanel.add(locationLabel, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));
			middlePanel.add(locationField, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
					0));
		}
		contentPane.add(middlePanel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(separator2, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// panel1
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout) panel1.getLayout()).columnWidths = new int[] { 0, 70, 65, 0 };
			((GridBagLayout) panel1.getLayout()).rowHeights = new int[] { 0, 0 };
			((GridBagLayout) panel1.getLayout()).columnWeights = new double[] { 1.0, 0.0, 0.0,
					1.0E-4 };
			((GridBagLayout) panel1.getLayout()).rowWeights = new double[] { 0.0, 1.0E-4 };

			// cancelButton
			cancelButton.setText(GuiConfig.getValue(GuiConfig.CANCEL));
			panel1.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
					0));
			cancelButton.addActionListener(this);

			// okButton
			okButton.setText(GuiConfig.getValue(GuiConfig.OK));
			panel1.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
					0));
			okButton.addActionListener(this);
		}
		contentPane.add(panel1, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		


		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon(GuiConfig.PROFILE_ICON16).getImage());
		icons.add(new ImageIcon(GuiConfig.PROFILE_ICON32).getImage());
		icons.add(new ImageIcon(GuiConfig.PROFILE_ICON48).getImage());
		setIconImages(icons);
		
		setSize(435, 305);
		setTitle(GuiConfig.getValue(GuiConfig.PROFILE_TITLE));
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			if (newPasswordField.getPassword().length != 0) {
				if (newPasswordRetypePasswordField.getPassword().length == 0) {
					JOptionPane.showMessageDialog(null,
							GuiConfig.getValue(GuiConfig.PASSWORD_ERROR),
							GuiConfig.getValue(GuiConfig.PASSWORD), JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (!new String(newPasswordField.getPassword()).equals(new String(
						newPasswordRetypePasswordField.getPassword()))) {
					JOptionPane.showMessageDialog(null,
							GuiConfig.getValue(GuiConfig.PASSWORD_MATCH_ERROR),
							GuiConfig.getValue(GuiConfig.PASSWORD), JOptionPane.WARNING_MESSAGE);
					return;
				}

				gui.getUserProfile().setPassword(new String(newPasswordField.getPassword()));
			}

			gui.getUserProfile().setLocation(locationField.getText());
			gui.getUserProfile().setFirstName(firstnameField.getText());
			gui.getUserProfile().setLastName(lastnameField.getText());

			gui.setUserProfile(gui.getUserProfile());
			setVisible(false);
			dispose();
		}

		if (e.getSource() == cancelButton) {
			setVisible(false);
			dispose();
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
			gui.getUserProfile().setAvatar(getBytesFromImageIcon(newAvatar));
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
	}

	private void setLocation(String location) {
		locationField.setText(location);
	}

	private void setUsername(String username) {
		usernameLabelValue.setText("<html><b>" + username + "</b></html>");
	}

	private void setName(String firstName, String lastName) {
		nameLabel.setText("<html><b><font size=\"15\" align=\"right\">" + firstName + "<br/>"
				+ lastName + "</font></b></html>");
		firstnameField.setText(firstName);
		lastnameField.setText(lastName);
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

	@Override
	public void showWindow() {
		setVisible(true);
	}

	@Override
	public void clearWindow() {
		newPasswordField.setText("");
		newPasswordRetype.setText("");
		firstnameField.setText("");
		lastnameField.setText("");
		locationField.setText("");
	}

	@Override
	public void hideWindow() {
		setVisible(false);
	}
}
