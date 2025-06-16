

package com.document.allreader.allofficefilereader.fc.dom4j.util;

import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;

/**
 * <p>
 * <code>NonLazyDocumentFactory</code> is a factory of XML objects which avoid
 * using the lazy creation pattern. This results in a slower creation of a
 * Document and uses more memory but it means that the same Document instance
 * can be shared across threads provided it is not modified.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class NonLazyDocumentFactory extends DocumentFactory
{
    /** The Singleton instance */
    protected static transient NonLazyDocumentFactory singleton = new NonLazyDocumentFactory();

    /**
     * <p>
     * Access to the singleton instance of this factory.
     * </p>
     * 
     * @return the default singleon instance
     */
    public static DocumentFactory getInstance()
    {
        return singleton;
    }

    // DocumentFactory methods
    // -------------------------------------------------------------------------
    public Element createElement(QName qname)
    {
        return new NonLazyElement(qname);
    }
}

