

package com.document.allreader.allofficefilereader.fc.dom4j;

/**
 * <p>
 * <code>XPathException</code> is thrown when an exception occurs while
 * evaluating an XPath expression, usually due to some function throwing an
 * exception.
 * </p>
 * 
 * @version $Revision: 1.6 $
 */
public class XPathException extends RuntimeException {
    /** The XPath expression that caused the exception */
    private String xpath;

    public XPathException(String xpath) {
        super("Exception occurred evaluting XPath: " + xpath);
        this.xpath = xpath;
    }

    public XPathException(String xpath, String reason) {
        super("Exception occurred evaluting XPath: " + xpath + " " + reason);
        this.xpath = xpath;
    }

    public XPathException(String xpath, Exception e) {
        super("Exception occurred evaluting XPath: " + xpath + ". Exception: "
                + e.getMessage());
        this.xpath = xpath;
    }

    /**
     * Returns the XPath expression that caused the problem
     * 
     * @return DOCUMENT ME!
     */
    public String getXPath() {
        return xpath;
    }
}

