

package com.document.allreader.allofficefilereader.fc.dom4j.jaxb;

/**
 * Exception internally thrown by the JAXBReader classes. This is a
 * RuntimeException since the {@link org.dom4j.ElementHandler}methods do not
 * throw Exceptions.
 * 
 * @author Wonne Keysers (Realsoftware.be)
 */
class JAXBRuntimeException extends RuntimeException {
    /**
     * DOCUMENT ME!
     * 
     * @param cause
     *            The causing {@link java.lang.Throwable}
     */
    protected JAXBRuntimeException(Throwable cause) {
        super(cause);
    }
}

