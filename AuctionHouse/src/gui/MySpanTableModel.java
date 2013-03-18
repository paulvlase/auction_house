package gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import spantable.CellAttribute;
import spantable.CellSpan;
import spantable.DefaultCellAttribute;
import spantable.Span;
import data.Service;
import data.UserEntry;

public class MySpanTableModel extends AbstractTableModel {

	private static final long				serialVersionUID	= 1L;

	private ArrayList<ArrayList<Object>>	data;
	private ArrayList<String>				columns;
	private ArrayList<Service>				services;
	private ArrayList<Span>					spans;
	protected CellAttribute					cellAtt;

	public MySpanTableModel(ArrayList<Service> services, ArrayList<String> columns) {
		this.services = services;
		this.columns = columns;

		buildData();
//		System.out.println("Nr rows : " + getRowCount());
//		System.out.println("Nr Columns : " + getColumnCount());
		cellAtt = new DefaultCellAttribute(getRowCount(), getColumnCount());
		applySpans();
	}
	
	private void applySpans(){
		for (Span span : spans) {
			System.out.println(span);
			((CellSpan) cellAtt).combine(span);
		}
	}

	public void addSpan(Span span) {
		if (spans == null) {
			spans = new ArrayList<Span>();
		}

		if (!spans.contains(span)) {
			((CellSpan) cellAtt).combine(span);
			spans.add(span);
			fireTableStructureChanged();
		}
	}

	public void removeSpan(Span obj) {
		Boolean found = false;
		for (Span span : spans) {
			if (span.equals(obj)) {
				found = true;
				((CellSpan) cellAtt).split(span);
				break;
			}
		}

		if (found) {
			spans.remove(obj);
			fireTableStructureChanged();
		}
	}

	public void clearSpans() {
		for (Span span : spans) {
			((CellSpan) cellAtt).split(span);
		}

		spans.clear();
		fireTableStructureChanged();
	}

	/**
	 * Builds "data" from an array of services
	 */
	private void buildData() {
		System.out.println("Here");
		ArrayList<UserEntry> users;
		ArrayList<Object> row;
		Boolean first;

		data = new ArrayList<ArrayList<Object>>();
		spans = new ArrayList<Span>();

		for (Service service : services) {
			users = service.getUsers();

			if (users == null) {
				row = new ArrayList<Object>();
				/* Service Name */
				row.add(service.getServiceName());
				/* Status */
				row.add("Inactive");
				/* User */
				row.add("");
				/* Offer made */
				row.add("");
				/* Time */
				row.add("");
				/* Price */
				row.add("");
				data.add(row);
				spans.add(new Span(data.size() - 1, 2, 1, 4));
			} else {
				first = true;
				for (UserEntry user : users) {
					row = new ArrayList<Object>();
					if (first) {
						/* Service Name */
						row.add(service.getServiceName());
						/* Status */
						row.add("Active");
						first = false;
					} else {
						/* Service Name */
						row.add("");
						/* Status */
						row.add("");
					}
					/* User */
					row.add(user.getName());
					/* Offer made */
					row.add(user.getOffer());
					/* Time */
					row.add(user.getTime());
					/* Price */
					row.add(user.getPrice());
					data.add(row);
				}

				/* Service name span */
				spans.add(new Span(data.size() - users.size(), 0, users.size(), 1));
				/* Status span */
				spans.add(new Span(data.size() - users.size(), 1, users.size(), 1));
			}
		}
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return columns.get(column);
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

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data.get(rowIndex).set(columnIndex, aValue);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
