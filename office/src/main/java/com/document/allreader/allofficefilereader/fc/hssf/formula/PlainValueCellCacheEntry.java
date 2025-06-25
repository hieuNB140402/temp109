

package com.document.allreader.allofficefilereader.fc.hssf.formula;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;

/**
 * Used for non-formula cells, primarily to keep track of the referencing (formula) cells.
 * 
 * @author Josh Micich
 */
final class PlainValueCellCacheEntry extends CellCacheEntry {
	public PlainValueCellCacheEntry(ValueEval value) {
		updateValue(value);
	}
}
