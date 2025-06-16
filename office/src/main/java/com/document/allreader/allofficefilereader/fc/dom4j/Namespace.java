

package com.document.allreader.allofficefilereader.fc.dom4j;

import com.document.allreader.allofficefilereader.fc.dom4j.tree.AbstractNode;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultNamespace;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.NamespaceCache;

/**
 * <p>
 * <code>Namespace</code> is a Flyweight Namespace that can be shared amongst
 * nodes.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.22 $
 */
public class Namespace extends AbstractNode
{
    /** Cache of Namespace instances */
    protected static final NamespaceCache CACHE = new NamespaceCache();

    /** XML Namespace */
    public static final Namespace XML_NAMESPACE = CACHE.get("xml",
        "http://www.w3.org/XML/1998/namespace");

    /** No Namespace present */
    public static final Namespace NO_NAMESPACE = CACHE.get("", "");

    /** The prefix mapped to this namespace */
    private String prefix;

    /** The URI for this namespace */
    private String uri;

    /** A cached version of the hashcode for efficiency */
    private int hashCode;

    /**
     * DOCUMENT ME!
     * 
     * @param prefix
     *            is the prefix for this namespace
     * @param uri
     *            is the URI for this namespace
     */
    public Namespace(String prefix, String uri)
    {
        this.prefix = (prefix != null) ? prefix : "";
        this.uri = (uri != null) ? uri : "";
    }

    /**
     * A helper method to return the Namespace instance for the given prefix and
     * URI
     * 
     * @param prefix
     *            DOCUMENT ME!
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return an interned Namespace object
     */
    public static Namespace get(String prefix, String uri)
    {
        return CACHE.get(prefix, uri);
    }

    /**
     * A helper method to return the Namespace instance for no prefix and the
     * URI
     * 
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return an interned Namespace object
     */
    public static Namespace get(String uri)
    {
        return CACHE.get(uri);
    }

    public short getNodeType()
    {
        return NAMESPACE_NODE;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the hash code based on the qualified name and the URI of the
     *         namespace.
     */
    public int hashCode()
    {
        if (hashCode == 0)
        {
            hashCode = createHashCode();
        }

        return hashCode;
    }

    /**
     * Factory method to create the hashcode allowing derived classes to change
     * the behaviour
     * 
     * @return DOCUMENT ME!
     */
    protected int createHashCode()
    {
        int result = uri.hashCode() ^ prefix.hashCode();

        if (result == 0)
        {
            result = 0xbabe;
        }

        return result;
    }

    /**
     * Checks whether this Namespace equals the given Namespace. Two Namespaces
     * are equals if their URI and prefix are equal.
     * 
     * @param object
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        else if (object instanceof Namespace)
        {
            Namespace that = (Namespace)object;

            // we cache hash codes so this should be quick
            if (hashCode() == that.hashCode())
            {
                return uri.equals(that.getURI()) && prefix.equals(that.getPrefix());
            }
        }

        return false;
    }

    public String getText()
    {
        return uri;
    }

    public String getStringValue()
    {
        return uri;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the prefix for this <code>Namespace</code>.
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the URI for this <code>Namespace</code>.
     */
    public String getURI()
    {
        return uri;
    }

    public String getXPathNameStep()
    {
        if ((prefix != null) && !"".equals(prefix))
        {
            return "namespace::" + prefix;
        }

        return "namespace::*[name()='']";
    }

    public String getPath(Element context)
    {
        StringBuffer path = new StringBuffer(10);
        Element parent = getParent();

        if ((parent != null) && (parent != context))
        {
            path.append(parent.getPath(context));
            path.append('/');
        }

        path.append(getXPathNameStep());

        return path.toString();
    }

    public String getUniquePath(Element context)
    {
        StringBuffer path = new StringBuffer(10);
        Element parent = getParent();

        if ((parent != null) && (parent != context))
        {
            path.append(parent.getUniquePath(context));
            path.append('/');
        }

        path.append(getXPathNameStep());

        return path.toString();
    }

    public String toString()
    {
        return super.toString() + " [Namespace: prefix " + getPrefix() + " mapped to URI \""
            + getURI() + "\"]";
    }

    public String asXML()
    {
        StringBuffer asxml = new StringBuffer(10);
        String pref = getPrefix();

        if ((pref != null) && (pref.length() > 0))
        {
            asxml.append("xmlns:");
            asxml.append(pref);
            asxml.append("=\"");
        }
        else
        {
            asxml.append("xmlns=\"");
        }

        asxml.append(getURI());
        asxml.append("\"");

        return asxml.toString();
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }

    protected Node createXPathResult(Element parent)
    {
        return new DefaultNamespace(parent, getPrefix(), getURI());
    }
}

