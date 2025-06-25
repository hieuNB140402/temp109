

package com.document.allreader.allofficefilereader.fc.openxml4j.exceptions;

/**
 * Global exception throws when a critical error occurs. (this exception is not
 * set as Runtime in order to force user to manage the exception in a
 * try/catch).
 *
 * @author CDubettier, Julien Chable
 * @version 1.0
 */
@ SuppressWarnings("serial")
public class OpenXML4JException extends Exception
{

    public OpenXML4JException(String msg)
    {
        super(msg);
    }
}
