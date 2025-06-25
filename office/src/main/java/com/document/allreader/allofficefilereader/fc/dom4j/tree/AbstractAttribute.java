

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.io.IOException;
import java.io.Writer;

import com.document.allreader.allofficefilereader.fc.dom4j.Attribute;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;
import com.document.allreader.allofficefilereader.fc.dom4j.Node;
import com.document.allreader.allofficefilereader.fc.dom4j.Visitor;


/**
 * <p>
 * <code>AbstractNamespace</code> is an abstract base class for tree
 * implementors to use for implementation inheritence.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.21 $
 */
public abstract class AbstractAttribute extends AbstractNode implements Attribute
{
    public short getNodeType()
    {
        return ATTRIBUTE_NODE;
    }

    public void setNamespace(Namespace namespace)
    {
        String msg = "This Attribute is read only and cannot be changed";
        throw new UnsupportedOperationException(msg);
    }

    public String getText()
    {
        return getValue();
    }

    public void setText(String text)
    {
        setValue(text);
    }

    public void setValue(String value)
    {
        String msg = "This Attribute is read only and cannot be changed";
        throw new UnsupportedOperationException(msg);
    }

    public Object getData()
    {
        return getValue();
    }

    public void setData(Object data)
    {
        setValue((data == null) ? null : data.toString());
    }

    public String toString()
    {
        return super.toString() + " [Attribute: name " + getQualifiedName() + " value \""
            + getValue() + "\"]";
    }

    public String asXML()
    {
        return getQualifiedName() + "=\"" + getValue() + "\"";
    }

    public void write(Writer writer) throws IOException
    {
        writer.write(getQualifiedName());
        writer.write("=\"");
        writer.write(getValue());
        writer.write("\"");
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }

    // QName methods
    public Namespace getNamespace()
    {
        return getQName().getNamespace();
    }

    public String getName()
    {
        return getQName().getName();
    }

    public String getNamespacePrefix()
    {
        return getQName().getNamespacePrefix();
    }

    public String getNamespaceURI()
    {
        return getQName().getNamespaceURI();
    }

    public String getQualifiedName()
    {
        return getQName().getQualifiedName();
    }

    public String getPath(Element context)
    {
        StringBuffer result = new StringBuffer();

        Element parent = getParent();

        if ((parent != null) && (parent != context))
        {
            result.append(parent.getPath(context));
            result.append("/");
        }

        result.append("@");

        String uri = getNamespaceURI();
        String prefix = getNamespacePrefix();

        if ((uri == null) || (uri.length() == 0) || (prefix == null) || (prefix.length() == 0))
        {
            result.append(getName());
        }
        else
        {
            result.append(getQualifiedName());
        }

        return result.toString();
    }

    public String getUniquePath(Element context)
    {
        StringBuffer result = new StringBuffer();

        Element parent = getParent();

        if ((parent != null) && (parent != context))
        {
            result.append(parent.getUniquePath(context));
            result.append("/");
        }

        result.append("@");

        String uri = getNamespaceURI();
        String prefix = getNamespacePrefix();

        if ((uri == null) || (uri.length() == 0) || (prefix == null) || (prefix.length() == 0))
        {
            result.append(getName());
        }
        else
        {
            result.append(getQualifiedName());
        }

        return result.toString();
    }

    protected Node createXPathResult(Element parent)
    {
        return new DefaultAttribute(parent, getQName(), getValue());
    }
}

