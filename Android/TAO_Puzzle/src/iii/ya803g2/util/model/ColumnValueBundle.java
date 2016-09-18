package iii.ya803g2.util.model;

public class ColumnValueBundle {
	private ColumnValue[] columnValues;
	private boolean useAnd = true;

	public ColumnValueBundle(ColumnValue... columnValues) {
		super();
		setColumnValues(columnValues);
	}

	public ColumnValueBundle(boolean useAnd, ColumnValue... columnValues) {
		super();
		setColumnValues(columnValues);
		this.useAnd = useAnd;
	}

	private void setColumnValues(ColumnValue... columnValues) {
		if (columnValues == null || columnValues.length == 0) {
			throw new IllegalArgumentException(
					"Invalid columnValues: null or empty array");
		}
		this.columnValues = columnValues;
	}

	public ColumnValue[] getColumnValues() {
		return columnValues;
	}

	public boolean isUseAnd() {
		return useAnd;
	}

	@Override
	public String toString() {
		String out = "{[";
		for (ColumnValue cv : columnValues) {
			out += cv;
		}
		out += "], useAnd:" + useAnd + "}";
		return out;
	}
}
