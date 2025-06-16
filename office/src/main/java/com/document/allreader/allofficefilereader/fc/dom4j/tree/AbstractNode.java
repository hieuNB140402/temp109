

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.dom4j.Document;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Node;
import com.document.allreader.allofficefilereader.fc.dom4j.NodeFilter;
import com.document.allreader.allofficefilereader.fc.dom4j.XPath;
import com.document.allreader.allofficefilereader.fc.dom4j.rule.Pattern;


/**
 * <p>
 * <code>AbstractNode</code> is an abstract base class for tree implementors
 * to use for implementation inheritence.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.31 $
 */
public abstract class AbstractNode implements Node, Cloneable, Serializable
{
    protected static final String[] NODE_TYPE_NAMES = {"Node", "Element", "Attribute", "Text",
        "CDATA", "Entity", "Entity", "ProcessingInstruction", "Comment", "Document",
        "DocumentType", "DocumentFragment", "Notation", "Namespace", "Unknown"};

    /** The <code>DocumentFactory</code> instance used by default */
    private static final DocumentFactory DOCUMENT_FACTORY = DocumentFactory.getInstance();

    public AbstractNode()
    {
    }

    public short getNodeType()
    {
        return UNKNOWN_NODE;
    }

    public String getNodeTypeName()
    {
        int type = getNodeType();

        if ((type < 0) || (type >= NODE_TYPE_NAMES.length))
        {
            return "Unknown";
        }

        return NODE_TYPE_NAMES[type];
    }

    public Document getDocument()
    {
        Element element = getParent();

        return (element != null) ? element.getDocument() : null;
    }

    public void setDocument(Document document)
    {
    }

    public Element getParent()
    {
        return null;
    }

    public void setParent(Element parent)
    {
    }

    public boolean supportsParent()
    {
        return false;
    }

    public boolean isReadOnly()
    {
        return true;
    }

    public boolean hasContent()
    {
        return false;
    }

    public String getPath()
    {
        return getPath(null);
    }

    public String getUniquePath()
    {
        return getUniquePath(null);
    }

    public Object clone()
    {
        if (isReadOnly())
        {
            return this;
        }
        else
        {
            try
            {
                Node answer = (Node)super.clone();
                answer.setParent(null);
                answer.setDocument(null);

                return answer;
            }
            catch(CloneNotSupportedException e)
            {
                // should never happen
                throw new RuntimeException("This should never happen. Caught: " + e);
            }
        }
    }

    public Node detach()
    {
        Element parent = getParent();

        if (parent != null)
        {
            parent.remove(this);
        }
        else
        {
            Document document = getDocument();

            if (document != null)
            {
                document.remove(this);
            }
        }

        setParent(null);
        setDocument(null);

        return this;
    }

    public String getName()
    {
        return null;
    }

    public void setName(String name)
    {
        throw new UnsupportedOperationException("This node cannot be modified");
    }

    public String getText()
    {
        return null;
    }

    public String getStringValue()
    {
        return getText();
    }

    public void setText(String text)
    {
        throw new UnsupportedOperationException("This node cannot be modified");
    }

    public void write(Writer writer) throws IOException
    {
        writer.write(asXML());
    }

    // XPath methods
    public Object selectObject(String xpathExpression)
    {
        XPath xpath = createXPath(xpathExpression);

        return xpath.evaluate(this);
    }

    public List selectNodes(String xpathExpression)
    {
        XPath xpath = createXPath(xpathExpression);

        return xpath.selectNodes(this);
    }

    public List selectNodes(String xpathExpression, String comparisonXPathExpression)
    {
        return selectNodes(xpathExpression, comparisonXPathExpression, false);
    }

    public List selectNodes(String xpathExpression, String comparisonXPathExpression,
        boolean removeDuplicates)
    {
        XPath xpath = createXPath(xpathExpression);
        XPath sortBy = createXPath(comparisonXPathExpression);

        return xpath.selectNodes(this, sortBy, removeDuplicates);
    }

    public Node selectSingleNode(String xpathExpression)
    {
        XPath xpath = createXPath(xpathExpression);

        return xpath.selectSingleNode(this);
    }

    public String valueOf(String xpathExpression)
    {
        XPath xpath = createXPath(xpathExpression);

        return xpath.valueOf(this);
    }

    public Number numberValueOf(String xpathExpression)
    {
        XPath xpath = createXPath(xpathExpression);

        return xpath.numberValueOf(this);
    }

    public boolean matches(String patternText)
    {
        NodeFilter filter = createXPathFilter(patternText);

        return filter.matches(this);
    }

    public XPath createXPath(String xpathExpression)
    {
        return getDocumentFactory().createXPath(xpathExpression);
    }

    public NodeFilter createXPathFilter(String patternText)
    {
        return getDocumentFactory().createXPathFilter(patternText);
    }

    public Pattern createPattern(String patternText)
    {
        return getDocumentFactory().createPattern(patternText);
    }

    public Node asXPathResult(Element parent)
    {
        if (supportsParent())
        {
            return this;
        }

        return createXPathResult(parent);
    }

    protected DocumentFactory getDocumentFactory()
    {
        return DOCUMENT_FACTORY;
    }

    protected Node createXPathResult(Element parent)
    {
        throw new RuntimeException("asXPathResult() not yet implemented fully " + "for: " + this);
    }
}

