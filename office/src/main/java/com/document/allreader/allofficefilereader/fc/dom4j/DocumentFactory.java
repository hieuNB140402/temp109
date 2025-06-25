

package com.document.allreader.allofficefilereader.fc.dom4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.document.allreader.allofficefilereader.fc.dom4j.rule.Pattern;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.AbstractDocument;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultAttribute;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultCDATA;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultComment;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultDocument;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultDocumentType;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultElement;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultEntity;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultProcessingInstruction;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultText;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.QNameCache;
import com.document.allreader.allofficefilereader.fc.dom4j.util.SimpleSingleton;
import com.document.allreader.allofficefilereader.fc.dom4j.util.SingletonStrategy;
import com.document.allreader.allofficefilereader.fc.dom4j.xpath.DefaultXPath;
import com.document.allreader.allofficefilereader.fc.dom4j.xpath.XPathPattern;


/**
 * <p>
 * <code>DocumentFactory</code> is a collection of factory methods to allow
 * easy custom building of DOM4J trees. The default tree that is built uses a
 * doubly linked tree.
 * </p>
 * 
 * <p>
 * The tree built allows full XPath expressions from anywhere on the tree.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 */
public class DocumentFactory implements Serializable
{
    private static SingletonStrategy singleton = null;

    protected transient QNameCache cache;

    /** Default namespace prefix -> URI mappings for XPath expressions to use */
    private Map xpathNamespaceURIs;

    private static SingletonStrategy createSingleton()
    {
        SingletonStrategy result = null;

        String documentFactoryClassName;
        try
        {
            documentFactoryClassName = System.getProperty("com.document.allreader.allofficefilereader.fc.dom4j.factory",
                "com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory");
        }
        catch(Exception e)
        {
            documentFactoryClassName = "com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory";
        }

        try
        {
            String singletonClass = System.getProperty(
                "com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory.singleton.strategy", "com.document.allreader.allofficefilereader.fc.dom4j.util.SimpleSingleton");
            Class clazz = Class.forName(singletonClass);
            result = (SingletonStrategy)clazz.newInstance();
        }
        catch(Exception e)
        {
            result = new SimpleSingleton();
        }

        result.setSingletonClassName(documentFactoryClassName);

        return result;
    }

    public DocumentFactory()
    {
        init();
    }

    /**
     * <p>
     * Access to singleton implementation of DocumentFactory which is used if no
     * DocumentFactory is specified when building using the standard builders.
     * </p>
     * 
     * @return the default singleon instance
     */
    public static synchronized DocumentFactory getInstance()
    {
        if (singleton == null)
        {
            singleton = createSingleton();
        }
        return (DocumentFactory)singleton.instance();
    }

    // Factory methods
    public Document createDocument()
    {
        DefaultDocument answer = new DefaultDocument();
        answer.setDocumentFactory(this);

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param encoding
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @since 1.5
     */
    public Document createDocument(String encoding)
    {
        // to keep the DocumentFactory backwards compatible, we have to do this
        // in this not so nice way, since subclasses only need to extend the
        // createDocument() method.
        Document answer = createDocument();

        if (answer instanceof AbstractDocument)
        {
            ((AbstractDocument)answer).setXMLEncoding(encoding);
        }

        return answer;
    }

    public Document createDocument(Element rootElement)
    {
        Document answer = createDocument();
        answer.setRootElement(rootElement);

        return answer;
    }

    public DocumentType createDocType(String name, String publicId, String systemId)
    {
        return new DefaultDocumentType(name, publicId, systemId);
    }

    public Element createElement(QName qname)
    {
        return new DefaultElement(qname);
    }

    public Element createElement(String name)
    {
        return createElement(createQName(name));
    }

    public Element createElement(String qualifiedName, String namespaceURI)
    {
        return createElement(createQName(qualifiedName, namespaceURI));
    }

    public Attribute createAttribute(Element owner, QName qname, String value)
    {
        return new DefaultAttribute(qname, value);
    }

    public Attribute createAttribute(Element owner, String name, String value)
    {
        return createAttribute(owner, createQName(name), value);
    }

    public CDATA createCDATA(String text)
    {
        return new DefaultCDATA(text);
    }

    public Comment createComment(String text)
    {
        return new DefaultComment(text);
    }

    public Text createText(String text)
    {
        if (text == null)
        {
            String msg = "Adding text to an XML document must not be null";
            throw new IllegalArgumentException(msg);
        }

        return new DefaultText(text);
    }

    public Entity createEntity(String name, String text)
    {
        return new DefaultEntity(name, text);
    }

    public Namespace createNamespace(String prefix, String uri)
    {
        return Namespace.get(prefix, uri);
    }

    public ProcessingInstruction createProcessingInstruction(String target, String data)
    {
        return new DefaultProcessingInstruction(target, data);
    }

    public ProcessingInstruction createProcessingInstruction(String target, Map data)
    {
        return new DefaultProcessingInstruction(target, data);
    }

    public QName createQName(String localName, Namespace namespace)
    {
        return cache.get(localName, namespace);
    }

    public QName createQName(String localName)
    {
        return cache.get(localName);
    }

    public QName createQName(String name, String prefix, String uri)
    {
        return cache.get(name, Namespace.get(prefix, uri));
    }

    public QName createQName(String qualifiedName, String uri)
    {
        return cache.get(qualifiedName, uri);
    }

    /**
     * <p>
     * <code>createXPath</code> parses an XPath expression and creates a new
     * XPath <code>XPath</code> instance.
     * </p>
     * 
     * @param xpathExpression
     *            is the XPath expression to create
     * 
     * @return a new <code>XPath</code> instance
     * 
     * @throws InvalidXPathException
     *             if the XPath expression is invalid
     */
    public XPath createXPath(String xpathExpression) throws InvalidXPathException
    {
        DefaultXPath xpath = new DefaultXPath(xpathExpression);

        if (xpathNamespaceURIs != null)
        {
            xpath.setNamespaceURIs(xpathNamespaceURIs);
        }

        return xpath;
    }


    public NodeFilter createXPathFilter(String xpathFilterExpression)
    {
        return createXPath(xpathFilterExpression);

        // return new DefaultXPath( xpathFilterExpression );
    }

    /**
     * <p>
     * <code>createPattern</code> parses the given XPath expression to create
     * an XSLT style {@link Pattern}instance which can then be used in an XSLT
     * processing model.
     * </p>
     * 
     * @param xpathPattern
     *            is the XPath pattern expression to create
     * 
     * @return a new <code>Pattern</code> instance
     */
    public Pattern createPattern(String xpathPattern)
    {
        return new XPathPattern(xpathPattern);
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * Returns a list of all the QName instances currently used by this document
     * factory
     * 
     * @return DOCUMENT ME!
     */
    public List getQNames()
    {
        return cache.getQNames();
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the Map of namespace URIs that will be used by by XPath
     *         expressions to resolve namespace prefixes into namespace URIs.
     *         The map is keyed by namespace prefix and the value is the
     *         namespace URI. This value could well be null to indicate no
     *         namespace URIs are being mapped.
     */
    public Map getXPathNamespaceURIs()
    {
        return xpathNamespaceURIs;
    }

    /**
     * Sets the namespace URIs to be used by XPath expressions created by this
     * factory or by nodes associated with this factory. The keys are namespace
     * prefixes and the values are namespace URIs.
     * 
     * @param namespaceURIs
     *            DOCUMENT ME!
     */
    public void setXPathNamespaceURIs(Map namespaceURIs)
    {
        this.xpathNamespaceURIs = namespaceURIs;
    }


    // -------------------------------------------------------------------------

    /**
     * <p>
     * <code>createSingleton</code> creates the singleton instance from the
     * given class name.
     * </p>
     * 
     * @param className
     *            is the name of the DocumentFactory class to use
     * 
     * @return a new singleton instance.
     */
    protected static DocumentFactory createSingleton(String className)
    {
        // let's try and class load an implementation?
        try
        {
            // I'll use the current class loader
            // that loaded me to avoid problems in J2EE and web apps
            Class theClass = Class.forName(className, true, DocumentFactory.class.getClassLoader());

            return (DocumentFactory)theClass.newInstance();
        }
        catch(Throwable e)
        {
            System.out.println("WARNING: Cannot load DocumentFactory: " + className);

            return new DocumentFactory();
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @param qname
     *            DOCUMENT ME!
     * 
     * @return the cached QName instance if there is one or adds the given qname
     *         to the cache if not
     */
    protected QName intern(QName qname)
    {
        return cache.intern(qname);
    }

    /**
     * Factory method to create the QNameCache. This method should be overloaded
     * if you wish to use your own derivation of QName.
     * 
     * @return DOCUMENT ME!
     */
    protected QNameCache createQNameCache()
    {
        return new QNameCache(this);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        init();
    }

    protected void init()
    {
        cache = createQNameCache();
    }
}

