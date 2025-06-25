
/*
 * Created on May 8, 2005
 *
 */

package com.document.allreader.allofficefilereader.fc.hssf.formula.eval;

import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.IntPtg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.NumberPtg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.Ptg;
import com.document.allreader.allofficefilereader.fc.ss.util.NumberToTextConverter;


/**
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 *  
 */
public final class NumberEval implements NumericValueEval, StringValueEval
{

    public static final NumberEval ZERO = new NumberEval(0);

    private final double _value;
    private String _stringValue;

    public NumberEval(Ptg ptg)
    {
        if (ptg == null)
        {
            throw new IllegalArgumentException("ptg must not be null");
        }
        if (ptg instanceof IntPtg)
        {
            _value = ((IntPtg)ptg).getValue();
        }
        else if (ptg instanceof NumberPtg)
        {
            _value = ((NumberPtg)ptg).getValue();
        }
        else
        {
            throw new IllegalArgumentException("bad argument type (" + ptg.getClass().getName()
                + ")");
        }
    }

    public NumberEval(double value)
    {
        _value = value;
    }

    public double getNumberValue()
    {
        return _value;
    }

    public String getStringValue()
    {
        if (_stringValue == null)
        {
            _stringValue = NumberToTextConverter.toText(_value);
        }
        return _stringValue;
    }

    public final String toString()
    {
        StringBuffer sb = new StringBuffer(64);
        sb.append(getClass().getName()).append(" [");
        sb.append(getStringValue());
        sb.append("]");
        return sb.toString();
    }
}
