

package com.document.allreader.allofficefilereader.fc.hpsf;

/**
 * <p>This exception is thrown when trying to write a (yet) unsupported variant
 * type.</p>
 * 
 * @see ReadingNotSupportedException
 * @see UnsupportedVariantTypeException
 *
 * @author Rainer Klute <a
 * href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 */
public class WritingNotSupportedException
    extends UnsupportedVariantTypeException
{

    /**
     * <p>Constructor</p>
     * 
     * @param variantType The unsupported variant type.
     * @param value The value.
     */
    public WritingNotSupportedException(final long variantType,
                                        final Object value)
    {
        super(variantType, value);
    }

}
