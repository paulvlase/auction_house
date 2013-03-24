/*
 * Created by JFormDesigner on Sun Mar 24 15:47:47 EET 2013
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author Ghennadi Procopciuc
 */
public class PriceWindow extends JFrame implements ActionListener {
	private static final long	serialVersionUID	= 1L;
	private JLabel				priceLabel;
	private JTextField			priceField;
	private JButton				okButton;

	public PriceWindow() {
		initComponents();
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
		priceLabel.setText("New price");
		contentPane.add(priceLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(priceField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// button1
		okButton.setText("OK");
		contentPane.add(okButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			System.out.println("Ok button");
		}
	}
}
