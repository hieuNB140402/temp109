

package com.document.allreader.allofficefilereader.fc.hpsf;

/**
 * <p>This exception is thrown when there is an illegal value set in a
 * {@link PropertySet}. For example, a {@link Variant#VT_BOOL} must
 * have a value of <code>-1 (true)</code> or <code>0 (false)</code>.
 * Any other value would trigger this exception. It supports a nested
 * "reason" throwable, i.e. an exception that caused this one to be
 * thrown.</p>
 *
 * @author Drew Varner(Drew.Varner atDomain sc.edu)
 */
public class IllegalPropertySetDataException extends HPSFRuntimeException
{

    /**
     * <p>Constructor</p>
     */
    public IllegalPropertySetDataException()
    {
        super();
    }



    /**
     * <p>Constructor</p>
     * 
     * @param msg The exception's message string
     */
    public IllegalPropertySetDataException(final String msg)
    {
        super(msg);
    }



    /**
     * <p>Constructor</p>
     * 
     * @param reason This exception's underlying reason
     */
    public IllegalPropertySetDataException(final Throwable reason)
    {
        super(reason);
    }



    /**
     * <p>Constructor</p>
     * 
     * @param msg The exception's message string
     * @param reason This exception's underlying reason
     */
    public IllegalPropertySetDataException(final String msg,
                                           final Throwable reason)
    {
        super(msg, reason);
    }

}
