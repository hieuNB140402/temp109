package com.document.allreader.allofficefilereader.fc.p014ss.util;

import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.IRow;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.Sheet;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.Workbook;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.RegionUtil */

public final class RegionUtil {
    private RegionUtil() {
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.RegionUtil$CellPropertySetter */

    private static final class CellPropertySetter {
        private final String _propertyName;
        private final Short _propertyValue;
        private final Workbook _workbook;

        public CellPropertySetter(Workbook workbook, String str, int i) {
            this._workbook = workbook;
            this._propertyName = str;
            this._propertyValue = Short.valueOf((short) i);
        }

        public void setProperty(IRow iRow, int i) {
            CellUtil.setCellStyleProperty(CellUtil.getCell(iRow, i), this._workbook, this._propertyName, this._propertyValue);
        }
    }

    public static void setBorderLeft(int i, HSSFCellRangeAddress hSSFCellRangeAddress, Sheet sheet, Workbook workbook) {
        int lastRow = hSSFCellRangeAddress.getLastRow();
        int firstColumn = hSSFCellRangeAddress.getFirstColumn();
        CellPropertySetter cellPropertySetter = new CellPropertySetter(workbook, CellUtil.BORDER_LEFT, i);
        for (int firstRow = hSSFCellRangeAddress.getFirstRow(); firstRow <= lastRow; firstRow++) {
            cellPropertySetter.setProperty(CellUtil.getRow(firstRow, sheet), firstColumn);
        }
    }

    public static void setLeftBorderColor(int i, HSSFCellRangeAddress hSSFCellRangeAddress, Sheet sheet, Workbook workbook) {
        int lastRow = hSSFCellRangeAddress.getLastRow();
        int firstColumn = hSSFCellRangeAddress.getFirstColumn();
        CellPropertySetter cellPropertySetter = new CellPropertySetter(workbook, CellUtil.LEFT_BORDER_COLOR, i);
        for (int firstRow = hSSFCellRangeAddress.getFirstRow(); firstRow <= lastRow; firstRow++) {
            cellPropertySetter.setProperty(CellUtil.getRow(firstRow, sheet), firstColumn);
        }
    }

    public static void setBorderRight(int i, HSSFCellRangeAddress hSSFCellRangeAddress, Sheet sheet, Workbook workbook) {
        int lastRow = hSSFCellRangeAddress.getLastRow();
        int lastColumn = hSSFCellRangeAddress.getLastColumn();
        CellPropertySetter cellPropertySetter = new CellPropertySetter(workbook, CellUtil.BORDER_RIGHT, i);
        for (int firstRow = hSSFCellRangeAddress.getFirstRow(); firstRow <= lastRow; firstRow++) {
            cellPropertySetter.setProperty(CellUtil.getRow(firstRow, sheet), lastColumn);
        }
    }

    public static void setRightBorderColor(int i, HSSFCellRangeAddress hSSFCellRangeAddress, Sheet sheet, Workbook workbook) {
        int lastRow = hSSFCellRangeAddress.getLastRow();
        int lastColumn = hSSFCellRangeAddress.getLastColumn();
        CellPropertySetter cellPropertySetter = new CellPropertySetter(workbook, CellUtil.RIGHT_BORDER_COLOR, i);
        for (int firstRow = hSSFCellRangeAddress.getFirstRow(); firstRow <= lastRow; firstRow++) {
            cellPropertySetter.setProperty(CellUtil.getRow(firstRow, sheet), lastColumn);
        }
    }

    public static void setBorderBottom(int i, HSSFCellRangeAddress hSSFCellRangeAddress, Sheet sheet, Workbook workbook) {
        int lastColumn = hSSFCellRangeAddress.getLastColumn();
        int lastRow = hSSFCellRangeAddress.getLastRow();
        CellPropertySetter cellPropertySetter = new CellPropertySetter(workbook, CellUtil.BORDER_BOTTOM, i);
        IRow row = CellUtil.getRow(lastRow, sheet);
        for (int firstColumn = hSSFCellRangeAddress.getFirstColumn(); firstColumn <= lastColumn; firstColumn++) {
            cellPropertySetter.setProperty(row, firstColumn);
        }
    }

    public static void setBottomBorderColor(int i, HSSFCellRangeAddress hSSFCellRangeAddress, Sheet sheet, Workbook workbook) {
        int lastColumn = hSSFCellRangeAddress.getLastColumn();
        int lastRow = hSSFCellRangeAddress.getLastRow();
        CellPropertySetter cellPropertySetter = new CellPropertySetter(workbook, CellUtil.BOTTOM_BORDER_COLOR, i);
        IRow row = CellUtil.getRow(lastRow, sheet);
        for (int firstColumn = hSSFCellRangeAddress.getFirstColumn(); firstColumn <= lastColumn; firstColumn++) {
            cellPropertySetter.setProperty(row, firstColumn);
        }
    }

    public static void setBorderTop(int i, HSSFCellRangeAddress hSSFCellRangeAddress, Sheet sheet, Workbook workbook) {
        int lastColumn = hSSFCellRangeAddress.getLastColumn();
        int firstRow = hSSFCellRangeAddress.getFirstRow();
        CellPropertySetter cellPropertySetter = new CellPropertySetter(workbook, CellUtil.BORDER_TOP, i);
        IRow row = CellUtil.getRow(firstRow, sheet);
        for (int firstColumn = hSSFCellRangeAddress.getFirstColumn(); firstColumn <= lastColumn; firstColumn++) {
            cellPropertySetter.setProperty(row, firstColumn);
        }
    }

    public static void setTopBorderColor(int i, HSSFCellRangeAddress hSSFCellRangeAddress, Sheet sheet, Workbook workbook) {
        int lastColumn = hSSFCellRangeAddress.getLastColumn();
        int firstRow = hSSFCellRangeAddress.getFirstRow();
        CellPropertySetter cellPropertySetter = new CellPropertySetter(workbook, CellUtil.TOP_BORDER_COLOR, i);
        IRow row = CellUtil.getRow(firstRow, sheet);
        for (int firstColumn = hSSFCellRangeAddress.getFirstColumn(); firstColumn <= lastColumn; firstColumn++) {
            cellPropertySetter.setProperty(row, firstColumn);
        }
    }
}
