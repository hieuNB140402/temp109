

package com.document.allreader.allofficefilereader.fc.hslf.exceptions;

/**
 * A generic exception that can be thrown by HSLF classes
 *
 * @author Yegor Kozlov
 */
public final class HSLFException extends RuntimeException
{

    public HSLFException()
    {
        super();
    }

    public HSLFException(String message)
    {
        super(message);
    }

    public HSLFException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public HSLFException(Throwable cause)
    {
        super(cause);
    }
}
