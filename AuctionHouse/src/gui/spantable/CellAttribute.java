/*
 * (swing1.1beta3)
 * 
 */
package gui.spantable;

import java.awt.*;

/**
 * @version 1.0 11/22/98
 */

public interface CellAttribute {

	public void addColumn();

	public void addRow();

	public void addRows(Integer rows);

	public void insertRow(int row);

	public Dimension getSize();

	public void setSize(Dimension size);

	public void clear();

}
