

package com.document.allreader.allofficefilereader.fc.openxml4j.exceptions;

/**
 * Throw when an invalid operation is done.
 *
 * @author Julien Chable
 * @version 1.0
 */
@SuppressWarnings("serial")
public final class InvalidOperationException extends OpenXML4JRuntimeException{

	public InvalidOperationException(String message){
		super(message);
	}
}
