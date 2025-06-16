
package com.document.allreader.allofficefilereader.fc.usermodel;

/**
 * Represents a hyperlink.
 */
public interface Hyperlink {
    /**
     * Link to a existing file or web page
     */
    public static final int LINK_URL = 1;

    /**
     * Link to a place in this document
     */
    public static final int LINK_DOCUMENT = 2;

    /**
     * Link to an E-mail address
     */
    public static final int LINK_EMAIL = 3;

    /**
     * Link to a file
     */
    public static final int LINK_FILE = 4;

    
    /**
     * Hypelink address. Depending on the hyperlink type it can be URL, e-mail, patrh to a file, etc.
     *
     * @return  the address of this hyperlink
     */
    public String getAddress();

    /**
     * Hypelink address. Depending on the hyperlink type it can be URL, e-mail, patrh to a file, etc.
     *
     * @param address  the address of this hyperlink
     */
    public void setAddress(String address);

    /**
     * Return text label for this hyperlink
     *
     * @return  text to display
     */
    public String getLabel();

    /**
     * Sets text label for this hyperlink
     *
     * @param label text label for this hyperlink
     */
    public void setLabel(String label);

    /**
     * Return the type of this hyperlink
     *
     * @return the type of this hyperlink
     */
    public int getType();
}
