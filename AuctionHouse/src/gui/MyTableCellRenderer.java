package gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

class MyTableCellRenderer extends DefaultTableCellRenderer {

	private static final long	serialVersionUID	= 1L;
	private final JProgressBar	bar					= new JProgressBar(0, 100);
	private final JPanel		panel					= new JPanel(new BorderLayout());

	public MyTableCellRenderer() {
		super();
		setOpaque(true);
		panel.add(bar);
		panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		if (column == 2) {
			if (value instanceof Integer) {
				Integer i = (Integer) value;
				String text = "Done";
				if (i < 0) {
					if(i == -1){
						new Runnable() {
							
							@Override
							public void run() {
								bar.setIndeterminate(true);
								bar.updateUI();
							}
						};
						bar.setIndeterminate(true);
						return panel;
					} else {
						text = "Canceled";						
					}
				} else if (i < 100) {
					bar.setIndeterminate(false);
					bar.setValue(i);
					return panel;
				}
				super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
				return this;
			}

			if (value instanceof JLabel) {
				return (JLabel) value;
			}
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

	@Override
	public void updateUI() {
		super.updateUI();
		if (panel != null)
			SwingUtilities.updateComponentTreeUI(panel);
	}
}
