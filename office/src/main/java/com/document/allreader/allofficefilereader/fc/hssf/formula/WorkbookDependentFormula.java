

package com.document.allreader.allofficefilereader.fc.hssf.formula;

/**
 * Should be implemented by any {@link com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.Ptg} subclass that needs a workbook to render its formula.
 * <br/>
 *
 * For POI internal use only
 *
 * @author Josh Micich
 */
public interface WorkbookDependentFormula {
	String toFormulaString(FormulaRenderingWorkbook book);
}
