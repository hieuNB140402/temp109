

package com.document.allreader.allofficefilereader.fc.hssf.eventusermodel.dummyrecord;



public final class MissingCellDummyRecord extends DummyRecordBase {
	private int row;
	private int column;
	
	public MissingCellDummyRecord(int row, int column) {
		this.row = row;
		this.column = column;
	}
	public int getRow() { return row; }
	public int getColumn() { return column; }
}
