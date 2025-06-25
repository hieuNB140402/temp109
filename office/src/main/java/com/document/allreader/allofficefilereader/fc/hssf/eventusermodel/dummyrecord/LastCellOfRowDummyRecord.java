

package com.document.allreader.allofficefilereader.fc.hssf.eventusermodel.dummyrecord;



public final class LastCellOfRowDummyRecord extends DummyRecordBase {
	private int row;
	private int lastColumnNumber;
	
	public LastCellOfRowDummyRecord(int row, int lastColumnNumber) {
		this.row = row;
		this.lastColumnNumber = lastColumnNumber;
	}
	

	public int getRow() { return row; }

	public int getLastColumnNumber() { return lastColumnNumber; }
}
