

package com.document.allreader.allofficefilereader.fc.hssf.formula.eval;

/**
 * @author Amol S Deshmukh &lt; amolweb at ya hoo dot com &gt;
 *
 * RefEval is the super interface for Ref2D and Ref3DEval. Basically a RefEval
 * impl should contain reference to the original ReferencePtg or Ref3DPtg as
 * well as the final "value" resulting from the evaluation of the cell
 * reference. Thus if the Cell has type CELL_TYPE_NUMERIC, the contained
 * value object should be of type NumberEval; if cell type is CELL_TYPE_STRING,
 * contained value object should be of type StringEval
 */
public interface RefEval extends ValueEval {

    /**
     * @return the evaluated value of the cell referred to by this RefEval.
     */
    ValueEval getInnerValueEval();

    /**
     * returns the zero based column index.
     */
    int getColumn();

    /**
     * returns the zero based row index.
     */
    int getRow();

    /**
     * Creates an {@link AreaEval} offset by a relative amount from this RefEval
     */
    AreaEval offset(int relFirstRowIx, int relLastRowIx, int relFirstColIx, int relLastColIx);
}
