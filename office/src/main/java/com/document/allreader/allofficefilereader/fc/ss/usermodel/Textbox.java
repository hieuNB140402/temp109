

package com.document.allreader.allofficefilereader.fc.ss.usermodel;

public interface Textbox {

    public final static short OBJECT_TYPE_TEXT = 6;

    /**
     * @return  the rich text string for this textbox.
     */
    RichTextString getString();

    /**
     * @param string    Sets the rich text string used by this object.
     */
    void setString(RichTextString string);

    /**
     * @return  Returns the left margin within the textbox.
     */
    int getMarginLeft();

    /**
     * Sets the left margin within the textbox.
     */
    void setMarginLeft(int marginLeft);

    /**
     * @return    returns the right margin within the textbox.
     */
    int getMarginRight();

    /**
     * Sets the right margin within the textbox.
     */
    void setMarginRight(int marginRight);

    /**
     * @return  returns the top margin within the textbox.
     */
    int getMarginTop();

    /**
     * Sets the top margin within the textbox.
     */
    void setMarginTop(int marginTop);

    /**
     * Gets the bottom margin within the textbox.
     */
    int getMarginBottom();

    /**
     * Sets the bottom margin within the textbox.
     */
    void setMarginBottom(int marginBottom);

}
