

package com.document.allreader.allofficefilereader.constant.fc;

import com.document.allreader.allofficefilereader.fc.ss.usermodel.ErrorConstants;


public class ErrorConstant {

	private static final ErrorConstants EC = null;

	private static final ErrorConstant NULL = new ErrorConstant(EC.ERROR_NULL);
	private static final ErrorConstant DIV_0 = new ErrorConstant(EC.ERROR_DIV_0);
	private static final ErrorConstant VALUE = new ErrorConstant(EC.ERROR_VALUE);
	private static final ErrorConstant REF = new ErrorConstant(EC.ERROR_REF);
	private static final ErrorConstant NAME = new ErrorConstant(EC.ERROR_NAME);
	private static final ErrorConstant NUM = new ErrorConstant(EC.ERROR_NUM);
	private static final ErrorConstant NA = new ErrorConstant(EC.ERROR_NA);

	private final int _errorCode;

	private ErrorConstant(int errorCode) {
		_errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return _errorCode;
	}
	public String getText() {
		if(ErrorConstants.isValidCode(_errorCode)) {
			return ErrorConstants.getText(_errorCode);
		}
		return "unknown error code (" + _errorCode + ")";
	}

	public static ErrorConstant valueOf(int errorCode) {
		switch (errorCode) {
			case ErrorConstants.ERROR_NULL:  return NULL;
			case ErrorConstants.ERROR_DIV_0: return DIV_0;
			case ErrorConstants.ERROR_VALUE: return VALUE;
			case ErrorConstants.ERROR_REF:   return REF;
			case ErrorConstants.ERROR_NAME:  return NAME;
			case ErrorConstants.ERROR_NUM:   return NUM;
			case ErrorConstants.ERROR_NA:	return NA;
		}
		System.err.println("Warning - unexpected error code (" + errorCode + ")");
		return new ErrorConstant(errorCode);
	}
	public String toString() {
		StringBuffer sb = new StringBuffer(64);
		sb.append(getClass().getName()).append(" [");
		sb.append(getText());
		sb.append("]");
		return sb.toString();
	}
}
