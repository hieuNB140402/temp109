

package com.document.allreader.allofficefilereader.fc.hssf.formula.eval;

import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.Ptg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.StringPtg;

/**
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 */
public final class StringEval implements StringValueEval
{

    public static final StringEval EMPTY_INSTANCE = new StringEval("");

    private final String _value;

    public StringEval(Ptg ptg)
    {
        this(((StringPtg)ptg).getValue());
    }

    public StringEval(String value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("value must not be null");
        }
        _value = value;
    }

    public String getStringValue()
    {
        return _value;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName()).append(" [");
        sb.append(_value);
        sb.append("]");
        return sb.toString();
    }
}
