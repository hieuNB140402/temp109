

package com.document.allreader.allofficefilereader.fc.hssf.usermodel;

import com.document.allreader.allofficefilereader.fc.hssf.formula.EvaluationCell;
import com.document.allreader.allofficefilereader.fc.hssf.formula.EvaluationSheet;
import com.document.allreader.allofficefilereader.ss.model.XLSModel.ACell;
import com.document.allreader.allofficefilereader.ss.model.XLSModel.ARow;
import com.document.allreader.allofficefilereader.ss.model.XLSModel.ASheet;

/**
 * HSSF wrapper for a sheet under evaluation
 * 
 * @author Josh Micich
 */
/*final*/ class HSSFEvaluationSheet implements EvaluationSheet {

	private /*final*/ ASheet _hs;

	public HSSFEvaluationSheet(ASheet hs) {
		_hs = hs;
	}

	public void setASheet(ASheet hs)
	{
	    _hs = hs;
	}
	
	public ASheet getASheet() {
		return _hs;
	}
	public EvaluationCell getCell(int rowIndex, int columnIndex) {
		ARow row = (ARow)_hs.getRow(rowIndex);
		if (row == null) {
			return null;
		}
		ACell cell = (ACell)row.getCell(columnIndex);
		if (cell == null) {
			return null;
		}
		return new HSSFEvaluationCell(cell, this);
	}
}
