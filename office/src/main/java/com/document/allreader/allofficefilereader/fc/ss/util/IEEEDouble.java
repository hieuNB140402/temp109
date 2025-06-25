

package com.document.allreader.allofficefilereader.fc.ss.util;


/**
 * For working with the internals of IEEE 754-2008 'binary64' (double precision) floating point numbers
 *
 * @author Josh Micich
 */
final class IEEEDouble {
	private static final long EXPONENT_MASK = 0x7FF0000000000000L;
	private static final int  EXPONENT_SHIFT = 52;
	public static final long FRAC_MASK = 0x000FFFFFFFFFFFFFL;
	public static final int  EXPONENT_BIAS  = 1023;
	public static final long FRAC_ASSUMED_HIGH_BIT = ( 1L<<EXPONENT_SHIFT );
	/**
	 * The value the exponent field gets for all <i>NaN</i> and <i>Infinity</i> values
	 */
	public static final int BIASED_EXPONENT_SPECIAL_VALUE = 0x07FF;

	/**
	 * @param rawBits the 64 bit binary representation of the double value
	 * @return the top 12 bits (sign and biased exponent value)
	 */
	public static int getBiasedExponent(long rawBits) {
		return (int) ((rawBits & EXPONENT_MASK) >> EXPONENT_SHIFT);
	}
}
