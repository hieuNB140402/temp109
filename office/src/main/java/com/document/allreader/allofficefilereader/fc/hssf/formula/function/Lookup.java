

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;


import com.document.allreader.allofficefilereader.fc.hssf.formula.TwoDEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.EvaluationException;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.OperandResolver;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.function.LookupUtils.ValueVector;


/**
 * Implementation of Excel function LOOKUP.<p/>
 *
 * LOOKUP finds an index  row in a lookup table by the first column value and returns the value from another column.
 *
 * <b>Syntax</b>:<br/>
 * <b>VLOOKUP</b>(<b>lookup_value</b>, <b>lookup_vector</b>, result_vector)<p/>
 *
 * <b>lookup_value</b>  The value to be found in the lookup vector.<br/>
 * <b>lookup_vector</> An area reference for the lookup data. <br/>
 * <b>result_vector</b> Single row or single column area reference from which the result value is chosen.<br/>
 *
 * @author Josh Micich
 */
public final class Lookup extends Var2or3ArgFunction {

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0, ValueEval arg1) {
		// complex rules to choose lookupVector and resultVector from the single area ref
		throw new RuntimeException("Two arg version of LOOKUP not supported yet");
	}

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0, ValueEval arg1,
			ValueEval arg2) 
	{
		try
		{
			ValueEval lookupValue = OperandResolver.getSingleValue(arg0, srcRowIndex, srcColumnIndex);
			TwoDEval aeLookupVector = LookupUtils.resolveTableArrayArg(arg1);
			TwoDEval aeResultVector = LookupUtils.resolveTableArrayArg(arg2);

			ValueVector lookupVector = createVector(aeLookupVector);
			ValueVector resultVector = createVector(aeResultVector);
			if(lookupVector.getSize() > resultVector.getSize()) 
			{
				// Excel seems to handle this by accessing past the end of the result vector.
				throw new RuntimeException("Lookup vector and result vector of differing sizes not supported yet");
			}
			int index = LookupUtils.lookupIndexOfValue(lookupValue, lookupVector, true);			
			if(index >= 0)
			{
			    return resultVector.getItem(index);
			}
					
			return null;
			
		}
		catch (EvaluationException e) 
		{
			return e.getErrorEval();
		}
	}

	private static ValueVector createVector(TwoDEval ae) {
		ValueVector result = LookupUtils.createVector(ae);
		if (result != null) {
			return result;
		}
		// extra complexity required to emulate the way LOOKUP can handles these abnormal cases.
		throw new RuntimeException("non-vector lookup or result areas not supported yet");
	}
}
