

package com.document.allreader.allofficefilereader.fc.hslf.exceptions;

import com.document.allreader.allofficefilereader.fc.EncryptedDocumentException;

/**
 * This exception is thrown when we try to open a PowerPoint file, and
 *  discover that it is encrypted
 */
public final class EncryptedPowerPointFileException extends EncryptedDocumentException
{
    public EncryptedPowerPointFileException(String s)
    {
        super(s);
    }
}
