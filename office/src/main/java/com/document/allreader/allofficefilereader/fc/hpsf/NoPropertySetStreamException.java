

package com.document.allreader.allofficefilereader.fc.hpsf;

/**
 * <p>This exception is thrown if a format error in a property set stream is
 * detected or when the input data do not constitute a property set stream.</p>
 * 
 * <p>The constructors of this class are analogous to those of its superclass
 * and are documented there.</p>
 *
 * @author Rainer Klute <a
 * href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 */
public class NoPropertySetStreamException extends HPSFException
{

    /**
     * <p>Constructor</p>
     */
    public NoPropertySetStreamException()
    {
        super();
    }



    /**
     * <p>Constructor</p>
     * 
     * @param msg The exception's message string
     */
    public NoPropertySetStreamException(final String msg)
    {
        super(msg);
    }



    /**
     * <p>Constructor</p>
     * 
     * @param reason This exception's underlying reason
     */
    public NoPropertySetStreamException(final Throwable reason)
    {
        super(reason);
    }



    /**
     * <p>Constructor</p>
     * 
     * @param msg The exception's message string
     * @param reason This exception's underlying reason
     */
    public NoPropertySetStreamException(final String msg,
                                        final Throwable reason)
    {
        super(msg, reason);
    }

}
