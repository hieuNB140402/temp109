

package com.document.allreader.allofficefilereader.fc.dom4j;

import java.util.List;

/**
 * <p>
 * <code>DocumentType</code> defines an XML DOCTYPE declaration.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
public interface DocumentType extends Node {
    /**
     * This method is the equivalent to the {@link #getName}method. It is added
     * for clarity.
     * 
     * @return the root element name for the document type.
     */
    String getElementName();

    /**
     * This method is the equivalent to the {@link #setName}method. It is added
     * for clarity.
     * 
     * @param elementName
     *            DOCUMENT ME!
     */
    void setElementName(String elementName);

    String getPublicID();

    void setPublicID(String publicID);

    String getSystemID();

    void setSystemID(String systemID);

    /**
     * Returns a list of internal DTD declaration objects, defined in the
     * {@link org.dom4j.dtd}package
     * 
     * @return DOCUMENT ME!
     */
    List getInternalDeclarations();

    /**
     * Sets the list of internal DTD declaration objects, defined in the
     * {@link org.dom4j.dtd}package
     * 
     * @param declarations
     *            DOCUMENT ME!
     */
    void setInternalDeclarations(List declarations);

    /**
     * Returns a list of internal DTD declaration objects, defined in the
     * {@link org.dom4j.dtd}package
     * 
     * @return DOCUMENT ME!
     */
    List getExternalDeclarations();

    /**
     * Sets the list of internal DTD declaration objects, defined in the
     * {@link org.dom4j.dtd}package
     * 
     * @param declarations
     *            DOCUMENT ME!
     */
    void setExternalDeclarations(List declarations);
}

