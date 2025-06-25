

package com.document.allreader.allofficefilereader.fc.hslf.exceptions;

/**
 * This exception is thrown when we try to open a PowerPoint file, and
 *  something is fundamentally broken about it.
 *
 * @author Nick Burch
 */

public final class CorruptPowerPointFileException extends IllegalStateException
{
    public CorruptPowerPointFileException(String s)
    {
        super(s);
    }
}
