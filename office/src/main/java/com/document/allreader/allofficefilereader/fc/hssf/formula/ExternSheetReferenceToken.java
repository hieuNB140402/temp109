

package com.document.allreader.allofficefilereader.fc.hssf.formula;

/**
 * Should be implemented by any {@link com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.Ptg} subclass that needs has an extern sheet index <br/>
 *
 * For POI internal use only
 *
 * @author Josh Micich
 */
public interface ExternSheetReferenceToken {
	int getExternSheetIndex();
	/**
	 * @return formula text for this reference token without the qualifying sheet name
	 */
	String format2DRefAsString();
}
