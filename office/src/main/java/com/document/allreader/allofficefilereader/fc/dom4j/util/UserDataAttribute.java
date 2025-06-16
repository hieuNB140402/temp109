

package com.document.allreader.allofficefilereader.fc.dom4j.util;

import com.document.allreader.allofficefilereader.fc.dom4j.QName;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultAttribute;


/**
 * <p>
 * <code>UserDataAttribute</code> support the adornment of a user data object
 * on an Element or Attribute instance such that the methods {@link#getData}
 * {@link #setData(Object)}will get and set the values of a user data object.
 * This can be useful for developers wishing to create XML trees and adorn the
 * trees with user defined objects.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.8 $
 */
public class UserDataAttribute extends DefaultAttribute
{
    /** The user data object */
    private Object data;

    public UserDataAttribute(QName qname)
    {
        super(qname);
    }

    public UserDataAttribute(QName qname, String text)
    {
        super(qname, text);
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}

