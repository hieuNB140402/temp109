

package com.document.allreader.allofficefilereader.fc.hssf.formula;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Josh Micich
 */
final class FormulaCellCache {

	static interface IEntryOperation {
		void processEntry(FormulaCellCacheEntry entry);
	}

	private final Map<Object, FormulaCellCacheEntry> _formulaEntriesByCell;

	public FormulaCellCache() {
		// assumes the object returned by EvaluationCell.getIdentityKey() has a well behaved hashCode+equals
		_formulaEntriesByCell = new HashMap<Object, FormulaCellCacheEntry>();
	}

	public CellCacheEntry[] getCacheEntries() {

		FormulaCellCacheEntry[] result = new FormulaCellCacheEntry[_formulaEntriesByCell.size()];
		_formulaEntriesByCell.values().toArray(result);
		return result;
	}

	public void clear() {
		_formulaEntriesByCell.clear();
	}

	/**
	 * @return <code>null</code> if not found
	 */
	public FormulaCellCacheEntry get(EvaluationCell cell) {
		return _formulaEntriesByCell.get(cell.getIdentityKey());
	}

	public void put(EvaluationCell cell, FormulaCellCacheEntry entry) {
		_formulaEntriesByCell.put(cell.getIdentityKey(), entry);
	}

	public FormulaCellCacheEntry remove(EvaluationCell cell) {
		return _formulaEntriesByCell.remove(cell.getIdentityKey());
	}

	public void applyOperation(IEntryOperation operation) {
		Iterator<FormulaCellCacheEntry> i = _formulaEntriesByCell.values().iterator();
		while (i.hasNext()) {
			operation.processEntry(i.next());
		}
	}
}
