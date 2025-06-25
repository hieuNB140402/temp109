

package com.document.allreader.allofficefilereader.fc.dom4j.io;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementHandler;


/**
 * This extension of the SAXContentHandler writes SAX events immediately to the
 * provided XMLWriter, unless some {@link org.dom4.ElementHandler}is still
 * handling the current Element.
 * 
 * @author Wonne Keysers (Realsoftware.be)
 * 
 * @see org.dom4j.io.SAXContentHandler
 */
class SAXModifyContentHandler extends SAXContentHandler
{
    private XMLWriter xmlWriter;

    public SAXModifyContentHandler()
    {
    }

    public SAXModifyContentHandler(DocumentFactory documentFactory)
    {
        super(documentFactory);
    }

    public SAXModifyContentHandler(DocumentFactory documentFactory, ElementHandler elementHandler)
    {
        super(documentFactory, elementHandler);
    }

    public SAXModifyContentHandler(DocumentFactory documentFactory, ElementHandler elementHandler,
        ElementStack elementStack)
    {
        super(documentFactory, elementHandler, elementStack);
    }

    public void setXMLWriter(XMLWriter writer)
    {
        this.xmlWriter = writer;
    }

    public void startCDATA() throws SAXException
    {
        super.startCDATA();

        if (!activeHandlers() && (xmlWriter != null))
        {
            xmlWriter.startCDATA();
        }
    }

    public void startDTD(String name, String publicId, String systemId) throws SAXException
    {
        super.startDTD(name, publicId, systemId);

        if (xmlWriter != null)
        {
            xmlWriter.startDTD(name, publicId, systemId);
        }
    }

    public void endDTD() throws org.xml.sax.SAXException
    {
        super.endDTD();

        if (xmlWriter != null)
        {
            xmlWriter.endDTD();
        }
    }

    public void comment(char[] characters, int parm2, int parm3) throws SAXException
    {
        super.comment(characters, parm2, parm3);

        if (!activeHandlers() && (xmlWriter != null))
        {
            xmlWriter.comment(characters, parm2, parm3);
        }
    }

    public void startEntity(String name) throws SAXException
    {
        super.startEntity(name);

        if (xmlWriter != null)
        {
            xmlWriter.startEntity(name);
        }
    }

    public void endCDATA() throws org.xml.sax.SAXException
    {
        super.endCDATA();

        if (!activeHandlers() && (xmlWriter != null))
        {
            xmlWriter.endCDATA();
        }
    }

    public void endEntity(String name) throws SAXException
    {
        super.endEntity(name);

        if (xmlWriter != null)
        {
            xmlWriter.endEntity(name);
        }
    }

    public void unparsedEntityDecl(String name, String publicId, String systemId, String notation)
        throws SAXException
    {
        super.unparsedEntityDecl(name, publicId, systemId, notation);

        if (!activeHandlers() && (xmlWriter != null))
        {
            xmlWriter.unparsedEntityDecl(name, publicId, systemId, notation);
        }
    }

    public void notationDecl(String name, String publicId, String systemId) throws SAXException
    {
        super.notationDecl(name, publicId, systemId);

        if (xmlWriter != null)
        {
            xmlWriter.notationDecl(name, publicId, systemId);
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes atts)
        throws SAXException
    {
        super.startElement(uri, localName, qName, atts);

        if (!activeHandlers() && (xmlWriter != null))
        {
            xmlWriter.startElement(uri, localName, qName, atts);
        }
    }

    public void startDocument() throws SAXException
    {
        super.startDocument();

        if (xmlWriter != null)
        {
            xmlWriter.startDocument();
        }
    }

    public void ignorableWhitespace(char[] parm1, int parm2, int parm3) throws SAXException
    {
        super.ignorableWhitespace(parm1, parm2, parm3);

        if (!activeHandlers() && (xmlWriter != null))
        {
            xmlWriter.ignorableWhitespace(parm1, parm2, parm3);
        }
    }

    public void processingInstruction(String target, String data) throws SAXException
    {
        super.processingInstruction(target, data);

        if (!activeHandlers() && (xmlWriter != null))
        {
            xmlWriter.processingInstruction(target, data);
        }
    }

    public void setDocumentLocator(Locator locator)
    {
        super.setDocumentLocator(locator);

        if (xmlWriter != null)
        {
            xmlWriter.setDocumentLocator(locator);
        }
    }

    public void skippedEntity(String name) throws SAXException
    {
        super.skippedEntity(name);

        if (!activeHandlers() && (xmlWriter != null))
        {
            xmlWriter.skippedEntity(name);
        }
    }

    public void endDocument() throws SAXException
    {
        super.endDocument();

        if (xmlWriter != null)
        {
            xmlWriter.endDocument();
        }
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException
    {
        super.startPrefixMapping(prefix, uri);

        if (xmlWriter != null)
        {
            xmlWriter.startPrefixMapping(prefix, uri);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        ElementHandler currentHandler = getElementStack().getDispatchHandler().getHandler(
            getElementStack().getPath());

        super.endElement(uri, localName, qName);

        if (!activeHandlers())
        {
            if (xmlWriter != null)
            {
                if (currentHandler == null)
                {
                    xmlWriter.endElement(uri, localName, qName);
                }
                else if (currentHandler instanceof SAXModifyElementHandler)
                {
                    SAXModifyElementHandler modifyHandler = (SAXModifyElementHandler)currentHandler;
                    Element modifiedElement = modifyHandler.getModifiedElement();

                    try
                    {
                        xmlWriter.write(modifiedElement);
                    }
                    catch(IOException ex)
                    {
                        throw new SAXModifyException(ex);
                    }
                }
            }
        }
    }

    public void endPrefixMapping(String prefix) throws SAXException
    {
        super.endPrefixMapping(prefix);

        if (xmlWriter != null)
        {
            xmlWriter.endPrefixMapping(prefix);
        }
    }

    public void characters(char[] parm1, int parm2, int parm3) throws SAXException
    {
        super.characters(parm1, parm2, parm3);

        if (!activeHandlers() && (xmlWriter != null))
        {
            xmlWriter.characters(parm1, parm2, parm3);
        }
    }

    protected XMLWriter getXMLWriter()
    {
        return this.xmlWriter;
    }

    private boolean activeHandlers()
    {
        DispatchHandler handler = getElementStack().getDispatchHandler();

        return handler.getActiveHandlerCount() > 0;
    }
}

