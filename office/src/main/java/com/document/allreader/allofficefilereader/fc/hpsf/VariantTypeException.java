

package com.document.allreader.allofficefilereader.fc.hpsf;

/**
 * <p>This exception is thrown if HPSF encounters a problem with a variant type.
 * Concrete subclasses specifiy the problem further.</p>
 *
 * @author Rainer Klute <a
 * href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 */
public abstract class VariantTypeException extends HPSFException
{

    private Object value;

    private long variantType;



    /**
     * <p>Constructor.</p>
     *
     * @param variantType The variant type causing the problem
     * @param value The value who's variant type causes the problem
     * @param msg A message text describing the problem
     */
    public VariantTypeException(final long variantType, final Object value,
                                final String msg)
    {
        super(msg);
        this.variantType = variantType;
        this.value = value;
    }



    /**
     * <p>Returns the offending variant type.</p>
     *
     * @return the offending variant type.
     */
    public long getVariantType()
    {
        return variantType;
    }



    /**
     * <p>Returns the value who's variant type caused the problem.</p>
     *
     * @return the value who's variant type caused the problem
     */
    public Object getValue()
    {
        return value;
    }

}
