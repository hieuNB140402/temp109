

package com.document.allreader.allofficefilereader.fc.ss.usermodel;

/**
 * Common definition of a HSSF or XSSF page header.
 * For a list of all the different fields that can be
 *  placed into a header, such as page number,
 *  bold, underline etc, see 
 *  {@link com.com.wxiwei.office.fc.ss.usermodel.HeaderFooter}.
 */
public interface Header extends HeaderFooter {
    /**
     * Get the left side of the header.
     *
     * @return The string representing the left side.
     */
    String getLeft();

    /**
     * Sets the left string.
     *
     * @param newLeft The string to set as the left side.
     */
    void setLeft(String newLeft);

    /**
     * Get the center of the header.
     *
     * @return The string representing the center.
     */
    String getCenter();

    /**
     * Sets the center string.
     *
     * @param newCenter The string to set as the center.
     */
    void setCenter(String newCenter);

    /**
     * Get the right side of the header.
     *
     * @return The string representing the right side.
     */
    String getRight();

    /**
     * Sets the right string.
     *
     * @param newRight The string to set as the right side.
     */
    void setRight(String newRight);
}
