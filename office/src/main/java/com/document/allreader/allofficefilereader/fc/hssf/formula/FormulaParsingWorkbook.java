

package com.document.allreader.allofficefilereader.fc.hssf.formula;


import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.NameXPtg;
import com.document.allreader.allofficefilereader.fc.ss.SpreadsheetVersion;


/**
 * Abstracts a workbook for the purpose of formula parsing.<br/>
 *
 * For POI internal use only
 *
 * @author Josh Micich
 */
public interface FormulaParsingWorkbook {
	/**
	 *  named range name matching is case insensitive
	 */
	EvaluationName getName(String name, int sheetIndex);

	NameXPtg getNameXPtg(String name);

	/**
	 * gets the externSheet index for a sheet from this workbook
	 */
	int getExternalSheetIndex(String sheetName);
	/**
	 * gets the externSheet index for a sheet from an external workbook
	 * @param workbookName e.g. "Budget.xls"
	 * @param sheetName a name of a sheet in that workbook
	 */
	int getExternalSheetIndex(String workbookName, String sheetName);

	/**
	 * Returns an enum holding spreadhseet properties specific to an Excel version (
	 * max column and row numbers, max arguments to a function, etc.)
	 */
	SpreadsheetVersion getSpreadsheetVersion();

}
