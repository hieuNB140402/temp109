package com.document.allreader.allofficefilereader.fc.p014ss.util;

import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.ICell;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.Sheet;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.Workbook;
import java.util.Calendar;
import java.util.Date;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.SheetBuilder */

public class SheetBuilder {
    private Object[][] cells;
    private boolean shouldCreateEmptyCells = false;
    private Workbook workbook;

    public Sheet build() {
        return null;
    }

    public SheetBuilder(Workbook workbook, Object[][] objArr) {
        this.workbook = workbook;
        this.cells = objArr;
    }

    public boolean getCreateEmptyCells() {
        return this.shouldCreateEmptyCells;
    }

    public SheetBuilder setCreateEmptyCells(boolean z) {
        this.shouldCreateEmptyCells = z;
        return this;
    }

    public void setCellValue(ICell iCell, Object obj) {
        if (obj != null && iCell != null) {
            if (obj instanceof Number) {
                iCell.setCellValue(((Number) obj).doubleValue());
            } else if (obj instanceof Date) {
                iCell.setCellValue((Date) obj);
            } else if (obj instanceof Calendar) {
                iCell.setCellValue((Calendar) obj);
            } else if (isFormulaDefinition(obj)) {
                iCell.setCellFormula(getFormula(obj));
            } else {
                iCell.setCellValue(obj.toString());
            }
        }
    }

    private boolean isFormulaDefinition(Object obj) {
        if (!(obj instanceof String)) {
            return false;
        }
        String str = (String) obj;
        if (str.length() >= 2 && str.charAt(0) == '=') {
            return true;
        }
        return false;
    }

    private String getFormula(Object obj) {
        return ((String) obj).substring(1);
    }
}
