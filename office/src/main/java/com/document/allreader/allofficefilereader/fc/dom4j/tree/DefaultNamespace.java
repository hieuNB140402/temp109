

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;

/**
 * <p>
 * <code>DefaultNamespace</code> implements a doubly linked node which
 * supports the parent relationship and is mutable. It is useful when returning
 * results from XPath expressions.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.16 $
 */
public class DefaultNamespace extends Namespace
{
    /** The parent of this node */
    private Element parent;

    /**
     * DOCUMENT ME!
     * 
     * @param prefix
     *            is the prefix for this namespace
     * @param uri
     *            is the URI for this namespace
     */
    public DefaultNamespace(String prefix, String uri)
    {
        super(prefix, uri);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param parent
     *            is the parent element
     * @param prefix
     *            is the prefix for this namespace
     * @param uri
     *            is the URI for this namespace
     */
    public DefaultNamespace(Element parent, String prefix, String uri)
    {
        super(prefix, uri);
        this.parent = parent;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the hash code based on the qualified name and the URI of the
     *         namespace and the hashCode() of the parent element.
     */
    protected int createHashCode()
    {
        int hashCode = super.createHashCode();

        if (parent != null)
        {
            hashCode ^= parent.hashCode();
        }

        return hashCode;
    }

    /**
     * Implements an identity based comparsion using the parent element as well
     * as the prefix and URI
     * 
     * @param object
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public boolean equals(Object object)
    {
        if (object instanceof DefaultNamespace)
        {
            DefaultNamespace that = (DefaultNamespace)object;

            if (that.parent == parent)
            {
                return super.equals(object);
            }
        }

        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
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

