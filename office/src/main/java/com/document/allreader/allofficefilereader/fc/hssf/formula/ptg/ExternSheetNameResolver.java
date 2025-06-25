

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;

import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaRenderingWorkbook;
import com.document.allreader.allofficefilereader.fc.hssf.formula.SheetNameFormatter;
import com.document.allreader.allofficefilereader.fc.hssf.formula.EvaluationWorkbook.ExternalSheet;

/**
 * @author Josh Micich
 */
final class ExternSheetNameResolver {

	private ExternSheetNameResolver() {
		// no instances of this class
	}

	public static String prependSheetName(FormulaRenderingWorkbook book, int field_1_index_extern_sheet, String cellRefText) {
		ExternalSheet externalSheet = book.getExternalSheet(field_1_index_extern_sheet);
		StringBuffer sb;
		if (externalSheet != null) {
			String wbName = externalSheet.getWorkbookName();
			String sheetName = externalSheet.getSheetName();
			sb = new StringBuffer(wbName.length() + sheetName.length() + cellRefText.length() + 4);
			SheetNameFormatter.appendFormat(sb, wbName, sheetName);
		} else {
			String sheetName = book.getSheetNameByExternSheet(field_1_index_extern_sheet);
			sb = new StringBuffer(sheetName.length() + cellRefText.length() + 4);
			if (sheetName.length() < 1) {
				// What excel does if sheet has been deleted
				sb.append("#REF"); // note - '!' added just once below
			} else {
				SheetNameFormatter.appendFormat(sb, sheetName);
			}
		}
   		sb.append('!');
		sb.append(cellRefText);
		return sb.toString();
	}
}
