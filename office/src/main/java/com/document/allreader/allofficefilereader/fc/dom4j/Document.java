

package com.document.allreader.allofficefilereader.fc.dom4j;

import java.util.Map;

import org.xml.sax.EntityResolver;

/**
 * <p>
 * <code>Document</code> defines an XML Document.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.14 $
 */
public interface Document extends Branch {
    /**
     * Returns the root {@link Element}for this document.
     * 
     * @return the root element for this document
     */
    Element getRootElement();

    /**
     * Sets the root element for this document
     * 
     * @param rootElement
     *            the new root element for this document
     */
    void setRootElement(Element rootElement);

    /**
     * Adds a new <code>Comment</code> node with the given text to this
     * branch.
     * 
     * @param comment
     *            is the text for the <code>Comment</code> node.
     * 
     * @return this <code>Document</code> instance.
     */
    Document addComment(String comment);

    /**
     * Adds a processing instruction for the given target
     * 
     * @param target
     *            is the target of the processing instruction
     * @param text
     *            is the textual data (key/value pairs) of the processing
     *            instruction
     * 
     * @return this <code>Document</code> instance.
     */
    Document addProcessingInstruction(String target, String text);

    /**
     * Adds a processing instruction for the given target
     * 
     * @param target
     *            is the target of the processing instruction
     * @param data
     *            is a Map of the key / value pairs of the processing
     *            instruction
     * 
     * @return this <code>Document</code> instance.
     */
    Document addProcessingInstruction(String target, Map data);

    /**
     * Adds a DOCTYPE declaration to this document
     * 
     * @param name
     *            is the name of the root element
     * @param publicId
     *            is the PUBLIC URI
     * @param systemId
     *            is the SYSTEM URI
     * 
     * @return this <code>Document</code> instance.
     */
    Document addDocType(String name, String publicId, String systemId);

    /**
     * DOCUMENT ME!
     * 
     * @return the DocumentType property
     */
    DocumentType getDocType();

    /**
     * Sets the DocumentType property
     * 
     * @param docType
     *            DOCUMENT ME!
     */
    void setDocType(DocumentType docType);

    /**
     * DOCUMENT ME!
     * 
     * @return the EntityResolver used to find resolve URIs such as for DTDs, or
     *         XML Schema documents
     */
    EntityResolver getEntityResolver();

    /**
     * Sets the EntityResolver used to find resolve URIs such as for DTDs, or
     * XML Schema documents
     * 
     * @param entityResolver
     *            DOCUMENT ME!
     */
    void setEntityResolver(EntityResolver entityResolver);

    /**
     * Return the encoding of this document, as part of the XML declaration This
     * is <code>null</code> when unspecified or when it is not known (such as
     * when the Document was created in memory) or when the implementation does
     * not support this operation.
     * 
     * <p>
     * The way this encoding is retrieved also depends on the way the XML source
     * is parsed. For instance, if the SAXReader is used and if the underlying
     * XMLReader implementation support the
     * <code>org.xml.sax.ext.Locator2</code> interface, the result returned by
     * this method is specified by the <code>getEncoding()</code> method of
     * that interface.
     * </p>
     * 
     * @return The encoding of this document, as stated in the XML declaration,
     *         or <code>null</code> if unknown.
     * 
     * @since 1.5
     */
    String getXMLEncoding();

    /**
     * Sets the encoding of this document as it will appear in the XML
     * declaration part of the document.
     * 
     * @param encoding the encoding of the document
     * 
     * @since 1.6
     */
    void setXMLEncoding(String encoding);
}

