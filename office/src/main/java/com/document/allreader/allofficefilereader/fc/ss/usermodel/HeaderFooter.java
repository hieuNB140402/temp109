
package com.document.allreader.allofficefilereader.fc.ss.usermodel;

/**
 * Common interface for {@link com.com.wxiwei.office.fc.ss.usermodel.Header} and
 *  {@link com.document.allreader.allofficefilereader.fc.ss.usermodel.com.wxiwei.office.fc.ss.usermodel.Footer}.
 */
public interface HeaderFooter {
    /**
     * Get the left side of the header or footer.
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
     * Get the center of the header or footer.
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
     * Get the right side of the header or footer.
     *
     * @return The string representing the right side.
     */
    String getRight();

    /**
     * Sets the right string or footer.
     *
     * @param newRight The string to set as the right side.
     */
    void setRight(String newRight);
}
