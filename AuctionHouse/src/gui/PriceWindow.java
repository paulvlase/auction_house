package gui;

import interfaces.Gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import config.GuiConfig;
import data.Service;

/**
 * @author Ghennadi Procopciuc
 */
public class PriceWindow extends JFrame implements ActionListener {
	private static final long		serialVersionUID	= 1L;
	private JLabel					priceLabel;
	private JTextField				priceField;
	private JButton					okButton;
	private Gui						gui;
	
	private Service service;
	private Integer userIndex;

	public PriceWindow() {
		initComponents();
	}

	public PriceWindow(Gui gui, Service service, Integer userIndex) {
		this();
		this.service = service;
		this.userIndex = userIndex;
		this.gui = gui;
	}

	private void initComponents() {
		priceLabel = new JLabel();
		priceField = new JTextField();
		okButton = new JButton();

		okButton.addActionListener(this);
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 15, 103, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 15, 0, 0, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 0.0, 0.0, 0.0,
				1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0E-4 };

		// label1
		priceLabel.setText(GuiConfig.getValue(GuiConfig.NEW_PRICE));
		priceField.addActionListener(this);
		contentPane.add(priceLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(priceField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// button1
		okButton.setText(GuiConfig.getValue(GuiConfig.OK));
		contentPane.add(okButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon(GuiConfig.PRICE_ICON64).getImage());
		icons.add(new ImageIcon(GuiConfig.PRICE_ICON48).getImage());
		icons.add(new ImageIcon(GuiConfig.PRICE_ICON16).getImage());
		setIconImages(icons);
		
		pack();
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Double price = 0.0;

		if (e.getSource() == okButton || e.getSource() == priceField) {
			try {
				price = Double.parseDouble(priceField.getText());
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null, GuiConfig.getValue(GuiConfig.PRICE_ERROR), "",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		service.setMakeOfferState(userIndex, price);
		gui.publishService(new Service(service));

		setVisible(false);
		dispose();
	}
}
