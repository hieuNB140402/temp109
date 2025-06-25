

package com.document.allreader.allofficefilereader.fc.dom4j.util;

import com.document.allreader.allofficefilereader.fc.dom4j.Attribute;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;

/**
 * <p>
 * <code>UserDataDocumentFactory</code> is a factory of XML objects which
 * support the adornment of a user data object on an Element or Attribute
 * instance such that the methods <code>getData()</code> and
 * <code>setData()</code> will get and set the values of a user data object.
 * This can be useful for developers wishing to create XML trees and adorn the
 * trees with user defined objects.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.11 $
 */
public class UserDataDocumentFactory extends DocumentFactory
{
    /** The Singleton instance */
    protected static transient UserDataDocumentFactory singleton = new UserDataDocumentFactory();

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
        return new UserDataElement(qname);
    }

    public Attribute createAttribute(Element owner, QName qname, String value)
    {
        return new UserDataAttribute(qname, value);
    }
}

