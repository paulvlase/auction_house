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
	private final JProgressBar	b					= new JProgressBar(0, 100);
	private final JPanel		p					= new JPanel(new BorderLayout());

	public MyTableCellRenderer() {
		super();
		setOpaque(true);
		p.add(b);
		p.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		if (column == 2) {
			if (value instanceof Integer) {
				Integer i = (Integer) value;
				String text = "Done";
				if (i < 0) {
					text = "Canceled";
				} else if (i < 100) {
					b.setValue(i);
					return p;
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
		if (p != null)
			SwingUtilities.updateComponentTreeUI(p);
	}
}
