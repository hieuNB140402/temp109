

package com.document.allreader.allofficefilereader.fc.hssf.formula.eval;


import com.document.allreader.allofficefilereader.fc.hssf.formula.EvaluationCell;
import com.document.allreader.allofficefilereader.fc.hssf.formula.EvaluationSheet;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.ICell;


/**
 * Represents a cell being used for forked evaluation that has had a value set different from the
 * corresponding cell in the shared master workbook.
 *
 * @author Josh Micich
 */
final class ForkedEvaluationCell implements EvaluationCell {

	private final EvaluationSheet _sheet;
	/** corresponding cell from master workbook */
	private final EvaluationCell _masterCell;
	private boolean _booleanValue;
	private int _cellType;
	private int _errorValue;
	private double _numberValue;
	private String _stringValue;

	public ForkedEvaluationCell(ForkedEvaluationSheet sheet, EvaluationCell masterCell) {
		_sheet = sheet;
		_masterCell = masterCell;
		// start with value blank, but expect construction to be immediately
		setValue(BlankEval.instance); // followed by a proper call to setValue()
	}

	public Object getIdentityKey() {
		return _masterCell.getIdentityKey();
	}

	public void setValue(ValueEval value) {
		Class<? extends ValueEval> cls = value.getClass();

		if (cls == NumberEval.class) {
			_cellType = ICell.CELL_TYPE_NUMERIC;
			_numberValue = ((NumberEval)value).getNumberValue();
			return;
		}
		if (cls == StringEval.class) {
			_cellType = ICell.CELL_TYPE_STRING;
			_stringValue = ((StringEval)value).getStringValue();
			return;
		}
		if (cls == BoolEval.class) {
			_cellType = ICell.CELL_TYPE_BOOLEAN;
			_booleanValue = ((BoolEval)value).getBooleanValue();
			return;
		}
		if (cls == ErrorEval.class) {
			_cellType = ICell.CELL_TYPE_ERROR;
			_errorValue = ((ErrorEval)value).getErrorCode();
			return;
		}
		if (cls == BlankEval.class) {
			_cellType = ICell.CELL_TYPE_BLANK;
			return;
		}
		throw new IllegalArgumentException("Unexpected value class (" + cls.getName() + ")");
	}
	public void copyValue(ICell destCell) {
		switch (_cellType) {
			case ICell.CELL_TYPE_BLANK:   destCell.setCellType(ICell.CELL_TYPE_BLANK);    return;
			case ICell.CELL_TYPE_NUMERIC: destCell.setCellValue(_numberValue);           return;
			case ICell.CELL_TYPE_BOOLEAN: destCell.setCellValue(_booleanValue);          return;
			case ICell.CELL_TYPE_STRING:  destCell.setCellValue(_stringValue);           return;
			case ICell.CELL_TYPE_ERROR:   destCell.setCellErrorValue((byte)_errorValue); return;
		}
		throw new IllegalStateException("Unexpected data type (" + _cellType + ")");
	}

	private void checkCellType(int expectedCellType) {
		if (_cellType != expectedCellType) {
			throw new RuntimeException("Wrong data type (" + _cellType + ")");
		}
	}
	public int getCellType() {
		return _cellType;
	}
	public boolean getBooleanCellValue() {
		checkCellType(ICell.CELL_TYPE_BOOLEAN);
		return _booleanValue;
	}
	public int getErrorCellValue() {
		checkCellType(ICell.CELL_TYPE_ERROR);
		return _errorValue;
	}
	public double getNumericCellValue() {
		checkCellType(ICell.CELL_TYPE_NUMERIC);
		return _numberValue;
	}
	public String getStringCellValue() {
		checkCellType(ICell.CELL_TYPE_STRING);
		return _stringValue;
	}
	public EvaluationSheet getSheet() {
		return _sheet;
	}
	public int getRowIndex() {
		return _masterCell.getRowIndex();
	}
	public int getColumnIndex() {
		return _masterCell.getColumnIndex();
	}
}
