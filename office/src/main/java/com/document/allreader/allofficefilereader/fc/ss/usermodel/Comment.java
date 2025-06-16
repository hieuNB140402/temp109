

package com.document.allreader.allofficefilereader.fc.ss.usermodel;


public interface Comment {

    /**
     * Returns whether this comment is visible.
     *
     * @param visible <code>true</code> if the comment is visible, <code>false</code> otherwise
     */
    void setVisible(boolean visible);

    /**
     * Sets whether this comment is visible.
     *
     * @return <code>true</code> if the comment is visible, <code>false</code> otherwise
     */
    boolean isVisible();

    /**
     * Return the row of the cell that contains the comment
     *
     * @return the 0-based row of the cell that contains the comment
     */
    int getRow();

    /**
     * Set the row of the cell that contains the comment
     *
     * @param row the 0-based row of the cell that contains the comment
     */
    void setRow(int row);

    /**
     * Return the column of the cell that contains the comment
     *
     * @return the 0-based column of the cell that contains the comment
     */
    int getColumn();

    /**
     * Set the column of the cell that contains the comment
     *
     * @param col the 0-based column of the cell that contains the comment
     */
    void setColumn(int col);

    /**
     * Name of the original comment author
     *
     * @return the name of the original author of the comment
     */
    String getAuthor();

    /**
     * Name of the original comment author
     *
     * @param author the name of the original author of the comment
     */
    void setAuthor(String author);
    
    /**
     * Fetches the rich text string of the comment
     */
    public RichTextString getString();

    /**
     * Sets the rich text string used by this comment.
     *
     * @param string    Sets the rich text string used by this object.
     */
    void setString(RichTextString string);

}
