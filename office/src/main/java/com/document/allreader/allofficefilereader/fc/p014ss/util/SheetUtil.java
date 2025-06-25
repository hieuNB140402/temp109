package com.document.allreader.allofficefilereader.fc.p014ss.util;

import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.CellValue;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.FormulaEvaluator;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.ICell;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.IFont;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.Sheet;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.SheetUtil */

public class SheetUtil {
    private static final char defaultChar = '0';
    private static final FormulaEvaluator dummyEvaluator = new FormulaEvaluator() { // from class: com.allreader.office.allofficefilereader.fc.ss.util.SheetUtil.1
        @Override // fc4ss.usermodel.FormulaEvaluator
        public void clearAllCachedResultValues() {
        }

        @Override // fc4ss.usermodel.FormulaEvaluator
        public CellValue evaluate(ICell iCell) {
            return null;
        }

        @Override // fc4ss.usermodel.FormulaEvaluator
        public void evaluateAll() {
        }

        @Override // fc4ss.usermodel.FormulaEvaluator
        public ICell evaluateInCell(ICell iCell) {
            return null;
        }

        @Override // fc4ss.usermodel.FormulaEvaluator
        public void notifyDeleteCell(ICell iCell) {
        }

        @Override // fc4ss.usermodel.FormulaEvaluator
        public void notifySetFormula(ICell iCell) {
        }

        @Override // fc4ss.usermodel.FormulaEvaluator
        public void notifyUpdateCell(ICell iCell) {
        }

        @Override // fc4ss.usermodel.FormulaEvaluator
        public int evaluateFormulaCell(ICell iCell) {
            return iCell.getCachedFormulaResultType();
        }
    };
    private static final double fontHeightMultiple = 2.0d;

    public static double getColumnWidth(Sheet sheet, int i, boolean z) {
        return 0.0d;
    }

    private static void copyAttributes(IFont iFont, AttributedString attributedString, int i, int i2) {
        attributedString.addAttribute(TextAttribute.FAMILY, iFont.getFontName(), i, i2);
        attributedString.addAttribute(TextAttribute.SIZE, Float.valueOf((float) iFont.getFontHeightInPoints()));
        if (iFont.getBoldweight() == 700) {
            attributedString.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, i, i2);
        }
        if (iFont.getItalic()) {
            attributedString.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, i, i2);
        }
        if (iFont.getUnderline() == 1) {
            attributedString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, i, i2);
        }
    }

    public static boolean containsCell(HSSFCellRangeAddress hSSFCellRangeAddress, int i, int i2) {
        return hSSFCellRangeAddress.getFirstRow() <= i && hSSFCellRangeAddress.getLastRow() >= i && hSSFCellRangeAddress.getFirstColumn() <= i2 && hSSFCellRangeAddress.getLastColumn() >= i2;
    }
}
