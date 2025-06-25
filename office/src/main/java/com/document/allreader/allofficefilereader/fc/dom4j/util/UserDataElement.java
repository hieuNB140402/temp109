

package com.document.allreader.allofficefilereader.fc.dom4j.util;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultElement;


/**
 * <p>
 * <code>UserDataElement</code> support the adornment of a user data object on
 * an Element or Attribute instance such that the methods {@link#getData}
 * {@link #setData(Object)}will get and set the values of a user data object.
 * This can be useful for developers wishing to create XML trees and adorn the
 * trees with user defined objects.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.12 $
 */
public class UserDataElement extends DefaultElement
{
    /** The user data object */
    private Object data;

    public UserDataElement(String name)
    {
        super(name);
    }

    public UserDataElement(QName qname)
    {
        super(qname);
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public String toString()
    {
        return super.toString() + " userData: " + data;
    }

    public Object clone()
    {
        UserDataElement answer = (UserDataElement)super.clone();

        if (answer != this)
        {
            answer.data = getCopyOfUserData();
        }

        return answer;
    }


    // -------------------------------------------------------------------------

    /**
     * If a deep copy of user data is required whenever the clone() or
     * createCopy() methods are called on this element then this method should
     * return a clone of the user data
     * 
     * @return DOCUMENT ME!
     */
    protected Object getCopyOfUserData()
    {
        return data;
    }

    protected Element createElement(String name)
    {
        Element answer = getDocumentFactory().createElement(name);
        answer.setData(getCopyOfUserData());

        return answer;
    }

    protected Element createElement(QName qName)
    {
        Element answer = getDocumentFactory().createElement(qName);
        answer.setData(getCopyOfUserData());

        return answer;
    }

    // protected DocumentFactory getDocumentFactory() {
    // return DOCUMENT_FACTORY;
    // }
}

