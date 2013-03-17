package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import data.Service;

import spantable.DefaultSpanModel;
import spantable.SpanModel;
import spantable.SpanTableModel;

public class MySpanTableModel extends AbstractTableModel implements SpanTableModel {

	private static final long				serialVersionUID	= 1L;

	private ArrayList<ArrayList<Object>>	data;
	private ArrayList<String>				columns;
	private SpanModel						spanModel;
	private ArrayList<Service>				services;

	public MySpanTableModel(ArrayList<Service> services, ArrayList<String> columns) {
		this.services = services;
		this.columns = columns;
		buildData();
		
		spanModel = new DefaultSpanModel();
	}
	
	/**
	 * Builds "data" from an array of services
	 */
	private void buildData(){
		//TODO :
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

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data.get(rowIndex).set(columnIndex, aValue);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public SpanModel getSpanModel() {
		return spanModel;
	}

}
