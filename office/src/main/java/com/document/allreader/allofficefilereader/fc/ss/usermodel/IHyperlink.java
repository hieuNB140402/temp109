
package com.document.allreader.allofficefilereader.fc.ss.usermodel;

/**
 * Represents an Excel hyperlink.
 */
public interface IHyperlink extends com.document.allreader.allofficefilereader.fc.usermodel.Hyperlink {
    /**
     * Return the row of the first cell that contains the hyperlink
     *
     * @return the 0-based row of the cell that contains the hyperlink
     */
    public int getFirstRow();

    /**
     * Set the row of the first cell that contains the hyperlink
     *
     * @param row the 0-based row of the first cell that contains the hyperlink
     */
    public void setFirstRow(int row);

    /**
     * Return the row of the last cell that contains the hyperlink
     *
     * @return the 0-based row of the last cell that contains the hyperlink
     */
    public int getLastRow();

    /**
     * Set the row of the last cell that contains the hyperlink
     *
     * @param row the 0-based row of the last cell that contains the hyperlink
     */
    public void setLastRow(int row);

    /**
     * Return the column of the first cell that contains the hyperlink
     *
     * @return the 0-based column of the first cell that contains the hyperlink
     */
    public int getFirstColumn();

    /**
     * Set the column of the first cell that contains the hyperlink
     *
     * @param col the 0-based column of the first cell that contains the hyperlink
     */
    public void setFirstColumn(int col);

    /**
     * Return the column of the last cell that contains the hyperlink
     *
     * @return the 0-based column of the last cell that contains the hyperlink
     */
    public int getLastColumn();

    /**
     * Set the column of the last cell that contains the hyperlink
     *
     * @param col the 0-based column of the last cell that contains the hyperlink
     */
    public void setLastColumn(int col);
}
