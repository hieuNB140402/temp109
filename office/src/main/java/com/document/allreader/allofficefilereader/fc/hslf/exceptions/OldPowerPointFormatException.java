

package com.document.allreader.allofficefilereader.fc.hslf.exceptions;

import com.document.allreader.allofficefilereader.fc.OldFileFormatException;

/**
 * This exception is thrown when we try to open a PowerPoint file, and
 *  it's too old for us.
 */
public class OldPowerPointFormatException extends OldFileFormatException
{
    public OldPowerPointFormatException(String s)
    {
        super(s);
    }
}
