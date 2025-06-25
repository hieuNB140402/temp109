
package com.document.allreader.allofficefilereader.fc.ss.usermodel;

import com.document.allreader.allofficefilereader.fc.hssf.usermodel.IClientAnchor;

/**
 * An object that handles instantiating concrete
 *  classes of the various instances one needs for
 *  HSSF and XSSF.
 * Works around a major shortcoming in Java, where we
 *  can't have static methods on interfaces or abstract
 *  classes.
 * This allows you to get the appropriate class for
 *  a given interface, without you having to worry
 *  about if you're dealing with HSSF or XSSF, despite
 *  Java being quite rubbish.
 */
public interface CreationHelper {
    /**
     * Creates a new RichTextString instance
     * @param text The text to initialise the RichTextString with
     */
    RichTextString createRichTextString(String text);

    /**
     * Creates a new DataFormat instance
     */
    DataFormat createDataFormat();

    /**
     * Creates a new Hyperlink, of the given type
     */
    IHyperlink createHyperlink(int type);

    /**
     * Creates FormulaEvaluator - an object that evaluates formula cells.
     *
     * @return a FormulaEvaluator instance
     */
    FormulaEvaluator createFormulaEvaluator();

    IClientAnchor createClientAnchor();
}
