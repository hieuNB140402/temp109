

package com.document.allreader.allofficefilereader.fc.dom4j.util;

import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;

/**
 * <p>
 * <code>IndexedDocumentFactory</code> is a factory of XML objects which
 * create indexed Element implementations to allow quicker lookup via name of
 * Element and Attributes though at the expense of more memory used to create
 * the name indexes.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class IndexedDocumentFactory extends DocumentFactory
{
    /** The Singleton instance */
    protected static transient IndexedDocumentFactory singleton = new IndexedDocumentFactory();

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
        return new IndexedElement(qname);
    }

    public Element createElement(QName qname, int attributeCount)
    {
        return new IndexedElement(qname, attributeCount);
    }
}

