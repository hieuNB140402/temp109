
package com.document.allreader.allofficefilereader.fc.ss.usermodel;

import com.document.allreader.allofficefilereader.fc.ss.util.CellRangeAddressList;

/**
 * @author <a href="rjankiraman@emptoris.com">Radhakrishnan J</a>
 * 
 */
public interface DataValidationHelper {
	
	DataValidationConstraint createFormulaListConstraint(String listFormula);

	DataValidationConstraint createExplicitListConstraint(String[] listOfValues);

	DataValidationConstraint createNumericConstraint(int validationType,int operatorType, String formula1, String formula2);
	
	DataValidationConstraint createTextLengthConstraint(int operatorType, String formula1, String formula2);
	
	DataValidationConstraint createDecimalConstraint(int operatorType, String formula1, String formula2);
	
	DataValidationConstraint createIntegerConstraint(int operatorType, String formula1, String formula2);
	
	DataValidationConstraint createDateConstraint(int operatorType, String formula1, String formula2,String dateFormat);
	
	DataValidationConstraint createTimeConstraint(int operatorType, String formula1, String formula2);
	
	DataValidationConstraint createCustomConstraint(String formula);
	
	DataValidation createValidation(DataValidationConstraint constraint,CellRangeAddressList cellRangeAddressList);
}
