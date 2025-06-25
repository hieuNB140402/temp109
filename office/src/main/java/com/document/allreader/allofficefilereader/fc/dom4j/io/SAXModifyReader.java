

package com.document.allreader.allofficefilereader.fc.dom4j.io;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;


/**
 * The SAXModifier parses, updates and writes an XML document. <br>
 * The input that is parsed is directly written to the specified output, unless
 * the current xml element has an associated ElementHandler. <br>
 * The {@link org.dom4j.ElementHandler}objects make it possible to update the
 * document on the fly, without having read tje complete document.
 * 
 * @author Wonne Keysers (Realsoftware.be)
 * 
 * @see org.dom4j.io.SAXReader
 * @see org.dom4j.io.XMLWriters
 */
class SAXModifyReader extends SAXReader
{
    private XMLWriter xmlWriter;

    private boolean pruneElements;

    public SAXModifyReader()
    {
    }

    public SAXModifyReader(boolean validating)
    {
        super(validating);
    }

    public SAXModifyReader(DocumentFactory factory)
    {
        super(factory);
    }

    public SAXModifyReader(DocumentFactory factory, boolean validating)
    {
        super(factory, validating);
    }

    public SAXModifyReader(XMLReader xmlReader)
    {
        super(xmlReader);
    }

    public SAXModifyReader(XMLReader xmlReader, boolean validating)
    {
        super(xmlReader, validating);
    }

    public SAXModifyReader(String xmlReaderClassName) throws SAXException
    {
        super(xmlReaderClassName);
    }

    public SAXModifyReader(String xmlReaderClassName, boolean validating) throws SAXException
    {
        super(xmlReaderClassName, validating);
    }

    public void setXMLWriter(XMLWriter writer)
    {
        this.xmlWriter = writer;
    }

    public boolean isPruneElements()
    {
        return pruneElements;
    }

    public void setPruneElements(boolean pruneElements)
    {
        this.pruneElements = pruneElements;
    }

    protected SAXContentHandler createContentHandler(XMLReader reader)
    {
        SAXModifyContentHandler handler = new SAXModifyContentHandler(getDocumentFactory(),
            getDispatchHandler());

        handler.setXMLWriter(xmlWriter);

        return handler;
    }

    protected XMLWriter getXMLWriter()
    {
        return this.xmlWriter;
    }
}

