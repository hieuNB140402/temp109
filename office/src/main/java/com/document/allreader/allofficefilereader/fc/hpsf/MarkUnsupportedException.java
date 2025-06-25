

package com.document.allreader.allofficefilereader.fc.hpsf;

/**
 * <p>This exception is thrown if an {@link java.io.InputStream} does
 * not support the {@link java.io.InputStream#mark} operation.</p>
 *
 * @author Rainer Klute <a
 * href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 */
public class MarkUnsupportedException extends HPSFException
{

    /**
     * <p>Constructor</p>
     */
    public MarkUnsupportedException()
    {
        super();
    }


    /**
     * <p>Constructor</p>
     *
     * @param msg The exception's message string
     */
    public MarkUnsupportedException(final String msg)
    {
        super(msg);
    }


    /**
     * <p>Constructor</p>
     *
     * @param reason This exception's underlying reason
     */
    public MarkUnsupportedException(final Throwable reason)
    {
        super(reason);
    }


   /**
    * <p>Constructor</p>
    *
    * @param msg The exception's message string
    * @param reason This exception's underlying reason
    */
    public MarkUnsupportedException(final String msg, final Throwable reason)
    {
        super(msg, reason);
    }

}
