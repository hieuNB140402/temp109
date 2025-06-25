

package com.document.allreader.allofficefilereader.fc.hssf.usermodel;

import com.document.allreader.allofficefilereader.fc.hssf.formula.EvaluationCell;
import com.document.allreader.allofficefilereader.fc.hssf.formula.EvaluationSheet;
import com.document.allreader.allofficefilereader.ss.model.XLSModel.ACell;
import com.document.allreader.allofficefilereader.ss.model.XLSModel.ASheet;
/**
 * HSSF wrapper for a cell under evaluation
 * 
 * @author Josh Micich
 */
final class HSSFEvaluationCell implements EvaluationCell {

	private /*final*/ EvaluationSheet _evalSheet;
	private /*final*/ ACell _cell;

	public HSSFEvaluationCell(ACell cell, EvaluationSheet evalSheet) {
		_cell = cell;
		_evalSheet = evalSheet;
	}
	public HSSFEvaluationCell(ACell cell) 
	{
		this(cell, new HSSFEvaluationSheet((ASheet)cell.getSheet()));
	}
	public Object getIdentityKey() {
		// save memory by just using the cell itself as the identity key
		// Note - this assumes HSSFCell has not overridden hashCode and equals
		return _cell;
	}

	public void setHSSFCell(ACell cell) 
	{
         _cell = cell;
         if(_evalSheet != null)
         {
             ((HSSFEvaluationSheet)_evalSheet).setASheet((ASheet)cell.getSheet());
         }
         else
         {
             _evalSheet = new HSSFEvaluationSheet((ASheet)cell.getSheet());
         }
    }
	
	public ACell getACell() {
		return _cell;
	}
	public boolean getBooleanCellValue() {
		return _cell.getBooleanCellValue();
	}
	public int getCellType() {
		return _cell.getCellType();
	}
	public int getColumnIndex() {
		return _cell.getColNumber();
	}
	public int getErrorCellValue() {
		return _cell.getErrorCellValue();
	}
	public double getNumericCellValue() {
		return _cell.getNumericCellValue();
	}
	public int getRowIndex() {
		return _cell.getRowNumber();
	}
	public EvaluationSheet getSheet() {
		return _evalSheet;
	}
	public String getStringCellValue() {
		//return _cell.getRichStringCellValue().getString();
	    
	    return _cell.getStringCellValue();
	}
}
