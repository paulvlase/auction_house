package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
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
		this.services = (ArrayList<Service>) services.clone();
		this.columns = columns;

		data = new ArrayList<ArrayList<Object>>();
		cellAtt = new DefaultCellAttribute(0, getColumnCount());

		for (Service service : services) {
			addService(service);
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

	public void removeService(Integer index) {
		data = new ArrayList<ArrayList<Object>>();
		cellAtt = new DefaultCellAttribute(0, getColumnCount());
		clearSpans();

		services.remove(index.intValue());

		for (Service serv : services) {
			addService(serv);
		}
	}

	public void removeService(Service service) {
		removeService(services.indexOf(service));
	}

	public void addService(Service service) {
		ArrayList<UserEntry> users;
		ArrayList<ArrayList<Object>> serviceData;
		
		users = service.getUsers();
		serviceData = service.getAsTable();
		
		data.addAll(serviceData);
		cellAtt.addRows(serviceData.size());
		
		if(serviceData.size() == 1){
			addSpan(new Span(data.size() - 1, 2, 1, 4));
		} else {
			/* Service name span */
			addSpan(new Span(data.size() - users.size(), 0, users.size(), 1));
			/* Status span */
			addSpan(new Span(data.size() - users.size(), 1, users.size(), 1));
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
