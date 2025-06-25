

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.util.Iterator;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;


/**
 * <p>
 * <code>ElementIterator</code> is a filtering {@link Iterator}which filters
 * out objects which do not implement the {@link Element}interface.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.8 $
 * 
 * @deprecated THIS CLASS WILL BE REMOVED IN dom4j-1.6 !!
 */
public class ElementIterator extends FilterIterator
{
    public ElementIterator(Iterator proxy)
    {
        super(proxy);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param element
     *            DOCUMENT ME!
     * 
     * @return true if the given element implements the {@link Element}
     *         interface
     */
    protected boolean matches(Object element)
    {
        return element instanceof Element;
    }
}

