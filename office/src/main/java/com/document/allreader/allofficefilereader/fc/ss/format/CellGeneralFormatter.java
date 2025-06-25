
package com.document.allreader.allofficefilereader.fc.ss.format;

import java.util.Formatter;

/**
 * A formatter for the default "General" cell format.
 *
 * @author Ken Arnold, Industrious Media LLC
 */
public class CellGeneralFormatter extends CellFormatter {
    /** Creates a new general formatter. */
    public CellGeneralFormatter() {
        super("General");
    }

    /**
     * The general style is not quite the same as any other, or any combination
     * of others.
     *
     * @param toAppendTo The buffer to append to.
     * @param value      The value to format.
     */
    public void formatValue(StringBuffer toAppendTo, Object value) {
        if (value instanceof Number) {
            double val = ((Number) value).doubleValue();
            if (val == 0) {
                toAppendTo.append('0');
                return;
            }

            String fmt;
            double exp = Math.log10(Math.abs(val));
            boolean stripZeros = true;
            if (exp > 10 || exp < -9)
                fmt = "%1.5E";
            else if ((long) val != val)
                fmt = "%1.9f";
            else {
                fmt = "%1.0f";
                stripZeros = false;
            }

            Formatter formatter = new Formatter(toAppendTo);
            formatter.format(LOCALE, fmt, value);
            if (stripZeros) {
                // strip off trailing zeros
                int removeFrom;
                if (fmt.endsWith("E"))
                    removeFrom = toAppendTo.lastIndexOf("E") - 1;
                else
                    removeFrom = toAppendTo.length() - 1;
                while (toAppendTo.charAt(removeFrom) == '0') {
                    toAppendTo.deleteCharAt(removeFrom--);
                }
                if (toAppendTo.charAt(removeFrom) == '.') {
                    toAppendTo.deleteCharAt(removeFrom--);
                }
            }
        } else {
            toAppendTo.append(value.toString());
        }
    }

    /** Equivalent to {@link #formatValue(StringBuffer,Object)}. {@inheritDoc}. */
    public void simpleValue(StringBuffer toAppendTo, Object value) {
        formatValue(toAppendTo, value);
    }
}
