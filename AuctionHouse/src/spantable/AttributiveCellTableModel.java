package spantable;

/*
 * (swing1.1beta3)
 * 
 */

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 * @version 1.0 11/22/98
 */

public class AttributiveCellTableModel extends DefaultTableModel {

	private static final long	serialVersionUID	= 1L;
	protected CellAttribute		cellAtt;
	private ArrayList<Span>		spans;

	public AttributiveCellTableModel() {
		this((Vector<Object>) null, 0);
	}

	public AttributiveCellTableModel(int numRows, int numColumns) {
		Vector<Object> names = new Vector<Object>(numColumns);
		names.setSize(numColumns);
		setColumnIdentifiers(names);
		dataVector = new Vector<Object>();
		setNumRows(numRows);
		cellAtt = new DefaultCellAttribute(numRows, numColumns);
	}

	public AttributiveCellTableModel(Vector<Object> columnNames, int numRows) {
		setColumnIdentifiers(columnNames);
		dataVector = new Vector<Object>();
		setNumRows(numRows);
		cellAtt = new DefaultCellAttribute(numRows, columnNames.size());
	}

	public AttributiveCellTableModel(Object[] columnNames, int numRows) {
		this(new Vector<Object>(Arrays.asList(columnNames)), numRows);
	}

	public AttributiveCellTableModel(Vector<Object> data, Vector<Object> columnNames) {
		setDataVector(data, columnNames);
	}

	public AttributiveCellTableModel(Object[][] data, Object[] columnNames) {
		setDataVector(data, columnNames);
	}
	
	public void addSpan(Span span){
		if(spans == null){
			spans = new ArrayList<Span>();
		}
		
		if(!spans.contains(span)){
			((CellSpan) cellAtt).combine(span);
			spans.add(span);
			fireTableStructureChanged();
		}
	}
	
	public void removeSpan(Span obj){
		Boolean found = false;
		for (Span span : spans) {
			if(span.equals(obj)){
				found = true;
				((CellSpan) cellAtt).split(span);
				break;
			}
		}
		
		if(found){
			spans.remove(obj);
			fireTableStructureChanged();
		}
	}
	
	public void clearSpans(){
		for (Span span : spans) {
			((CellSpan) cellAtt).split(span);
		}
		
		spans.clear();
		fireTableStructureChanged();
	}

	public void setDataVector(Vector newData, Vector columnNames) {
		if (newData == null)
			throw new IllegalArgumentException("setDataVector() - Null parameter");
		dataVector = new Vector<Object>();

		super.setDataVector(newData, columnNames);
		dataVector = newData;

		cellAtt = new DefaultCellAttribute(dataVector.size(), columnIdentifiers.size());

		newRowsAdded(new TableModelEvent(this, 0, getRowCount() - 1, TableModelEvent.ALL_COLUMNS,
				TableModelEvent.INSERT));
	}

	public void addColumn(Object columnName, Vector columnData) {
		if (columnName == null) {
			throw new IllegalArgumentException("addColumn() - null parameter");
		}

		columnIdentifiers.addElement(columnName);
		int index = 0;
		Enumeration enumeration = dataVector.elements();
		while (enumeration.hasMoreElements()) {
			Object value;
			if ((columnData != null) && (index < columnData.size()))
				value = columnData.elementAt(index);
			else
				value = null;
			((Vector) enumeration.nextElement()).addElement(value);
			index++;
		}

		cellAtt.addColumn();

		fireTableStructureChanged();
	}

	public void addRow(Vector rowData) {
		Vector newData = null;
		if (rowData == null) {
			newData = new Vector(getColumnCount());
		} else {
			rowData.setSize(getColumnCount());
		}
		dataVector.addElement(newData);

		//
		cellAtt.addRow();

		newRowsAdded(new TableModelEvent(this, getRowCount() - 1, getRowCount() - 1,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	public void insertRow(int row, Vector rowData) {
		if (rowData == null) {
			rowData = new Vector(getColumnCount());
		} else {
			rowData.setSize(getColumnCount());
		}

		dataVector.insertElementAt(rowData, row);

		cellAtt.insertRow(row);

		newRowsAdded(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS,
				TableModelEvent.INSERT));
	}

	public CellAttribute getCellAttribute() {
		return cellAtt;
	}

	public void setCellAttribute(CellAttribute newCellAtt) {
		int numColumns = getColumnCount();
		int numRows = getRowCount();
		if ((newCellAtt.getSize().width != numColumns) || (newCellAtt.getSize().height != numRows)) {
			newCellAtt.setSize(new Dimension(numRows, numColumns));
		}
		cellAtt = newCellAtt;
		fireTableDataChanged();
	}
}
