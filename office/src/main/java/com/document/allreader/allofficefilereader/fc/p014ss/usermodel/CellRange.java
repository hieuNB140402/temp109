package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

import java.util.Iterator;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.CellRange */

public interface CellRange<C extends ICell> extends Iterable<C> {
    C getCell(int i, int i2);

    C[][] getCells();

    C[] getFlattenedCells();

    int getHeight();

    String getReferenceText();

    C getTopLeftCell();

    int getWidth();

    @Override // java.lang.Iterable
    Iterator<C> iterator();

    int size();
}
