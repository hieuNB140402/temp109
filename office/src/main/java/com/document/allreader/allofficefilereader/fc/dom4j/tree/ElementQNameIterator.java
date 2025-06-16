

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.util.Iterator;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;


/**
 * <p>
 * <code>ElementQNameIterator</code> is a filtering {@link Iterator}which
 * filters out objects which do not implement the {@link Element}interface and
 * are not of the correct fully qualified element name.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 * 
 * @deprecated THIS CLASS WILL BE REMOVED IN dom4j-1.6 !!
 */
public class ElementQNameIterator extends FilterIterator
{
    private QName qName;

    public ElementQNameIterator(Iterator proxy, QName qName)
    {
        super(proxy);
        this.qName = qName;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param object
     *            DOCUMENT ME!
     * 
     * @return true if the given element implements the {@link Element}
     *         interface and matches the given {@link QName}
     */
    protected boolean matches(Object object)
    {
        if (object instanceof Element)
        {
            Element element = (Element)object;

            return qName.equals(element.getQName());
        }

        return false;
    }
}

