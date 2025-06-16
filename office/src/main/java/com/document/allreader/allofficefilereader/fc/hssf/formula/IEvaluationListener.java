

package com.document.allreader.allofficefilereader.fc.hssf.formula;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;

/**
 * Tests can implement this class to track the internal working of the {@link WorkbookEvaluator}.<br/>
 *
 * For POI internal testing use only
 *
 * @author Josh Micich
 */
interface IEvaluationListener {
	/**
	 * A (mostly) opaque interface to allow test clients to trace cache values
	 * Each spreadsheet cell gets one unique cache entry instance.  These objects
	 * are safe to use as keys in {@link java.util.HashMap}s
	 */
	interface ICacheEntry {
		ValueEval getValue();
	}

	void onCacheHit(int sheetIndex, int rowIndex, int columnIndex, ValueEval result);
	void onReadPlainValue(int sheetIndex, int rowIndex, int columnIndex, ICacheEntry entry);
	void onStartEvaluate(EvaluationCell cell, ICacheEntry entry);
	void onEndEvaluate(ICacheEntry entry, ValueEval result);
	void onClearWholeCache();
	void onClearCachedValue(ICacheEntry entry);
	/**
	 * Internally, formula {@link ICacheEntry}s are stored in sets which may change ordering due
	 * to seemingly trivial changes.  This method is provided to make the order of call-backs to
	 * {@link #onClearDependentCachedValue(ICacheEntry, int)} more deterministic.
	 */
	void sortDependentCachedValues(ICacheEntry[] formulaCells);
	void onClearDependentCachedValue(ICacheEntry formulaCell, int depth);
	void onChangeFromBlankValue(int sheetIndex, int rowIndex, int columnIndex,
			EvaluationCell cell, ICacheEntry entry);
}
