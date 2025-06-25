

package com.document.allreader.allofficefilereader.fc.dom4j.util;

import java.util.Map;

import com.document.allreader.allofficefilereader.fc.dom4j.Attribute;
import com.document.allreader.allofficefilereader.fc.dom4j.CDATA;
import com.document.allreader.allofficefilereader.fc.dom4j.Comment;
import com.document.allreader.allofficefilereader.fc.dom4j.Document;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentType;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Entity;
import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;
import com.document.allreader.allofficefilereader.fc.dom4j.NodeFilter;
import com.document.allreader.allofficefilereader.fc.dom4j.ProcessingInstruction;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;
import com.document.allreader.allofficefilereader.fc.dom4j.Text;
import com.document.allreader.allofficefilereader.fc.dom4j.XPath;
import com.document.allreader.allofficefilereader.fc.dom4j.rule.Pattern;


/**
 * <p>
 * <code>ProxyDocumentFactory</code> implements a proxy to a DocumentFactory
 * which is useful for implementation inheritence, allowing the pipelining of
 * various factory implementations. For example an EncodingDocumentFactory which
 * takes care of encoding strings outside of allowable XML ranges could be used
 * with a DatatypeDocumentFactory which is XML Schema Data Type aware.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.13 $
 */
public abstract class ProxyDocumentFactory
{
    private DocumentFactory proxy;

    public ProxyDocumentFactory()
    {
        // use default factory
        this.proxy = DocumentFactory.getInstance();
    }

    public ProxyDocumentFactory(DocumentFactory proxy)
    {
        this.proxy = proxy;
    }

    // Factory methods
    // -------------------------------------------------------------------------
    public Document createDocument()
    {
        return proxy.createDocument();
    }

    public Document createDocument(Element rootElement)
    {
        return proxy.createDocument(rootElement);
    }

    public DocumentType createDocType(String name, String publicId, String systemId)
    {
        return proxy.createDocType(name, publicId, systemId);
    }

    public Element createElement(QName qname)
    {
        return proxy.createElement(qname);
    }

    public Element createElement(String name)
    {
        return proxy.createElement(name);
    }

    public Attribute createAttribute(Element owner, QName qname, String value)
    {
        return proxy.createAttribute(owner, qname, value);
    }

    public Attribute createAttribute(Element owner, String name, String value)
    {
        return proxy.createAttribute(owner, name, value);
    }

    public CDATA createCDATA(String text)
    {
        return proxy.createCDATA(text);
    }

    public Comment createComment(String text)
    {
        return proxy.createComment(text);
    }

    public Text createText(String text)
    {
        return proxy.createText(text);
    }

    public Entity createEntity(String name, String text)
    {
        return proxy.createEntity(name, text);
    }

    public Namespace createNamespace(String prefix, String uri)
    {
        return proxy.createNamespace(prefix, uri);
    }

    public ProcessingInstruction createProcessingInstruction(String target, String data)
    {
        return proxy.createProcessingInstruction(target, data);
    }

    public ProcessingInstruction createProcessingInstruction(String target, Map data)
    {
        return proxy.createProcessingInstruction(target, data);
    }

    public QName createQName(String localName, Namespace namespace)
    {
        return proxy.createQName(localName, namespace);
    }

    public QName createQName(String localName)
    {
        return proxy.createQName(localName);
    }

    public QName createQName(String name, String prefix, String uri)
    {
        return proxy.createQName(name, prefix, uri);
    }

    public QName createQName(String qualifiedName, String uri)
    {
        return proxy.createQName(qualifiedName, uri);
    }

    public XPath createXPath(String xpathExpression)
    {
        return proxy.createXPath(xpathExpression);
    }

    /*public XPath createXPath(String xpathExpression, VariableContext variableContext)
    {
        return proxy.createXPath(xpathExpression, variableContext);
    }

    public NodeFilter createXPathFilter(String xpathFilterExpression,
        VariableContext variableContext)
    {
        return proxy.createXPathFilter(xpathFilterExpression, variableContext);
    }*/

    public NodeFilter createXPathFilter(String xpathFilterExpression)
    {
        return proxy.createXPathFilter(xpathFilterExpression);
    }

    public Pattern createPattern(String xpathPattern)
    {
        return proxy.createPattern(xpathPattern);
    }


    // -------------------------------------------------------------------------
    protected DocumentFactory getProxy()
    {
        return proxy;
    }

    protected void setProxy(DocumentFactory proxy)
    {
        if (proxy == null)
        {
            // use default factory
            proxy = DocumentFactory.getInstance();
        }

        this.proxy = proxy;
    }
}

