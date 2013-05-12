/*
 * Created by JFormDesigner on Wed Mar 20 22:02:48 EET 2013
 */

package guiBuilder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Ghennadi Procopciuc
 */
public class AddNewService extends JFrame {
	public AddNewService() {
		initComponents();
	}

	private void okButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void cancelButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		mainPanel = new JPanel();
		nameLabel = new JLabel();
		textField1 = new JTextField();
		timeLabel = new JLabel();
		timeSpinner = new JSpinner();
		priceLabel = new JLabel();
		textField3 = new JTextField();
		bottomPanel = new JPanel();
		cancelButton = new JButton();
		okButton = new JButton();

		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 15, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 15, 0, 0, 10, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0E-4 };

		// ======== mainPanel ========
		{
			mainPanel.setLayout(new GridBagLayout());
			((GridBagLayout) mainPanel.getLayout()).columnWidths = new int[] { 0, 110, 0 };
			((GridBagLayout) mainPanel.getLayout()).rowHeights = new int[] { 0, 0, 0, 0 };
			((GridBagLayout) mainPanel.getLayout()).columnWeights = new double[] { 0.0, 0.0, 1.0E-4 };
			((GridBagLayout) mainPanel.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0E-4 };

			// ---- nameLabel ----
			nameLabel.setText("Name");
			mainPanel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
			mainPanel.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));

			// ---- timeLabel ----
			timeLabel.setText("Time");
			mainPanel.add(timeLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

			// ---- timeSpinner ----
			timeSpinner.setModel(new SpinnerDateModel(new java.util.Date((System.currentTimeMillis() / 60000) * 60000),
					new java.util.Date((System.currentTimeMillis() / 60000) * 60000), null, java.util.Calendar.MINUTE));
			timeSpinner.setPreferredSize(new Dimension(120, 20));
			mainPanel.add(timeSpinner, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));

			// ---- priceLabel ----
			priceLabel.setText("Price");
			mainPanel.add(priceLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
			mainPanel.add(textField3, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(mainPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// ======== bottomPanel ========
		{
			bottomPanel.setLayout(new GridBagLayout());
			((GridBagLayout) bottomPanel.getLayout()).columnWidths = new int[] { 0, 0, 0, 0 };
			((GridBagLayout) bottomPanel.getLayout()).rowHeights = new int[] { 15, 0, 0 };
			((GridBagLayout) bottomPanel.getLayout()).columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0E-4 };
			((GridBagLayout) bottomPanel.getLayout()).rowWeights = new double[] { 0.0, 0.0, 1.0E-4 };

			// ---- cancelButton ----
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cancelButtonActionPerformed(e);
				}
			});
			bottomPanel.add(cancelButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));

			// ---- okButton ----
			okButton.setText("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					okButtonActionPerformed(e);
				}
			});
			bottomPanel.add(okButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(bottomPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		pack();
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JPanel		mainPanel;
	private JLabel		nameLabel;
	private JTextField	textField1;
	private JLabel		timeLabel;
	private JSpinner	timeSpinner;
	private JLabel		priceLabel;
	private JTextField	textField3;
	private JPanel		bottomPanel;
	private JButton		cancelButton;
	private JButton		okButton;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
