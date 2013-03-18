/*
 * (swing1.1beta3)
 * 
 */
package spantable;

import java.awt.*;

/**
 * @version 1.0 11/22/98
 */

public class DefaultCellAttribute implements CellAttribute, CellSpan {

	protected int		rowSize;
	protected int		columnSize;
	protected int[][][]	span;		// CellSpan
	protected Color[][]	background; //

	public DefaultCellAttribute() {
		this(1, 1);
	}

	public DefaultCellAttribute(int numRows, int numColumns) {
		setSize(new Dimension(numColumns, numRows));
	}

	protected void initValue() {
		for (int i = 0; i < span.length; i++) {
			for (int j = 0; j < span[i].length; j++) {
				span[i][j][CellSpan.COLUMN] = 1;
				span[i][j][CellSpan.ROW] = 1;
			}
		}
	}

	//
	// CellSpan
	//
	public int[] getSpan(int row, int column) {
		if (isOutOfBounds(row, column)) {
			int[] ret_code = { 1, 1 };
			return ret_code;
		}
		return span[row][column];
	}

	public void setSpan(int[] span, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		this.span[row][column] = span;
	}

	public boolean isVisible(int row, int column) {
		if (isOutOfBounds(row, column))
			return false;
		if ((span[row][column][CellSpan.COLUMN] < 1) || (span[row][column][CellSpan.ROW] < 1))
			return false;
		return true;
	}

	@Override
	public void split(Span span) {
		split(span.getRow(), span.getColumn());
	}

	public void split(int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		int columnSpan = span[row][column][CellSpan.COLUMN];
		int rowSpan = span[row][column][CellSpan.ROW];
		for (int i = 0; i < rowSpan; i++) {
			for (int j = 0; j < columnSpan; j++) {
				span[row + i][column + j][CellSpan.COLUMN] = 1;
				span[row + i][column + j][CellSpan.ROW] = 1;
			}
		}
	}

	public void combine(int row, int column, int height, int width) {
		if (isOutOfBounds(row, column, height, width)) {
			System.out.println("Span out of bounds : " + new Span(row, column, height, width));
			return;
		}

		int rowSpan = height;
		int columnSpan = width;
		int startRow = row;
		int startColumn = column;
		
		for (int i = 0; i < rowSpan; i++) {
			for (int j = 0; j < columnSpan; j++) {
				if ((span[startRow + i][startColumn + j][CellSpan.COLUMN] != 1)
						|| (span[startRow + i][startColumn + j][CellSpan.ROW] != 1)) {
					return;
				}
			}
		}
		for (int i = 0, ii = 0; i < rowSpan; i++, ii--) {
			for (int j = 0, jj = 0; j < columnSpan; j++, jj--) {
				span[startRow + i][startColumn + j][CellSpan.COLUMN] = jj;
				span[startRow + i][startColumn + j][CellSpan.ROW] = ii;
			}
		}
		span[startRow][startColumn][CellSpan.COLUMN] = columnSpan;
		span[startRow][startColumn][CellSpan.ROW] = rowSpan;
	}

	@Override
	public void combine(Span span) {
		combine(span.getRow(), span.getColumn(), span.getHeight(), span.getWidth());
	}

	public Color getBackground(int row, int column) {
		if (isOutOfBounds(row, column))
			return null;
		return background[row][column];
	}

	public void setBackground(Color color, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		background[row][column] = color;
	}

	public void setBackground(Color color, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns))
			return;
		setValues(background, color, rows, columns);
	}

	//
	// CellAttribute
	//
	public void addColumn() {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows][numColumns + 1][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numRows; i++) {
			span[i][numColumns][CellSpan.COLUMN] = 1;
			span[i][numColumns][CellSpan.ROW] = 1;
		}
	}

	public void addRow() {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numColumns; i++) {
			span[numRows][i][CellSpan.COLUMN] = 1;
			span[numRows][i][CellSpan.ROW] = 1;
		}
	}

	public void insertRow(int row) {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		if (0 < row) {
			System.arraycopy(oldSpan, 0, span, 0, row - 1);
		}
		System.arraycopy(oldSpan, 0, span, row, numRows - row);
		for (int i = 0; i < numColumns; i++) {
			span[row][i][CellSpan.COLUMN] = 1;
			span[row][i][CellSpan.ROW] = 1;
		}
	}

	public Dimension getSize() {
		return new Dimension(rowSize, columnSize);
	}

	public void setSize(Dimension size) {
		columnSize = size.width;
		rowSize = size.height;
		span = new int[rowSize][columnSize][2];
		background = new Color[rowSize][columnSize];
		initValue();
	}

	protected boolean isOutOfBounds(int[] rows, int[] columns) {
		for (int i = 0; i < rows.length; i++) {
			if ((rows[i] < 0) || (rowSize <= rows[i]))
				return true;
		}
		for (int i = 0; i < columns.length; i++) {
			if ((columns[i] < 0) || (columnSize <= columns[i]))
				return true;
		}
		return false;
	}

	protected boolean isOutOfBounds(int row, int column) {
		if ((row < 0) || (rowSize <= row) || (column < 0) || (columnSize <= column)) {
			return true;
		}
		return false;
	}

	protected boolean isOutOfBounds(int row, int column, int height, int width) {
//		System.out.println(">>>>Rowsize : " + rowSize);
//		System.out.println("Columnsize : " + columnSize);
//		System.out.println("Row : " + row + " column : " + column);
//		System.out.println("Width : " + width + " height "+ height);
		if (row < 0 || row + width > rowSize) {
//			System.out.println("Row condition");
			return true;
		}

		if (column < 0 || column + height > columnSize) {
//			System.out.println("Row Column");
			return true;
		}

		return false;
	}

	protected void setValues(Object[][] target, Object value, int[] rows, int[] columns) {
		for (int i = 0; i < rows.length; i++) {
			int row = rows[i];
			for (int j = 0; j < columns.length; j++) {
				int column = columns[j];
				target[row][column] = value;
			}
		}
	}
}
