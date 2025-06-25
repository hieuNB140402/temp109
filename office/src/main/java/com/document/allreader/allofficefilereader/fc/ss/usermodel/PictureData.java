

package com.document.allreader.allofficefilereader.fc.ss.usermodel;

public interface PictureData {

    /**
     * Gets the picture data.
     *
     * @return the picture data.
     */
    byte[] getData();

    /**
     * Suggests a file extension for this image.
     *
     * @return the file extension.
     */
    String suggestFileExtension();

    /**
     * Returns the mime type for the image
     */
    String getMimeType();
}
