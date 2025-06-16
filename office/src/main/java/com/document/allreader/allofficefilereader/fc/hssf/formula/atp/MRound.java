

package com.document.allreader.allofficefilereader.fc.hssf.formula.atp;

import com.document.allreader.allofficefilereader.fc.hssf.formula.OperationEvaluationContext;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ErrorEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.EvaluationException;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.NumberEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.OperandResolver;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.function.FreeRefFunction;
import com.document.allreader.allofficefilereader.fc.hssf.formula.function.NumericFunction;


/**
 * Implementation of Excel 'Analysis ToolPak' function MROUND()<br/>
 *
 * Returns a number rounded to the desired multiple.<p/>
 *
 * <b>Syntax</b><br/>
 * <b>MROUND</b>(<b>number</b>, <b>multiple</b>)
 *
 * <p/>
 *
 * @author Yegor Kozlov
 */
final class MRound implements FreeRefFunction
{

    public static final FreeRefFunction instance = new MRound();

    private MRound()
    {
        // enforce singleton
    }

    public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext ec)
    {
        double number, multiple, result;

        if (args.length != 2)
        {
            return ErrorEval.VALUE_INVALID;
        }

        try
        {
            number = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(args[0],
                ec.getRowIndex(), ec.getColumnIndex()));
            multiple = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(args[1],
                ec.getRowIndex(), ec.getColumnIndex()));

            if (multiple == 0.0)
            {
                result = 0.0;
            }
            else
            {
                if (number * multiple < 0)
                {
                    // Returns #NUM! because the number and the multiple have different signs
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
                result = multiple * Math.round(number / multiple);
            }
            NumericFunction.checkValue(result);
            return new NumberEval(result);
        }
        catch(EvaluationException e)
        {
            return e.getErrorEval();
        }
    }
}
