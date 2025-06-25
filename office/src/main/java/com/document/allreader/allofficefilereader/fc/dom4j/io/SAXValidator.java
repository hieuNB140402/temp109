

package com.document.allreader.allofficefilereader.fc.dom4j.io;

import java.io.IOException;

import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.document.allreader.allofficefilereader.fc.dom4j.Document;


/**
 * <p>
 * <code>SAXValidator</code> validates an XML document by writing the document
 * to a text buffer and parsing it with a validating SAX parser. This could be
 * implemented much more efficiently by validating against the dom4j object
 * model directly but at least allows the reuse of existing SAX based validating
 * parsers.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
public class SAXValidator
{
    /** <code>XMLReader</code> used to parse the SAX events */
    private XMLReader xmlReader;

    /** ErrorHandler class to use */
    private ErrorHandler errorHandler;

    public SAXValidator()
    {
    }

    public SAXValidator(XMLReader xmlReader)
    {
        this.xmlReader = xmlReader;
    }

    /**
     * Validates the given <code>Document</code> by writing it to a validating
     * SAX Parser.
     * 
     * @param document
     *            is the Document to validate
     * 
     * @throws SAXException
     *             if a validation error occurs
     * @throws RuntimeException
     *             DOCUMENT ME!
     */
    public void validate(Document document) throws SAXException
    {
        if (document != null)
        {
            XMLReader reader = getXMLReader();

            if (errorHandler != null)
            {
                reader.setErrorHandler(errorHandler);
            }

            try
            {
                reader.parse(new DocumentInputSource(document));
            }
            catch(IOException e)
            {
                throw new RuntimeException("Caught and exception that should " + "never happen: "
                    + e);
            }
        }
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     * 
     * @return the <code>XMLReader</code> used to parse SAX events
     * 
     * @throws SAXException
     *             DOCUMENT ME!
     */
    public XMLReader getXMLReader() throws SAXException
    {
        if (xmlReader == null)
        {
            xmlReader = createXMLReader();
            configureReader();
        }

        return xmlReader;
    }

    /**
     * Sets the <code>XMLReader</code> used to parse SAX events
     * 
     * @param reader
     *            is the <code>XMLReader</code> to parse SAX events
     * 
     * @throws SAXException
     *             DOCUMENT ME!
     */
    public void setXMLReader(XMLReader reader) throws SAXException
    {
        this.xmlReader = reader;
        configureReader();
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the <code>ErrorHandler</code> used by SAX
     */
    public ErrorHandler getErrorHandler()
    {
        return errorHandler;
    }

    /**
     * Sets the <code>ErrorHandler</code> used by the SAX
     * <code>XMLReader</code>.
     * 
     * @param errorHandler
     *            is the <code>ErrorHandler</code> used by SAX
     */
    public void setErrorHandler(ErrorHandler errorHandler)
    {
        this.errorHandler = errorHandler;
    }


    // -------------------------------------------------------------------------

    /**
     * Factory Method to allow alternate methods of creating and configuring
     * XMLReader objects
     * 
     * @return DOCUMENT ME!
     * 
     * @throws SAXException
     *             DOCUMENT ME!
     */
    protected XMLReader createXMLReader() throws SAXException
    {
        return SAXHelper.createXMLReader(true);
    }

    /**
     * Configures the XMLReader before use
     * 
     * @throws SAXException
     *             DOCUMENT ME!
     */
    protected void configureReader() throws SAXException
    {
        ContentHandler handler = xmlReader.getContentHandler();

        if (handler == null)
        {
            xmlReader.setContentHandler(new DefaultHandler());
        }

        // configure validation support
        xmlReader.setFeature("http://xml.org/sax/features/validation", true);

        // configure namespace support
        xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
        xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
    }
}

