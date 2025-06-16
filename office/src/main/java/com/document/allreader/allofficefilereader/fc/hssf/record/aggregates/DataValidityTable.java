

package com.document.allreader.allofficefilereader.fc.hssf.record.aggregates;

import java.util.ArrayList;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.hssf.model.RecordStream;
import com.document.allreader.allofficefilereader.fc.hssf.record.DVALRecord;
import com.document.allreader.allofficefilereader.fc.hssf.record.DVRecord;
import com.document.allreader.allofficefilereader.fc.hssf.record.Record;



/**
 * Manages the DVALRecord and DVRecords for a single sheet<br/>
 * See OOO excelfileformat.pdf section 4.14
 * @author Josh Micich
 */
public final class DataValidityTable extends RecordAggregate {

	private final DVALRecord _headerRec;
	/**
	 * The list of data validations for the current sheet.
	 * Note - this may be empty (contrary to OOO documentation)
	 */
	private final List _validationList;

	public DataValidityTable(RecordStream rs) {
		_headerRec = (DVALRecord) rs.getNext();
		List temp = new ArrayList();
		while (rs.peekNextClass() == DVRecord.class) {
			temp.add(rs.getNext());
		}
		_validationList = temp;
	}

	public DataValidityTable() {
		_headerRec = new DVALRecord();
		_validationList = new ArrayList();
	}

	public void visitContainedRecords(RecordVisitor rv) {
		if (_validationList.isEmpty()) {
			return;
		}
		rv.visitRecord(_headerRec);
		for (int i = 0; i < _validationList.size(); i++) {
			rv.visitRecord((Record) _validationList.get(i));
		}
	}

	public void addDataValidation(DVRecord dvRecord) {
		_validationList.add(dvRecord);
		_headerRec.setDVRecNo(_validationList.size());
	}
}
