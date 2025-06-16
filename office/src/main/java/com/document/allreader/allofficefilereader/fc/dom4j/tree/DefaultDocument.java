

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.EntityResolver;

import com.document.allreader.allofficefilereader.fc.dom4j.Document;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentType;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.IllegalAddException;
import com.document.allreader.allofficefilereader.fc.dom4j.Node;
import com.document.allreader.allofficefilereader.fc.dom4j.ProcessingInstruction;


/**
 * <p>
 * <code>DefaultDocument</code> is the default DOM4J default implementation of
 * an XML document.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.34 $
 */
public class DefaultDocument extends AbstractDocument
{
    protected static final List EMPTY_LIST = Collections.EMPTY_LIST;

    protected static final Iterator EMPTY_ITERATOR = EMPTY_LIST.iterator();

    /** The name of the document */
    private String name;

    /** The root element of this document */
    private Element rootElement;

    /**
     * Store the contents of the document as a lazily created <code>List</code>
     */
    private List content;

    /** The document type for this document */
    private DocumentType docType;

    /** The document factory used by default */
    private DocumentFactory documentFactory = DocumentFactory.getInstance();

    /** The resolver of URIs */
    private transient EntityResolver entityResolver;

    public DefaultDocument()
    {
    }

    public DefaultDocument(String name)
    {
        this.name = name;
    }

    public DefaultDocument(Element rootElement)
    {
        this.rootElement = rootElement;
    }

    public DefaultDocument(DocumentType docType)
    {
        this.docType = docType;
    }

    public DefaultDocument(Element rootElement, DocumentType docType)
    {
        this.rootElement = rootElement;
        this.docType = docType;
    }

    public DefaultDocument(String name, Element rootElement, DocumentType docType)
    {
        this.name = name;
        this.rootElement = rootElement;
        this.docType = docType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Element getRootElement()
    {
        return rootElement;
    }

    public DocumentType getDocType()
    {
        return docType;
    }

    public void setDocType(DocumentType docType)
    {
        this.docType = docType;
    }

    public Document addDocType(String docTypeName, String publicId, String systemId)
    {
        setDocType(getDocumentFactory().createDocType(docTypeName, publicId, systemId));

        return this;
    }

    public String getXMLEncoding()
    {
        return encoding;
    }

    public EntityResolver getEntityResolver()
    {
        return entityResolver;
    }

    public void setEntityResolver(EntityResolver entityResolver)
    {
        this.entityResolver = entityResolver;
    }

    public Object clone()
    {
        DefaultDocument document = (DefaultDocument)super.clone();
        document.rootElement = null;
        document.content = null;
        document.appendContent(this);

        return document;
    }

    public List processingInstructions()
    {
        List source = contentList();
        List answer = createResultList();
        int size = source.size();

        for (int i = 0; i < size; i++)
        {
            Object object = source.get(i);

            if (object instanceof ProcessingInstruction)
            {
                answer.add(object);
            }
        }

        return answer;
    }

    public List processingInstructions(String target)
    {
        List source = contentList();
        List answer = createResultList();
        int size = source.size();

        for (int i = 0; i < size; i++)
        {
            Object object = source.get(i);

            if (object instanceof ProcessingInstruction)
            {
                ProcessingInstruction pi = (ProcessingInstruction)object;

                if (target.equals(pi.getName()))
                {
                    answer.add(pi);
                }
            }
        }

        return answer;
    }

    public ProcessingInstruction processingInstruction(String target)
    {
        List source = contentList();
        int size = source.size();

        for (int i = 0; i < size; i++)
        {
            Object object = source.get(i);

            if (object instanceof ProcessingInstruction)
            {
                ProcessingInstruction pi = (ProcessingInstruction)object;

                if (target.equals(pi.getName()))
                {
                    return pi;
                }
            }
        }

        return null;
    }

    public boolean removeProcessingInstruction(String target)
    {
        List source = contentList();

        for (Iterator iter = source.iterator(); iter.hasNext();)
        {
            Object object = iter.next();

            if (object instanceof ProcessingInstruction)
            {
                ProcessingInstruction pi = (ProcessingInstruction)object;

                if (target.equals(pi.getName()))
                {
                    iter.remove();

                    return true;
                }
            }
        }

        return false;
    }

    public void setContent(List content)
    {
        rootElement = null;
        contentRemoved();

        if (content instanceof ContentListFacade)
        {
            content = ((ContentListFacade)content).getBackingList();
        }

        if (content == null)
        {
            this.content = null;
        }
        else
        {
            int size = content.size();
            List newContent = createContentList(size);

            for (int i = 0; i < size; i++)
            {
                Object object = content.get(i);

                if (object instanceof Node)
                {
                    Node node = (Node)object;
                    Document doc = node.getDocument();

                    if ((doc != null) && (doc != this))
                    {
                        node = (Node)node.clone();
                    }

                    if (node instanceof Element)
                    {
                        if (rootElement == null)
                        {
                            rootElement = (Element)node;
                        }
                        else
                        {
                            throw new IllegalAddException("A document may only "
                                + "contain one root " + "element: " + content);
                        }
                    }

                    newContent.add(node);
                    childAdded(node);
                }
            }

            this.content = newContent;
        }
    }

    public void clearContent()
    {
        contentRemoved();
        content = null;
        rootElement = null;
    }

    public void setDocumentFactory(DocumentFactory documentFactory)
    {
        this.documentFactory = documentFactory;
    }


    // -------------------------------------------------------------------------
    protected List contentList()
    {
        if (content == null)
        {
            content = createContentList();

            if (rootElement != null)
            {
                content.add(rootElement);
            }
        }

        return content;
    }

    protected void addNode(Node node)
    {
        if (node != null)
        {
            Document document = node.getDocument();

            if ((document != null) && (document != this))
            {
                // XXX: could clone here
                String message = "The Node already has an existing document: " + document;
                throw new IllegalAddException(this, node, message);
            }

            contentList().add(node);
            childAdded(node);
        }
    }

    protected void addNode(int index, Node node)
    {
        if (node != null)
        {
            Document document = node.getDocument();

            if ((document != null) && (document != this))
            {
                // XXX: could clone here
                String message = "The Node already has an existing document: " + document;
                throw new IllegalAddException(this, node, message);
            }

            contentList().add(index, node);
            childAdded(node);
        }
    }

    protected boolean removeNode(Node node)
    {
        if (node == rootElement)
        {
            rootElement = null;
        }

        if (contentList().remove(node))
        {
            childRemoved(node);

            return true;
        }

        return false;
    }

    protected void rootElementAdded(Element element)
    {
        this.rootElement = element;
        element.setDocument(this);
    }

    protected DocumentFactory getDocumentFactory()
    {
        return documentFactory;
    }
}

