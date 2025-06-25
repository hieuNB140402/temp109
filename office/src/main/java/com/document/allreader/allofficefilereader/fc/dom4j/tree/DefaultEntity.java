

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;

/**
 * <p>
 * <code>DefaultEntity</code> is the default Entity implementation. It is a
 * doubly linked node which supports the parent relationship and can be modified
 * in place.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.11 $
 */
public class DefaultEntity extends FlyweightEntity
{
    /** The parent of this node */
    private Element parent;

    /**
     * Creates the <code>Entity</code> with the specified name
     * 
     * @param name
     *            is the name of the entity
     */
    public DefaultEntity(String name)
    {
        super(name);
    }

    /**
     * Creates the <code>Entity</code> with the specified name and text.
     * 
     * @param name
     *            is the name of the entity
     * @param text
     *            is the text of the entity
     */
    public DefaultEntity(String name, String text)
    {
        super(name, text);
    }

    /**
     * Creates the <code>Entity</code> with the specified name and text.
     * 
     * @param parent
     *            is the parent element
     * @param name
     *            is the name of the entity
     * @param text
     *            is the text of the entity
     */
    public DefaultEntity(Element parent, String name, String text)
    {
        super(name, text);
        this.parent = parent;
    }

    public void setName(String name)
    {
        this.name = name;
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

