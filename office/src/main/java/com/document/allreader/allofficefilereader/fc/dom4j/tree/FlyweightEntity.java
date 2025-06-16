

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Node;

/**
 * <p>
 * <code>FlyweightEntity</code> is a Flyweight pattern implementation of a
 * singly linked, read-only XML entity.
 * </p>
 * 
 * <p>
 * This node could be shared across documents and elements though it does not
 * support the parent relationship.
 * </p>
 * 
 * <p>
 * Often this node needs to be created and then the text content added later
 * (for example in SAX) so this implementation allows a call to {@link#setText}
 * providing the entity has no text already.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.6 $
 */
public class FlyweightEntity extends AbstractEntity
{
    /** The name of the <code>Entity</code> */
    protected String name;

    /** The text of the <code>Entity</code> */
    protected String text;

    /**
     * A default constructor for implementors to use.
     */
    protected FlyweightEntity()
    {
    }

    /**
     * Creates the <code>Entity</code> with the specified name
     * 
     * @param name
     *            is the name of the entity
     */
    public FlyweightEntity(String name)
    {
        this.name = name;
    }

    /**
     * Creates the <code>Entity</code> with the specified name and text.
     * 
     * @param name
     *            is the name of the entity
     * @param text
     *            is the text of the entity
     */
    public FlyweightEntity(String name, String text)
    {
        this.name = name;
        this.text = text;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the name of the entity
     */
    public String getName()
    {
        return name;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the text of the entity
     */
    public String getText()
    {
        return text;
    }

    /**
     * sets the value of the entity if it is not defined yet otherwise an
     * <code>UnsupportedOperationException</code> is thrown as this class is
     * read only.
     * 
     * @param text
     *            DOCUMENT ME!
     * 
     * @throws UnsupportedOperationException
     *             DOCUMENT ME!
     */
    public void setText(String text)
    {
        if (this.text != null)
        {
            this.text = text;
        }
        else
        {
            throw new UnsupportedOperationException("This Entity is read-only. "
                + "It cannot be modified");
        }
    }

    protected Node createXPathResult(Element parent)
    {
        return new DefaultEntity(parent, getName(), getText());
    }
}

