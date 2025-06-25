

package com.document.allreader.allofficefilereader.fc.hssf.formula;


import com.document.allreader.allofficefilereader.fc.hssf.formula.EvaluationWorkbook.ExternalSheet;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.NamePtg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.NameXPtg;


/**
 * Abstracts a workbook for the purpose of converting formula to text.<br/>
 *
 * For POI internal use only
 *
 * @author Josh Micich
 */
public interface FormulaRenderingWorkbook {

	/**
	 * @return <code>null</code> if externSheetIndex refers to a sheet inside the current workbook
	 */
	ExternalSheet getExternalSheet(int externSheetIndex);
	String getSheetNameByExternSheet(int externSheetIndex);
	String resolveNameXText(NameXPtg nameXPtg);
	String getNameText(NamePtg namePtg);
}
