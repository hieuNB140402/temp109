

package com.document.allreader.allofficefilereader.fc.dom4j.io;

/**
 * Exception internally thrown by the SAX Modification classes. This is a
 * RuntimeException since the {@link org.dom4j.ElementHandler}methods do not
 * throw Exceptions.
 * 
 * @author Wonne Keysers (Realsoftware.be)
 */
class SAXModifyException extends RuntimeException {
    /**
     * DOCUMENT ME!
     * 
     * @param cause
     *            The causing {@link java.lang.Throwable}
     */
    protected SAXModifyException(Throwable cause) {
        super(cause);
    }
}

