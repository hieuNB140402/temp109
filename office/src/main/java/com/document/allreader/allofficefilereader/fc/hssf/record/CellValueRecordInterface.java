

package com.document.allreader.allofficefilereader.fc.hssf.record;


/**
 * The cell value record interface is implemented by all classes of type Record that
 * contain cell values.  It allows the containing sheet to move through them and compare
 * them.
 *
 * @author Andrew C. Oliver (acoliver at apache dot org)
 * @author Jason Height (jheight at chariot dot net dot au)
 *
 * @see com.document.allreader.allofficefilereader.fc.hssf.record.Record
 * @see com.document.allreader.allofficefilereader.fc.hssf.record.RecordFactory
 */
public interface CellValueRecordInterface {

    /**
     * @return the row this cell occurs on
     */
    int getRow();

    /**
     * @return the column this cell defines within the row
     */
    short getColumn();

    /**
     * @param row the row this cell occurs within
     */
    void setRow(int row);

    /**
     * @param col the column this cell defines
     */
    void setColumn(short col);

    void setXFIndex(short xf);

    short getXFIndex();
}
