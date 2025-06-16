

package com.document.allreader.allofficefilereader.fc.dom4j.io;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import com.document.allreader.allofficefilereader.fc.dom4j.Branch;
import com.document.allreader.allofficefilereader.fc.dom4j.Document;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentType;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementHandler;
import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;
import com.document.allreader.allofficefilereader.fc.dom4j.dtd.AttributeDecl;
import com.document.allreader.allofficefilereader.fc.dom4j.dtd.ElementDecl;
import com.document.allreader.allofficefilereader.fc.dom4j.dtd.ExternalEntityDecl;
import com.document.allreader.allofficefilereader.fc.dom4j.dtd.InternalEntityDecl;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.AbstractElement;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.NamespaceStack;


public class SAXContentHandler extends DefaultHandler implements LexicalHandler, DeclHandler,
    DTDHandler
{
    private DocumentFactory documentFactory;


    private Document document;

    private ElementStack elementStack;

    private NamespaceStack namespaceStack;

    private ElementHandler elementHandler;

    private Locator locator;

    private String entity;
    private boolean insideDTDSection;

    private boolean insideCDATASection;

    private StringBuffer cdataText;

    private Map availableNamespaceMap = new HashMap();

    private List declaredNamespaceList = new ArrayList();

    private List internalDTDDeclarations;

    private List externalDTDDeclarations;

    private int declaredNamespaceIndex;

    private EntityResolver entityResolver;

    private InputSource inputSource;

    private Element currentElement;

    private boolean includeInternalDTDDeclarations = false;

    private boolean includeExternalDTDDeclarations = false;

    private int entityLevel;

    private boolean internalDTDsubset = false;

    private boolean mergeAdjacentText = false;

    private boolean textInTextBuffer = false;

    private boolean ignoreComments = false;

    private StringBuffer textBuffer;

    private boolean stripWhitespaceText = false;

    public SAXContentHandler()
    {
        this(DocumentFactory.getInstance());
    }

    public SAXContentHandler(DocumentFactory documentFactory)
    {
        this(documentFactory, null);
    }

    public SAXContentHandler(DocumentFactory documentFactory, ElementHandler elementHandler)
    {
        this(documentFactory, elementHandler, null);
        this.elementStack = createElementStack();
    }

    public SAXContentHandler(DocumentFactory documentFactory, ElementHandler elementHandler,
        ElementStack elementStack)
    {
        this.documentFactory = documentFactory;
        this.elementHandler = elementHandler;
        this.elementStack = elementStack;
        this.namespaceStack = new NamespaceStack(documentFactory);
    }


    public Document getDocument()
    {
        if (document == null)
        {
            document = createDocument();
        }

        return document;
    }

     public void setDocumentLocator(Locator documentLocator)
    {
        this.locator = documentLocator;
    }

    public void processingInstruction(String target, String data) throws SAXException
    {
        if (mergeAdjacentText && textInTextBuffer)
        {
            completeCurrentTextNode();
        }

        if (currentElement != null)
        {
            currentElement.addProcessingInstruction(target, data);
        }
        else
        {
            getDocument().addProcessingInstruction(target, data);
        }
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException
    {
        namespaceStack.push(prefix, uri);
    }

    public void endPrefixMapping(String prefix) throws SAXException
    {
        namespaceStack.pop(prefix);
        declaredNamespaceIndex = namespaceStack.size();
    }

    public void startDocument() throws SAXException
    {
        // document = createDocument();
        document = null;
        currentElement = null;

        elementStack.clear();

        if ((elementHandler != null) && (elementHandler instanceof DispatchHandler))
        {
            elementStack.setDispatchHandler((DispatchHandler)elementHandler);
        }

        namespaceStack.clear();
        declaredNamespaceIndex = 0;

        if (mergeAdjacentText && (textBuffer == null))
        {
            textBuffer = new StringBuffer();
        }

        textInTextBuffer = false;
    }

    public void endDocument() throws SAXException
    {
        namespaceStack.clear();
        elementStack.clear();
        currentElement = null;
        textBuffer = null;
    }

    public void startElement(String namespaceURI, String localName, String qualifiedName,
        Attributes attributes) throws SAXException
    {
        if (mergeAdjacentText && textInTextBuffer)
        {
            completeCurrentTextNode();
        }

        QName qName = namespaceStack.getQName(namespaceURI, localName, qualifiedName);

        Branch branch = currentElement;

        if (branch == null)
        {
            branch = getDocument();
        }

        Element element = branch.addElement(qName);

        addDeclaredNamespaces(element);

        addAttributes(element, attributes);

        elementStack.pushElement(element);
        currentElement = element;

        entity = null; // fixes bug527062

        if (elementHandler != null)
        {
            elementHandler.onStart(elementStack);
        }
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException
    {
        if (mergeAdjacentText && textInTextBuffer)
        {
            completeCurrentTextNode();
        }

        if ((elementHandler != null) && (currentElement != null))
        {
            elementHandler.onEnd(elementStack);
        }

        elementStack.popElement();
        currentElement = elementStack.peekElement();
    }

    public void characters(char[] ch, int start, int end) throws SAXException
    {
        if (end == 0)
        {
            return;
        }

        if (currentElement != null)
        {
            if (entity != null)
            {
                if (mergeAdjacentText && textInTextBuffer)
                {
                    completeCurrentTextNode();
                }

                currentElement.addEntity(entity, new String(ch, start, end));
                entity = null;
            }
            else if (insideCDATASection)
            {
                if (mergeAdjacentText && textInTextBuffer)
                {
                    completeCurrentTextNode();
                }

                cdataText.append(new String(ch, start, end));
            }
            else
            {
                if (mergeAdjacentText)
                {
                    textBuffer.append(ch, start, end);
                    textInTextBuffer = true;
                }
                else
                {
                    currentElement.addText(new String(ch, start, end));
                }
            }
        }
    }

    // ErrorHandler interface
    // -------------------------------------------------------------------------

    /**
     * This method is called when a warning occurs during the parsing of the
     * document. This method does nothing.
     * 
     * @param exception
     *            DOCUMENT ME!
     * 
     * @throws SAXException
     *             DOCUMENT ME!
     */
    public void warning(SAXParseException exception) throws SAXException
    {
        // ignore warnings by default
    }

    /**
     * This method is called when an error is detected during parsing such as a
     * validation error. This method rethrows the exception
     * 
     * @param exception
     *            DOCUMENT ME!
     * 
     * @throws SAXException
     *             DOCUMENT ME!
     */
    public void error(SAXParseException exception) throws SAXException
    {
        throw exception;
    }

    /**
     * This method is called when a fatal error occurs during parsing. This
     * method rethrows the exception
     * 
     * @param exception
     *            DOCUMENT ME!
     * 
     * @throws SAXException
     *             DOCUMENT ME!
     */
    public void fatalError(SAXParseException exception) throws SAXException
    {
        throw exception;
    }

    // LexicalHandler interface
    // -------------------------------------------------------------------------
    public void startDTD(String name, String publicId, String systemId) throws SAXException
    {
        getDocument().addDocType(name, publicId, systemId);
        insideDTDSection = true;
        internalDTDsubset = true;
    }

    public void endDTD() throws SAXException
    {
        insideDTDSection = false;

        DocumentType docType = getDocument().getDocType();

        if (docType != null)
        {
            if (internalDTDDeclarations != null)
            {
                docType.setInternalDeclarations(internalDTDDeclarations);
            }

            if (externalDTDDeclarations != null)
            {
                docType.setExternalDeclarations(externalDTDDeclarations);
            }
        }

        internalDTDDeclarations = null;
        externalDTDDeclarations = null;
    }

    public void startEntity(String name) throws SAXException
    {
        ++entityLevel;

        entity = null;

        if (!insideDTDSection)
        {
            if (!isIgnorableEntity(name))
            {
                entity = name;
            }
        }

        internalDTDsubset = false;
    }

    public void endEntity(String name) throws SAXException
    {
        --entityLevel;
        entity = null;

        if (entityLevel == 0)
        {
            internalDTDsubset = true;
        }
    }

    public void startCDATA() throws SAXException
    {
        insideCDATASection = true;
        cdataText = new StringBuffer();
    }

    public void endCDATA() throws SAXException
    {
        insideCDATASection = false;
        currentElement.addCDATA(cdataText.toString());
    }

    public void comment(char[] ch, int start, int end) throws SAXException
    {
        if (!ignoreComments)
        {
            if (mergeAdjacentText && textInTextBuffer)
            {
                completeCurrentTextNode();
            }

            String text = new String(ch, start, end);

            if (!insideDTDSection && (text.length() > 0))
            {
                if (currentElement != null)
                {
                    currentElement.addComment(text);
                }
                else
                {
                    getDocument().addComment(text);
                }
            }
        }
    }

    public void elementDecl(String name, String model) throws SAXException
    {
        if (internalDTDsubset)
        {
            if (includeInternalDTDDeclarations)
            {
                addDTDDeclaration(new ElementDecl(name, model));
            }
        }
        else
        {
            if (includeExternalDTDDeclarations)
            {
                addExternalDTDDeclaration(new ElementDecl(name, model));
            }
        }
    }


    public void attributeDecl(String eName, String aName, String type, String valueDefault,
        String val) throws SAXException
    {
        if (internalDTDsubset)
        {
            if (includeInternalDTDDeclarations)
            {
                addDTDDeclaration(new AttributeDecl(eName, aName, type, valueDefault, val));
            }
        }
        else
        {
            if (includeExternalDTDDeclarations)
            {
                addExternalDTDDeclaration(new AttributeDecl(eName, aName, type, valueDefault, val));
            }
        }
    }


    public void internalEntityDecl(String name, String value) throws SAXException
    {
        if (internalDTDsubset)
        {
            if (includeInternalDTDDeclarations)
            {
                addDTDDeclaration(new InternalEntityDecl(name, value));
            }
        }
        else
        {
            if (includeExternalDTDDeclarations)
            {
                addExternalDTDDeclaration(new InternalEntityDecl(name, value));
            }
        }
    }


    public void externalEntityDecl(String name, String publicId, String sysId) throws SAXException
    {
        ExternalEntityDecl declaration = new ExternalEntityDecl(name, publicId, sysId);

        if (internalDTDsubset)
        {
            if (includeInternalDTDDeclarations)
            {
                addDTDDeclaration(declaration);
            }
        }
        else
        {
            if (includeExternalDTDDeclarations)
            {
                addExternalDTDDeclaration(declaration);
            }
        }
    }

    public void notationDecl(String name, String publicId, String systemId) throws SAXException
    {
        // #### not supported yet!
    }


    public void unparsedEntityDecl(String name, String publicId, String systemId,
        String notationName) throws SAXException
    {

    }


    public ElementStack getElementStack()
    {
        return elementStack;
    }

    public void setElementStack(ElementStack elementStack)
    {
        this.elementStack = elementStack;
    }

    public EntityResolver getEntityResolver()
    {
        return entityResolver;
    }

    public void setEntityResolver(EntityResolver entityResolver)
    {
        this.entityResolver = entityResolver;
    }

    public InputSource getInputSource()
    {
        return inputSource;
    }

    public void setInputSource(InputSource inputSource)
    {
        this.inputSource = inputSource;
    }


    public boolean isIncludeInternalDTDDeclarations()
    {
        return includeInternalDTDDeclarations;
    }


    public void setIncludeInternalDTDDeclarations(boolean include)
    {
        this.includeInternalDTDDeclarations = include;
    }

    public boolean isIncludeExternalDTDDeclarations()
    {
        return includeExternalDTDDeclarations;
    }


    public void setIncludeExternalDTDDeclarations(boolean include)
    {
        this.includeExternalDTDDeclarations = include;
    }


    public boolean isMergeAdjacentText()
    {
        return mergeAdjacentText;
    }


    public void setMergeAdjacentText(boolean mergeAdjacentText)
    {
        this.mergeAdjacentText = mergeAdjacentText;
    }

    public boolean isStripWhitespaceText()
    {
        return stripWhitespaceText;
    }


    public void setStripWhitespaceText(boolean stripWhitespaceText)
    {
        this.stripWhitespaceText = stripWhitespaceText;
    }


    public boolean isIgnoreComments()
    {
        return ignoreComments;
    }


    public void setIgnoreComments(boolean ignoreComments)
    {
        this.ignoreComments = ignoreComments;
    }



    protected void completeCurrentTextNode()
    {
        if (stripWhitespaceText)
        {
            boolean whitespace = true;

            for (int i = 0, size = textBuffer.length(); i < size; i++)
            {
                if (!Character.isWhitespace(textBuffer.charAt(i)))
                {
                    whitespace = false;

                    break;
                }
            }

            if (!whitespace)
            {
                currentElement.addText(textBuffer.toString());
            }
        }
        else
        {
            currentElement.addText(textBuffer.toString());
        }

        textBuffer.setLength(0);
        textInTextBuffer = false;
    }


    protected Document createDocument()
    {
        String encoding = getEncoding();
        Document doc = documentFactory.createDocument(encoding);

        // set the EntityResolver
        doc.setEntityResolver(entityResolver);

        if (inputSource != null)
        {
            doc.setName(inputSource.getSystemId());
        }

        return doc;
    }

    private String getEncoding()
    {
        if (locator == null)
        {
            return null;
        }

        try
        {
            Method m = locator.getClass().getMethod("getEncoding", new Class[]{});

            if (m != null)
            {
                return (String)m.invoke(locator, null);
            }
        }
        catch(Exception e)
        {
        }

        return null;
    }


    protected boolean isIgnorableEntity(String name)
    {
        return "amp".equals(name) || "apos".equals(name) || "gt".equals(name) || "lt".equals(name)
            || "quot".equals(name);
    }


    protected void addDeclaredNamespaces(Element element)
    {
        Namespace elementNamespace = element.getNamespace();

        for (int size = namespaceStack.size(); declaredNamespaceIndex < size; declaredNamespaceIndex++)
        {
            Namespace namespace = namespaceStack.getNamespace(declaredNamespaceIndex);

            element.add(namespace);

        }
    }


    protected void addAttributes(Element element, Attributes attributes)
    {

        boolean noNamespaceAttributes = false;

        if (element instanceof AbstractElement)
        {
            // optimised method
            AbstractElement baseElement = (AbstractElement)element;
            baseElement.setAttributes(attributes, namespaceStack, noNamespaceAttributes);
        }
        else
        {
            int size = attributes.getLength();

            for (int i = 0; i < size; i++)
            {
                String attributeQName = attributes.getQName(i);

                if (noNamespaceAttributes || !attributeQName.startsWith("xmlns"))
                {
                    String attributeURI = attributes.getURI(i);
                    String attributeLocalName = attributes.getLocalName(i);
                    String attributeValue = attributes.getValue(i);

                    QName qName = namespaceStack.getAttributeQName(attributeURI,
                        attributeLocalName, attributeQName);
                    element.addAttribute(qName, attributeValue);
                }
            }
        }
    }


    protected void addDTDDeclaration(Object declaration)
    {
        if (internalDTDDeclarations == null)
        {
            internalDTDDeclarations = new ArrayList();
        }

        internalDTDDeclarations.add(declaration);
    }


    protected void addExternalDTDDeclaration(Object declaration)
    {
        if (externalDTDDeclarations == null)
        {
            externalDTDDeclarations = new ArrayList();
        }

        externalDTDDeclarations.add(declaration);
    }

    protected ElementStack createElementStack()
    {
        return new ElementStack();
    }
}

