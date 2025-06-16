

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.util.Iterator;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;


/**
 * <p>
 * <code>ElementNameIterator</code> is a filtering {@link Iterator}which
 * filters out objects which do not implement the {@link Element}interface and
 * are not of the correct element name.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.11 $
 * 
 * @deprecated THIS CLASS WILL BE REMOVED IN dom4j-1.6 !!
 */
public class ElementNameIterator extends FilterIterator
{
    private String name;

    public ElementNameIterator(Iterator proxy, String name)
    {
        super(proxy);
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param object
     *            DOCUMENT ME!
     * 
     * @return true if the given element implements the {@link Element}
     *         interface
     */
    protected boolean matches(Object object)
    {
        if (object instanceof Element)
        {
            Element element = (Element)object;

            return name.equals(element.getName());
        }

        return false;
    }
}

