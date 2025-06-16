
package com.document.allreader.allofficefilereader.fc.ss.usermodel;

import com.document.allreader.allofficefilereader.fc.hssf.usermodel.IClientAnchor;

/**
 * Repersents a picture in a SpreadsheetML document
 *
 * @author Yegor Kozlov
 */
public interface Picture {

    /**
     * Reset the image to the original size.
     *
     * <p>
     * Please note, that this method works correctly only for workbooks
     * with default font size (Arial 10pt for .xls and Calibri 11pt for .xlsx).
     * If the default font is changed the resized image can be streched vertically or horizontally.
     * </p>
     */
    void resize();

    /**
     * Reset the image to the original size.
     *
     * <p>
     * Please note, that this method works correctly only for workbooks
     * with default font size (Arial 10pt for .xls and Calibri 11pt for .xlsx).
     * If the default font is changed the resize() procedure can be 'off'.
     * </p>
     *
     * @param scale the amount by which image dimensions are multiplied relative to the original size.
     * <code>resize(1.0)</code> sets the original size, <code>resize(0.5)</code> resize to 50% of the original,
     * <code>resize(2.0)</code> resizes to 200% of the original.
     */
    void resize(double scale);

    IClientAnchor getPreferredSize();
    
    /**
     * Return picture data for this picture
     *
     * @return picture data for this picture
     */
    PictureData getPictureData();

}
