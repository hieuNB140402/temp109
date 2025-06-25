

package com.document.allreader.allofficefilereader.fc.hssf.model;


import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaParseException;
import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaParser;
import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaParsingWorkbook;
import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaRenderer;
import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaType;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.Ptg;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFEvaluationWorkbook;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFWorkbook;
import com.document.allreader.allofficefilereader.ss.model.XLSModel.AWorkbook;


/**
 * HSSF wrapper for the {@link FormulaParser} and {@link FormulaRenderer}
 *
 * @author Josh Micich
 */
public final class HSSFFormulaParser {

	private static FormulaParsingWorkbook createParsingWorkbook(AWorkbook book) {
		return HSSFEvaluationWorkbook.create(book);
	}

	private HSSFFormulaParser() {
		// no instances of this class
	}

	/**
	 * Convenience method for parsing cell formulas. see {@link #parse(String, HSSFWorkbook, int, int)}
	 */
	public static Ptg[] parse(String formula, AWorkbook workbook) throws FormulaParseException {
		return parse(formula, workbook, FormulaType.CELL);
	}

	/**
	 * @param formulaType a constant from {@link FormulaType}
	 * @return the parsed formula tokens
     * @throws FormulaParseException if the formula has incorrect syntax or is otherwise invalid
	 */
	public static Ptg[] parse(String formula, AWorkbook workbook, int formulaType) throws FormulaParseException {
		return parse(formula, workbook, formulaType, -1);
	}

	/**
	 * @param formula     the formula to parse
	 * @param workbook    the parent workbook
	 * @param formulaType a constant from {@link FormulaType}
	 * @param sheetIndex  the 0-based index of the sheet this formula belongs to.
	 * The sheet index is required to resolve sheet-level names. <code>-1</code> means that
	 * the scope of the name will be ignored and  the parser will match named ranges only by name
	 *
	 * @return the parsed formula tokens
     * @throws FormulaParseException if the formula has incorrect syntax or is otherwise invalid
	 */
	public static Ptg[] parse(String formula, AWorkbook workbook, int formulaType, int sheetIndex) throws FormulaParseException {
		return FormulaParser.parse(formula, createParsingWorkbook(workbook), formulaType, sheetIndex);
	}

	/**
	 * Static method to convert an array of {@link Ptg}s in RPN order
	 * to a human readable string format in infix mode.
	 * @param book  used for defined names and 3D references
	 * @param ptgs  must not be <code>null</code>
	 * @return a human readable String
	 */
	public static String toFormulaString(AWorkbook book, Ptg[] ptgs) {
		return FormulaRenderer.toFormulaString(HSSFEvaluationWorkbook.create(book), ptgs);
	}
}
