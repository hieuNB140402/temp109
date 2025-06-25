package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.AreaEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ErrorEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.RefEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.StringEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;

/* renamed from: com.allreader.office.allofficefilereader.fc.hssf.formula.function.T */

public final class C0918T extends Fixed1ArgFunction {
    @Override // fcf.formula.function.Function1Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        if (valueEval instanceof RefEval) {
            valueEval = ((RefEval) valueEval).getInnerValueEval();
        } else if (valueEval instanceof AreaEval) {
            valueEval = ((AreaEval) valueEval).getRelativeValue(0, 0);
        }
        if (!(valueEval instanceof StringEval) && !(valueEval instanceof ErrorEval)) {
            return StringEval.EMPTY_INSTANCE;
        }
        return valueEval;
    }
}
