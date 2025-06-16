package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaParseException;
import com.document.allreader.allofficefilereader.fc.p014ss.util.HSSFCellRangeAddress;
import java.util.Calendar;
import java.util.Date;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.ICell */

public interface ICell {
    public static final int CELL_TYPE_BLANK = 3;
    public static final int CELL_TYPE_BOOLEAN = 4;
    public static final int CELL_TYPE_ERROR = 5;
    public static final int CELL_TYPE_FORMULA = 2;
    public static final int CELL_TYPE_NUMERIC = 0;
    public static final int CELL_TYPE_STRING = 1;

    HSSFCellRangeAddress getArrayFormulaRange();

    boolean getBooleanCellValue();

    int getCachedFormulaResultType();

    Comment getCellComment();

    String getCellFormula();

    ICellStyle getCellStyle();

    int getCellType();

    int getColumnIndex();

    Date getDateCellValue();

    byte getErrorCellValue();

    IHyperlink getHyperlink();

    double getNumericCellValue();

    RichTextString getRichStringCellValue();

    IRow getRow();

    int getRowIndex();

    Sheet getSheet();

    String getStringCellValue();

    boolean isPartOfArrayFormulaGroup();

    void removeCellComment();

    void setAsActiveCell();

    void setCellComment(Comment comment);

    void setCellErrorValue(byte b);

    void setCellFormula(String str) throws FormulaParseException;

    void setCellStyle(ICellStyle iCellStyle);

    void setCellType(int i);

    void setCellValue(double d);

    void setCellValue(RichTextString richTextString);

    void setCellValue(String str);

    void setCellValue(Calendar calendar);

    void setCellValue(Date date);

    void setCellValue(boolean z);

    void setHyperlink(IHyperlink iHyperlink);
}
