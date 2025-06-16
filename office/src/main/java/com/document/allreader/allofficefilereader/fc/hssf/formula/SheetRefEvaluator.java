

package com.document.allreader.allofficefilereader.fc.hssf.formula;


import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.FuncVarPtg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.Ptg;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.ICell;


/**
 *
 *
 * @author Josh Micich
 */
final class SheetRefEvaluator {

	private final WorkbookEvaluator _bookEvaluator;
	private final EvaluationTracker _tracker;
	private final int _sheetIndex;
	private EvaluationSheet _sheet;

	public SheetRefEvaluator(WorkbookEvaluator bookEvaluator, EvaluationTracker tracker, int sheetIndex) {
		if (sheetIndex < 0) {
			throw new IllegalArgumentException("Invalid sheetIndex: " + sheetIndex + ".");
		}
		_bookEvaluator = bookEvaluator;
		_tracker = tracker;
		_sheetIndex = sheetIndex;
	}

	public String getSheetName() {
		return _bookEvaluator.getSheetName(_sheetIndex);
	}

	public ValueEval getEvalForCell(int rowIndex, int columnIndex) {
		return _bookEvaluator.evaluateReference(getSheet(), _sheetIndex, rowIndex, columnIndex, _tracker);
	}

	private EvaluationSheet getSheet() {
		if (_sheet == null) {
			_sheet = _bookEvaluator.getSheet(_sheetIndex);
		}
		return _sheet;
	}

    /**
     * @return  whether cell at rowIndex and columnIndex is a subtotal
     * @see com.document.allreader.allofficefilereader.fc.hssf.formula.function.Subtotal
     */
    public boolean isSubTotal(int rowIndex, int columnIndex){
        boolean subtotal = false;
        EvaluationCell cell = getSheet().getCell(rowIndex, columnIndex);
        if(cell != null && cell.getCellType() == ICell.CELL_TYPE_FORMULA){
            EvaluationWorkbook wb = _bookEvaluator.getWorkbook();
            for(Ptg ptg : wb.getFormulaTokens(cell)){
                if(ptg instanceof FuncVarPtg){
                    FuncVarPtg f = (FuncVarPtg)ptg;
                    if("SUBTOTAL".equals(f.getName())) {
                        subtotal = true;
                        break;
                    }
                }
            }
        }
        return subtotal;
    }

}
