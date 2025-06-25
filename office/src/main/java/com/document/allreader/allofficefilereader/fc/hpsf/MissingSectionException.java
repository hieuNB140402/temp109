

package com.document.allreader.allofficefilereader.fc.hpsf;

/**
 * <p>This exception is thrown if one of the {@link PropertySet}'s
 * convenience methods does not find a required {@link Section}.</p>
 *
 * <p>The constructors of this class are analogous to those of its
 * superclass and documented there.</p>
 *
 * @author Rainer Klute <a
 * href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 */
public class MissingSectionException extends HPSFRuntimeException
{

    /**
     * <p>Constructor</p>
     */
    public MissingSectionException()
    {
        super();
    }


    /**
     * <p>Constructor</p>
     * 
     * @param msg The exception's message string
     */
    public MissingSectionException(final String msg)
    {
        super(msg);
    }


    /**
     * <p>Constructor</p>
     * 
     * @param reason This exception's underlying reason
     */
    public MissingSectionException(final Throwable reason)
    {
        super(reason);
    }


    /**
     * <p>Constructor</p>
     * 
     * @param msg The exception's message string
     * @param reason This exception's underlying reason
     */
    public MissingSectionException(final String msg, final Throwable reason)
    {
        super(msg, reason);
    }

}
