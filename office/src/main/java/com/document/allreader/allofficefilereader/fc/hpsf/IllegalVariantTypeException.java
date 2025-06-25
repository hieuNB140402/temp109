

package com.document.allreader.allofficefilereader.fc.hpsf;

import com.document.allreader.allofficefilereader.fc.util.HexDump;

/**
 * <p>This exception is thrown if HPSF encounters a variant type that is illegal
 * in the current context.</p>
 * 
 * @author Rainer Klute <a
 * href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 */
public class IllegalVariantTypeException extends VariantTypeException
{

    /**
     * <p>Constructor</p>
     * 
     * @param variantType The unsupported variant type
     * @param value The value
     * @param msg A message string
     */
    public IllegalVariantTypeException(final long variantType,
                                       final Object value, final String msg)
    {
        super(variantType, value, msg);
    }

    /**
     * <p>Constructor</p>
     * 
     * @param variantType The unsupported variant type
     * @param value The value
     */
    public IllegalVariantTypeException(final long variantType,
                                       final Object value)
    {
        this(variantType, value, "The variant type " + variantType + " (" +
             Variant.getVariantName(variantType) + ", " + 
             HexDump.toHex(variantType) + ") is illegal in this context.");
    }

}
