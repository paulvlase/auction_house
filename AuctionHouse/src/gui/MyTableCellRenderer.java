package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import config.GuiConfig;
import data.Period;

/**
 * @author Ghennadi Procopciuc
 */
class MyTableCellRenderer extends DefaultTableCellRenderer {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(MyTableCellRenderer.class);

	private final JProgressBar	bar					= new JProgressBar(0, 100);
	private final JPanel		panel				= new JPanel(new BorderLayout());

	public MyTableCellRenderer() {
		super();

		// TODO: logger.setLevel(Level.OFF);

		setOpaque(true);
		panel.add(bar);
		panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (column == 2 || column == 3) {
			if (value instanceof Integer) {
				Integer i = (Integer) value;
				String text = GuiConfig.getValue(GuiConfig.DONE);
				if (i < 0) {
					if (i == -1) {
						text = GuiConfig.getValue(GuiConfig.CANCELED);
					}
				} else if (i <= 100) {
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

		if (column == 4) {
			Calendar cal = Calendar.getInstance();

			if (value instanceof Long) {
				logger.debug("cal.getTime(): " + cal.getTime() + " value: " + value);
				String text = "" + new Period(cal.getTime().getTime(), (Long) value);
				super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
				return this;
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
