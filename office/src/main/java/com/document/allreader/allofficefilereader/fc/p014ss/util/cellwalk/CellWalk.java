package com.document.allreader.allofficefilereader.fc.p014ss.util.cellwalk;

import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.ICell;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.Sheet;
import com.document.allreader.allofficefilereader.fc.p014ss.util.DataMarker;
import com.document.allreader.allofficefilereader.fc.p014ss.util.HSSFCellRangeAddress;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.cellwalk.CellWalk */

public class CellWalk {
    private HSSFCellRangeAddress range;
    private Sheet sheet;
    private boolean traverseEmptyCells;

    public CellWalk(DataMarker dataMarker) {
        this(dataMarker.getSheet(), dataMarker.getRange());
    }

    public CellWalk(Sheet sheet, HSSFCellRangeAddress hSSFCellRangeAddress) {
        this.sheet = sheet;
        this.range = hSSFCellRangeAddress;
        this.traverseEmptyCells = false;
    }

    public boolean isTraverseEmptyCells() {
        return this.traverseEmptyCells;
    }

    public void setTraverseEmptyCells(boolean z) {
        this.traverseEmptyCells = z;
    }

    public void traverse(CellHandler cellHandler) {
        this.range.getFirstRow();
        this.range.getLastRow();
        this.range.getFirstColumn();
        this.range.getLastColumn();
        new SimpleCellWalkContext();
    }

    private boolean isEmpty(ICell iCell) {
        return iCell.getCellType() == 3;
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.cellwalk.CellWalk$SimpleCellWalkContext */

    private class SimpleCellWalkContext implements CellWalkContext {
        public int colNumber;
        public long ordinalNumber;
        public int rowNumber;

        private SimpleCellWalkContext() {
            this.ordinalNumber = 0;
            this.rowNumber = 0;
            this.colNumber = 0;
        }

        @Override // fc4ss.util.cellwalk.CellWalkContext
        public long getOrdinalNumber() {
            return this.ordinalNumber;
        }

        @Override // fc4ss.util.cellwalk.CellWalkContext
        public int getRowNumber() {
            return this.rowNumber;
        }

        @Override // fc4ss.util.cellwalk.CellWalkContext
        public int getColumnNumber() {
            return this.colNumber;
        }
    }
}
