

package com.document.allreader.allofficefilereader.fc.openxml4j.exceptions;

/**
 * Global exception throws when a critical error occurs (this exception is
 * set as Runtime in order not to force the user to manage the exception in a
 * try/catch).
 *
 * @author Julien Chable
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OpenXML4JRuntimeException extends RuntimeException {

	public OpenXML4JRuntimeException(String msg) {
		super(msg);
	}

    public OpenXML4JRuntimeException(String msg, Throwable reason) {
        super(msg, reason);
    }
}
