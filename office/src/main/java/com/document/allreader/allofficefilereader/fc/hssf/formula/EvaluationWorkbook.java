

package com.document.allreader.allofficefilereader.fc.hssf.formula;


import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.NamePtg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.NameXPtg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.Ptg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.udf.UDFFinder;


/**
 * Abstracts a workbook for the purpose of formula evaluation.<br/>
 *
 * For POI internal use only
 *
 * @author Josh Micich
 */
public interface EvaluationWorkbook {
	String getSheetName(int sheetIndex);
	/**
	 * @return -1 if the specified sheet is from a different book
	 */
	int getSheetIndex(EvaluationSheet sheet);
	/**
	 * Finds a sheet index by case insensitive name.
	 * @return the index of the sheet matching the specified name.  -1 if not found
	 */
	int getSheetIndex(String sheetName);

	EvaluationSheet getSheet(int sheetIndex);

	/**
	 * @return <code>null</code> if externSheetIndex refers to a sheet inside the current workbook
	 */
	ExternalSheet getExternalSheet(int externSheetIndex);
	int convertFromExternSheetIndex(int externSheetIndex);
	ExternalName getExternalName(int externSheetIndex, int externNameIndex);
	EvaluationName getName(NamePtg namePtg);
    EvaluationName getName(String name, int sheetIndex);
	String resolveNameXText(NameXPtg ptg);
	Ptg[] getFormulaTokens(EvaluationCell cell);
    UDFFinder getUDFFinder();

	class ExternalSheet {
		private final String _workbookName;
		private final String _sheetName;

		public ExternalSheet(String workbookName, String sheetName) {
			_workbookName = workbookName;
			_sheetName = sheetName;
		}
		public String getWorkbookName() {
			return _workbookName;
		}
		public String getSheetName() {
			return _sheetName;
		}
	}
	class ExternalName {
		private final String _nameName;
		private final int _nameNumber;
		private final int _ix;

		public ExternalName(String nameName, int nameNumber, int ix) {
			_nameName = nameName;
			_nameNumber = nameNumber;
			_ix = ix;
		}
		public String getName() {
			return _nameName;
		}
		public int getNumber() {
			return _nameNumber;
		}
		public int getIx() {
			return _ix;
		}
	}
}
