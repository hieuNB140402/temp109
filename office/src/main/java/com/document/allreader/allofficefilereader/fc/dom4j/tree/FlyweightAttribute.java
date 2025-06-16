

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;

/**
 * <p>
 * <code>FlyweightAttribute</code> is a Flyweight pattern implementation of a
 * singly linked, read-only XML Attribute.
 * </p>
 * 
 * <p>
 * This node could be shared across documents and elements though it does not
 * support the parent relationship.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public class FlyweightAttribute extends AbstractAttribute
{
    /** The <code>QName</code> for this element */
    private QName qname;

    /** The value of the <code>Attribute</code> */
    protected String value;

    public FlyweightAttribute(QName qname)
    {
        this.qname = qname;
    }

    public FlyweightAttribute(QName qname, String value)
    {
        this.qname = qname;
        this.value = value;
    }

    /**
     * Creates the <code>Attribute</code> with the specified local name and
     * value.
     * 
     * @param name
     *            is the name of the attribute
     * @param value
     *            is the value of the attribute
     */
    public FlyweightAttribute(String name, String value)
    {
        this.qname = getDocumentFactory().createQName(name);
        this.value = value;
    }

    /**
     * Creates the <code>Attribute</code> with the specified local name, value
     * and <code>Namespace</code>.
     * 
     * @param name
     *            is the name of the attribute
     * @param value
     *            is the value of the attribute
     * @param namespace
     *            is the namespace of the attribute
     */
    public FlyweightAttribute(String name, String value, Namespace namespace)
    {
        this.qname = getDocumentFactory().createQName(name, namespace);
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public QName getQName()
    {
        return qname;
    }
}

