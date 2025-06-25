

package com.document.allreader.allofficefilereader.fc.hssf.formula;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaUsedBlankCellSet.BookSheetKey;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;




/**
 * Stores the cached result of a formula evaluation, along with the set of sensitive input cells
 * 
 * @author Josh Micich
 */
final class FormulaCellCacheEntry extends CellCacheEntry {
	
	/**
	 * Cells 'used' in the current evaluation of the formula corresponding to this cache entry
	 *
	 * If any of the following cells change, this cache entry needs to be cleared
	 */
	private CellCacheEntry[] _sensitiveInputCells;

	private FormulaUsedBlankCellSet _usedBlankCellGroup;

	public FormulaCellCacheEntry() {
		// leave fields un-set
	}
	
	public boolean isInputSensitive() {
		if (_sensitiveInputCells != null) {
			if (_sensitiveInputCells.length > 0 ) {
				return true;
			}
		}
		return _usedBlankCellGroup == null ? false : !_usedBlankCellGroup.isEmpty();
	}

	public void setSensitiveInputCells(CellCacheEntry[] sensitiveInputCells) {
		// need to tell all cells that were previously used, but no longer are, 
		// that they are not consumed by this cell any more
		changeConsumingCells(sensitiveInputCells == null ? CellCacheEntry.EMPTY_ARRAY : sensitiveInputCells);
		_sensitiveInputCells = sensitiveInputCells;
	}

	public void clearFormulaEntry() {
		CellCacheEntry[] usedCells = _sensitiveInputCells;
		if (usedCells != null) {
			for (int i = usedCells.length-1; i>=0; i--) {
				usedCells[i].clearConsumingCell(this);
			}
		}
		_sensitiveInputCells = null;
		clearValue();
	}
	
	private void changeConsumingCells(CellCacheEntry[] usedCells) {

		CellCacheEntry[] prevUsedCells = _sensitiveInputCells;
		int nUsed = usedCells.length;
		for (int i = 0; i < nUsed; i++) {
			usedCells[i].addConsumingCell(this);
		}
		if (prevUsedCells == null) {
			return;
		}
		int nPrevUsed = prevUsedCells.length;
		if (nPrevUsed < 1) {
			return;
		}
		Set<CellCacheEntry> usedSet;
		if (nUsed < 1) {
			usedSet = Collections.emptySet();
		} else {
			usedSet = new HashSet<CellCacheEntry>(nUsed * 3 / 2);
			for (int i = 0; i < nUsed; i++) {
				usedSet.add(usedCells[i]);
			}
		}
		for (int i = 0; i < nPrevUsed; i++) {
			CellCacheEntry prevUsed = prevUsedCells[i];
			if (!usedSet.contains(prevUsed)) {
				// previously was used by cellLoc, but not anymore
				prevUsed.clearConsumingCell(this);
			}
		}
	}

	public void updateFormulaResult(ValueEval result, CellCacheEntry[] sensitiveInputCells, FormulaUsedBlankCellSet usedBlankAreas) {
		updateValue(result);
		setSensitiveInputCells(sensitiveInputCells);
		_usedBlankCellGroup = usedBlankAreas;
	}

	public void notifyUpdatedBlankCell(BookSheetKey bsk, int rowIndex, int columnIndex, IEvaluationListener evaluationListener) {
		if (_usedBlankCellGroup != null) {
			if (_usedBlankCellGroup.containsCell(bsk, rowIndex, columnIndex)) {
				clearFormulaEntry();
				recurseClearCachedFormulaResults(evaluationListener);
			}
		}
	}
}
