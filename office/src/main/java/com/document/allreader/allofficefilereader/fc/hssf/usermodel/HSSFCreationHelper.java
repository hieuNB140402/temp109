

package com.document.allreader.allofficefilereader.fc.hssf.usermodel;

import com.document.allreader.allofficefilereader.fc.ss.usermodel.CreationHelper;

public class HSSFCreationHelper implements CreationHelper {
	private HSSFWorkbook workbook;
	private HSSFDataFormat dataFormat;

	HSSFCreationHelper(HSSFWorkbook wb) {
		workbook = wb;

		// Create the things we only ever need one of
		dataFormat = new HSSFDataFormat(workbook.getWorkbook());
	}

	public HSSFRichTextString createRichTextString(String text) {
		return new HSSFRichTextString(text);
	}

	public HSSFDataFormat createDataFormat() {
		return dataFormat;
	}

	public HSSFHyperlink createHyperlink(int type) {
		return new HSSFHyperlink(type);
	}

	/**
	 * Creates a HSSFFormulaEvaluator, the object that evaluates formula cells.
	 *
	 * @return a HSSFFormulaEvaluator instance
	 */
	public HSSFFormulaEvaluator createFormulaEvaluator(){
		//return new HSSFFormulaEvaluator(workbook);
	    return null;
	}

	/**
	 * Creates a HSSFClientAnchor. Use this object to position drawing object in a sheet
	 *
	 * @return a HSSFClientAnchor instance
	 * @see com.document.allreader.allofficefilereader.fc.ss.usermodel.com.wxiwei.office.fc.ss.usermodel.com.wxiwei.office.fc.ss.usermodel.Drawing
	 */
	public HSSFClientAnchor createClientAnchor(){
		return new HSSFClientAnchor();
	}
}
