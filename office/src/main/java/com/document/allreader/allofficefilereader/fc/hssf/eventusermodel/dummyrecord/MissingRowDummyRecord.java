

package com.document.allreader.allofficefilereader.fc.hssf.eventusermodel.dummyrecord;


public final class MissingRowDummyRecord extends DummyRecordBase {
	private int rowNumber;
	
	public MissingRowDummyRecord(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getRowNumber() {
		return rowNumber;
	}
}
