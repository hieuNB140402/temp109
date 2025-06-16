

package com.document.allreader.allofficefilereader.fc.hssf.formula;

import java.util.HashSet;
import java.util.Set;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;


/**
 * Stores details about the current evaluation of a cell.<br/>
 */
final class CellEvaluationFrame {

	private final FormulaCellCacheEntry _cce;
	private final Set<CellCacheEntry> _sensitiveInputCells;
	private FormulaUsedBlankCellSet _usedBlankCellGroup;

	public CellEvaluationFrame(FormulaCellCacheEntry cce) {
		_cce = cce;
		_sensitiveInputCells = new HashSet<CellCacheEntry>();
	}
	public CellCacheEntry getCCE() {
		return _cce;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(64);
		sb.append(getClass().getName()).append(" [");
		sb.append("]");
		return sb.toString();
	}
	/**
	 * @param inputCell a cell directly used by the formula of this evaluation frame
	 */
	public void addSensitiveInputCell(CellCacheEntry inputCell) {
		_sensitiveInputCells.add(inputCell);
	}
	/**
	 * @return never <code>null</code>, (possibly empty) array of all cells directly used while
	 * evaluating the formula of this frame.
	 */
	private CellCacheEntry[] getSensitiveInputCells() {
		int nItems = _sensitiveInputCells.size();
		if (nItems < 1) {
			return CellCacheEntry.EMPTY_ARRAY;
		}
		CellCacheEntry[] result = new CellCacheEntry[nItems];
		_sensitiveInputCells.toArray(result);
		return result;
	}
	public void addUsedBlankCell(int bookIndex, int sheetIndex, int rowIndex, int columnIndex) {
		if (_usedBlankCellGroup == null) {
			_usedBlankCellGroup = new FormulaUsedBlankCellSet();
		}
		_usedBlankCellGroup.addCell(bookIndex, sheetIndex, rowIndex, columnIndex);
	}

	public void updateFormulaResult(ValueEval result) {
		_cce.updateFormulaResult(result, getSensitiveInputCells(), _usedBlankCellGroup);
	}
}
