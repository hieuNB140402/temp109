package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

import java.util.Iterator;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.IRow */

public interface IRow extends Iterable<ICell> {
    public static final MissingCellPolicy CREATE_NULL_AS_BLANK = new MissingCellPolicy();
    public static final MissingCellPolicy RETURN_BLANK_AS_NULL = new MissingCellPolicy();
    public static final MissingCellPolicy RETURN_NULL_AND_BLANK = new MissingCellPolicy();

    Iterator<ICell> cellIterator();

    ICell createCell(int i);

    ICell createCell(int i, int i2);

    ICell getCell(int i);

    ICell getCell(int i, MissingCellPolicy missingCellPolicy);

    short getFirstCellNum();

    short getHeight();

    float getHeightInPoints();

    short getLastCellNum();

    int getPhysicalNumberOfCells();

    int getRowNum();

    ICellStyle getRowStyle();

    Sheet getSheet();

    boolean getZeroHeight();

    boolean isFormatted();

    void removeCell(ICell iCell);

    void setHeight(short s);

    void setHeightInPoints(float f);

    void setRowNum(int i);

    void setRowStyle(ICellStyle iCellStyle);

    void setZeroHeight(boolean z);

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.IRow$MissingCellPolicy */

    public static final class MissingCellPolicy {
        private static int NEXT_ID = 1;

        /* renamed from: id */
        public final int f179id;

        private MissingCellPolicy() {
            int i = NEXT_ID;
            NEXT_ID = i + 1;
            this.f179id = i;
        }
    }
}
