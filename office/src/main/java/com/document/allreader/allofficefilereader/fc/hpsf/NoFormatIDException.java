

package com.document.allreader.allofficefilereader.fc.hpsf;

/**
 * <p>This exception is thrown if a {@link MutablePropertySet} is to be written
 * but does not have a formatID set (see {@link
 * MutableSection#setFormatID(ClassID)} or
 * {@link com.document.allreader.allofficefilereader.fc.hpsf.MutableSection#setFormatID(byte[])}.
 *
 * @author Rainer Klute <a
 * href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 */
public class NoFormatIDException extends HPSFRuntimeException
{

    /**
     * <p>Constructor</p>
     */
    public NoFormatIDException()
    {
        super();
    }


    /**
     * <p>Constructor</p>
     * 
     * @param msg The exception's message string
     */
    public NoFormatIDException(final String msg)
    {
        super(msg);
    }


    /**
     * <p>Constructor</p>
     * 
     * @param reason This exception's underlying reason
     */
    public NoFormatIDException(final Throwable reason)
    {
        super(reason);
    }


    /**
     * <p>Constructor</p>
     * 
     * @param msg The exception's message string
     * @param reason This exception's underlying reason
     */
    public NoFormatIDException(final String msg, final Throwable reason)
    {
        super(msg, reason);
    }

}
