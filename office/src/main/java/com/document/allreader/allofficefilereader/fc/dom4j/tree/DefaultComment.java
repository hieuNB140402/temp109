

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;

/**
 * <p>
 * <code>DefaultComment</code> is the default Comment implementation. It is a
 * doubly linked node which supports the parent relationship and can be modified
 * in place.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.11 $
 */
public class DefaultComment extends FlyweightComment
{
    /** The parent of this node */
    private Element parent;

    /**
     * DOCUMENT ME!
     * 
     * @param text
     *            is the Comment text
     */
    public DefaultComment(String text)
    {
        super(text);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param parent
     *            is the parent element
     * @param text
     *            is the Comment text
     */
    public DefaultComment(Element parent, String text)
    {
        super(text);
        this.parent = parent;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Element getParent()
    {
        return parent;
    }

    public void setParent(Element parent)
    {
        this.parent = parent;
    }

    public boolean supportsParent()
    {
        return true;
    }

    public boolean isReadOnly()
    {
        return false;
    }
}

