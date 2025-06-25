

package com.document.allreader.allofficefilereader.fc.hslf.exceptions;

/**
 * This exception is thrown when we try to create a record, and the
 *  underlying data just doesn't match up
 *
 * @author Nick Burch
 */

public final class InvalidRecordFormatException extends Exception
{
    public InvalidRecordFormatException(String s)
    {
        super(s);
    }
}
