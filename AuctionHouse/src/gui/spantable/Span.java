package gui.spantable;

public class Span {
	private int	row;
	private int	column;
	private int	height;
	private int	width;

	public Span(int row, int column, int height, int width) {
		this.row = row;
		this.column = column;
		this.height = height;
		this.width = width;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Span)) {
			return false;
		}

		Span span = (Span) obj;
		if (span.getColumn() == column && span.getHeight() == height && span.getRow() == row
				&& span.getWidth() == width) {
			return true;
		}

		return false;
	}
	
	@Override
	public String toString() {
		return "[row:" + row + ", column:" + column + ", width:" + width + ", height:" + height + "]";  
	}
}
