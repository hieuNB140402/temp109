

package com.document.allreader.allofficefilereader.fc.hpsf;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * <p>This exception is the superclass of all other unchecked
 * exceptions thrown in this package. It supports a nested "reason"
 * throwable, i.e. an exception that caused this one to be thrown.</p>
 *
 * @author Rainer Klute <a
 * href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 */
public class HPSFRuntimeException extends RuntimeException
{

    /** <p>The underlying reason for this exception - may be
     * <code>null</code>.</p> */
    private Throwable reason;



    /**
     * <p>Creates a new {@link HPSFRuntimeException}.</p>
     */
    public HPSFRuntimeException()
    {
        super();
    }



    /**
     * <p>Creates a new {@link HPSFRuntimeException} with a message
     * string.</p>
     *
     * @param msg The message string.
     */
    public HPSFRuntimeException(final String msg)
    {
        super(msg);
    }



    /**
     * <p>Creates a new {@link HPSFRuntimeException} with a
     * reason.</p>
     *
     * @param reason The reason, i.e. a throwable that indirectly
     * caused this exception.
     */
    public HPSFRuntimeException(final Throwable reason)
    {
        super();
        this.reason = reason;
    }



    /**
     * <p>Creates a new {@link HPSFRuntimeException} with a message
     * string and a reason.</p>
     *
     * @param msg The message string.
     * @param reason The reason, i.e. a throwable that indirectly
     * caused this exception.
     */
    public HPSFRuntimeException(final String msg, final Throwable reason)
    {
        super(msg);
        this.reason = reason;
    }



    /**
     * <p>Returns the {@link Throwable} that caused this exception to
     * be thrown or <code>null</code> if there was no such {@link
     * Throwable}.</p>
     *
     * @return The reason
     */
    public Throwable getReason()
    {
        return reason;
    }



    /**
     * @see Throwable#printStackTrace()
     */
    public void printStackTrace()
    {
        printStackTrace(System.err);
    }



    /**
     * @see Throwable#printStackTrace(java.io.PrintStream)
     */
    public void printStackTrace(final PrintStream p)
    {
        final Throwable reason = getReason();
        super.printStackTrace(p);
        if (reason != null)
        {
            p.println("Caused by:");
            reason.printStackTrace(p);
        }
    }



    /**
     * @see Throwable#printStackTrace(java.io.PrintWriter)
     */
    public void printStackTrace(final PrintWriter p)
    {
        final Throwable reason = getReason();
        super.printStackTrace(p);
        if (reason != null)
        {
            p.println("Caused by:");
            reason.printStackTrace(p);
        }
    }

}
