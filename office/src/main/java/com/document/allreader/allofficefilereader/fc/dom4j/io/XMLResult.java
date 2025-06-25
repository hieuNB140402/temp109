

package com.document.allreader.allofficefilereader.fc.dom4j.io;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.transform.sax.SAXResult;

import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;

/**
 * <p>
 * <code>XMLResult</code> implements a JAXP {@link SAXResult}for an output
 * stream with support for pretty printing and control over how the XML is
 * formatted.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class XMLResult extends SAXResult {
    private XMLWriter xmlWriter;

    public XMLResult() {
        this(new XMLWriter());
    }

    public XMLResult(Writer writer) {
        this(new XMLWriter(writer));
    }

    public XMLResult(Writer writer, OutputFormat format) {
        this(new XMLWriter(writer, format));
    }

    public XMLResult(OutputStream out) throws UnsupportedEncodingException {
        this(new XMLWriter(out));
    }

    public XMLResult(OutputStream out, OutputFormat format)
            throws UnsupportedEncodingException {
        this(new XMLWriter(out, format));
    }

    public XMLResult(XMLWriter xmlWriter) {
        super(xmlWriter);
        this.xmlWriter = xmlWriter;
        setLexicalHandler(xmlWriter);
    }

    public XMLWriter getXMLWriter() {
        return xmlWriter;
    }

    public void setXMLWriter(XMLWriter writer) {
        this.xmlWriter = writer;
        setHandler(xmlWriter);
        setLexicalHandler(xmlWriter);
    }

    public ContentHandler getHandler() {
        return xmlWriter;
    }

    public LexicalHandler getLexicalHandler() {
        return xmlWriter;
    }
}

