

package com.document.allreader.allofficefilereader.fc.dom4j.dom;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

import com.document.allreader.allofficefilereader.fc.dom4j.Attribute;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultElement;



public class DOMElement extends DefaultElement implements org.w3c.dom.Element
{

    private static final DocumentFactory DOCUMENT_FACTORY = DOMDocumentFactory.getInstance();

    public DOMElement(String name)
    {
        super(name);
    }

    public DOMElement(QName qname)
    {
        super(qname);
    }

    public DOMElement(QName qname, int attributeCount)
    {
        super(qname, attributeCount);
    }

    public DOMElement(String name, Namespace namespace)
    {
        super(name, namespace);
    }


    public boolean supports(String feature, String version)
    {
        return DOMNodeHelper.supports(this, feature, version);
    }

    public String getNamespaceURI()
    {
        return getQName().getNamespaceURI();
    }

    public String getPrefix()
    {
        return getQName().getNamespacePrefix();
    }

    public void setPrefix(String prefix) throws DOMException
    {
        DOMNodeHelper.setPrefix(this, prefix);
    }

    public String getLocalName()
    {
        return getQName().getName();
    }

    public String getNodeName()
    {
        return getName();
    }


    public String getNodeValue() throws DOMException
    {
        return null;
    }

    public void setNodeValue(String nodeValue) throws DOMException
    {
    }

    public org.w3c.dom.Node getParentNode()
    {
        return DOMNodeHelper.getParentNode(this);
    }

    public NodeList getChildNodes()
    {
        return DOMNodeHelper.createNodeList(content());
    }

    public org.w3c.dom.Node getFirstChild()
    {
        return DOMNodeHelper.asDOMNode(node(0));
    }

    public org.w3c.dom.Node getLastChild()
    {
        return DOMNodeHelper.asDOMNode(node(nodeCount() - 1));
    }

    public org.w3c.dom.Node getPreviousSibling()
    {
        return DOMNodeHelper.getPreviousSibling(this);
    }

    public org.w3c.dom.Node getNextSibling()
    {
        return DOMNodeHelper.getNextSibling(this);
    }

    public NamedNodeMap getAttributes()
    {
        return new DOMAttributeNodeMap(this);
    }

    public Document getOwnerDocument()
    {
        return DOMNodeHelper.getOwnerDocument(this);
    }

    public org.w3c.dom.Node insertBefore(org.w3c.dom.Node newChild, org.w3c.dom.Node refChild)
        throws DOMException
    {
        checkNewChildNode(newChild);

        return DOMNodeHelper.insertBefore(this, newChild, refChild);
    }

    public org.w3c.dom.Node replaceChild(org.w3c.dom.Node newChild, org.w3c.dom.Node oldChild)
        throws DOMException
    {
        checkNewChildNode(newChild);

        return DOMNodeHelper.replaceChild(this, newChild, oldChild);
    }

    public org.w3c.dom.Node removeChild(org.w3c.dom.Node oldChild) throws DOMException
    {
        return DOMNodeHelper.removeChild(this, oldChild);
    }

    public org.w3c.dom.Node appendChild(org.w3c.dom.Node newChild) throws DOMException
    {
        checkNewChildNode(newChild);

        return DOMNodeHelper.appendChild(this, newChild);
    }

    private void checkNewChildNode(org.w3c.dom.Node newChild) throws DOMException
    {
        final int nodeType = newChild.getNodeType();

        if (!((nodeType == Node.ELEMENT_NODE) || (nodeType == Node.TEXT_NODE)
            || (nodeType == Node.COMMENT_NODE) || (nodeType == Node.PROCESSING_INSTRUCTION_NODE)
            || (nodeType == Node.CDATA_SECTION_NODE) || (nodeType == Node.ENTITY_REFERENCE_NODE)))
        {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                "Given node cannot be a child of element");
        }
    }

    public boolean hasChildNodes()
    {
        return nodeCount() > 0;
    }

    public org.w3c.dom.Node cloneNode(boolean deep)
    {
        return DOMNodeHelper.cloneNode(this, deep);
    }

    public boolean isSupported(String feature, String version)
    {
        return DOMNodeHelper.isSupported(this, feature, version);
    }

    public boolean hasAttributes()
    {
        return DOMNodeHelper.hasAttributes(this);
    }


    public String getTagName()
    {
        return getName();
    }

    public String getAttribute(String name)
    {
        String answer = attributeValue(name);

        return (answer != null) ? answer : "";
    }

    public void setAttribute(String name, String value) throws DOMException
    {
        addAttribute(name, value);
    }

    public void removeAttribute(String name) throws DOMException
    {
        Attribute attribute = attribute(name);

        if (attribute != null)
        {
            remove(attribute);
        }
    }

    public org.w3c.dom.Attr getAttributeNode(String name)
    {
        return DOMNodeHelper.asDOMAttr(attribute(name));
    }

    public org.w3c.dom.Attr setAttributeNode(org.w3c.dom.Attr newAttr) throws DOMException
    {
        if (this.isReadOnly())
        {
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR,
                "No modification allowed");
        }

        Attribute attribute = attribute(newAttr);

        if (attribute != newAttr)
        {
            if (newAttr.getOwnerElement() != null)
            {
                throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR,
                    "Attribute is already in use");
            }

            Attribute newAttribute = createAttribute(newAttr);

            if (attribute != null)
            {
                attribute.detach();
            }

            add(newAttribute);
        }

        return DOMNodeHelper.asDOMAttr(attribute);
    }

    public org.w3c.dom.Attr removeAttributeNode(org.w3c.dom.Attr oldAttr) throws DOMException
    {
        Attribute attribute = attribute(oldAttr);

        if (attribute != null)
        {
            attribute.detach();

            return DOMNodeHelper.asDOMAttr(attribute);
        }
        else
        {
            throw new DOMException(DOMException.NOT_FOUND_ERR, "No such attribute");
        }
    }

    public String getAttributeNS(String namespaceURI, String localName)
    {
        Attribute attribute = attribute(namespaceURI, localName);

        if (attribute != null)
        {
            String answer = attribute.getValue();

            if (answer != null)
            {
                return answer;
            }
        }

        return "";
    }

    public void setAttributeNS(String namespaceURI, String qualifiedName, String value)
        throws DOMException
    {
        Attribute attribute = attribute(namespaceURI, qualifiedName);

        if (attribute != null)
        {
            attribute.setValue(value);
        }
        else
        {
            QName qname = getQName(namespaceURI, qualifiedName);
            addAttribute(qname, value);
        }
    }

    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException
    {
        Attribute attribute = attribute(namespaceURI, localName);

        if (attribute != null)
        {
            remove(attribute);
        }
    }

    public org.w3c.dom.Attr getAttributeNodeNS(String namespaceURI, String localName)
    {
        Attribute attribute = attribute(namespaceURI, localName);

        if (attribute != null)
        {
            DOMNodeHelper.asDOMAttr(attribute);
        }

        return null;
    }

    public org.w3c.dom.Attr setAttributeNodeNS(org.w3c.dom.Attr newAttr) throws DOMException
    {
        Attribute attribute = attribute(newAttr.getNamespaceURI(), newAttr.getLocalName());

        if (attribute != null)
        {
            attribute.setValue(newAttr.getValue());
        }
        else
        {
            attribute = createAttribute(newAttr);
            add(attribute);
        }

        return DOMNodeHelper.asDOMAttr(attribute);
    }

    public NodeList getElementsByTagName(String name)
    {
        ArrayList list = new ArrayList();
        DOMNodeHelper.appendElementsByTagName(list, this, name);

        return DOMNodeHelper.createNodeList(list);
    }

    public NodeList getElementsByTagNameNS(String namespace, String lName)
    {
        ArrayList list = new ArrayList();
        DOMNodeHelper.appendElementsByTagNameNS(list, this, namespace, lName);

        return DOMNodeHelper.createNodeList(list);
    }

    public boolean hasAttribute(String name)
    {
        return attribute(name) != null;
    }

    public boolean hasAttributeNS(String namespaceURI, String localName)
    {
        return attribute(namespaceURI, localName) != null;
    }


    // -------------------------------------------------------------------------
    protected DocumentFactory getDocumentFactory()
    {
        DocumentFactory factory = getQName().getDocumentFactory();

        return (factory != null) ? factory : DOCUMENT_FACTORY;
    }

    protected Attribute attribute(org.w3c.dom.Attr attr)
    {
        return attribute(DOCUMENT_FACTORY.createQName(attr.getLocalName(), attr.getPrefix(),
            attr.getNamespaceURI()));
    }

    protected Attribute attribute(String namespaceURI, String localName)
    {
        List attributes = attributeList();
        int size = attributes.size();

        for (int i = 0; i < size; i++)
        {
            Attribute attribute = (Attribute)attributes.get(i);

            if (localName.equals(attribute.getName())
                && (((namespaceURI == null || namespaceURI.length() == 0) && ((attribute
                    .getNamespaceURI() == null) || (attribute.getNamespaceURI().length() == 0))) || ((namespaceURI != null) && namespaceURI
                    .equals(attribute.getNamespaceURI()))))
            {
                return attribute;
            }
        }

        return null;
    }

    protected Attribute createAttribute(org.w3c.dom.Attr newAttr)
    {
        QName qname = null;
        String name = newAttr.getLocalName();

        if (name != null)
        {
            String prefix = newAttr.getPrefix();
            String uri = newAttr.getNamespaceURI();
            qname = getDocumentFactory().createQName(name, prefix, uri);
        }
        else
        {
            name = newAttr.getName();
            qname = getDocumentFactory().createQName(name);
        }

        return new DOMAttribute(qname, newAttr.getValue());
    }

    protected QName getQName(String namespace, String qualifiedName)
    {
        int index = qualifiedName.indexOf(':');
        String prefix = "";
        String localName = qualifiedName;

        if (index >= 0)
        {
            prefix = qualifiedName.substring(0, index);
            localName = qualifiedName.substring(index + 1);
        }

        return getDocumentFactory().createQName(localName, prefix, namespace);
    }

    @ Override
    public String getBaseURI()
    {
        
        return null;
    }

    @ Override
    public short compareDocumentPosition(Node other) throws DOMException
    {
        
        return 0;
    }

    @ Override
    public String getTextContent() throws DOMException
    {
        
        return null;
    }

    @ Override
    public void setTextContent(String textContent) throws DOMException
    {
        

    }

    @ Override
    public boolean isSameNode(Node other)
    {
        
        return false;
    }

    @ Override
    public String lookupPrefix(String namespaceURI)
    {
        
        return null;
    }

    @ Override
    public boolean isDefaultNamespace(String namespaceURI)
    {
        
        return false;
    }

    @ Override
    public String lookupNamespaceURI(String prefix)
    {
        
        return null;
    }

    @ Override
    public boolean isEqualNode(Node arg)
    {
        
        return false;
    }

    @ Override
    public Object getFeature(String feature, String version)
    {
        
        return null;
    }

    @ Override
    public Object setUserData(String key, Object data, UserDataHandler handler)
    {
        
        return null;
    }

    @ Override
    public Object getUserData(String key)
    {
        
        return null;
    }

    @ Override
    public TypeInfo getSchemaTypeInfo()
    {
        
        return null;
    }

    @ Override
    public void setIdAttribute(String name, boolean isId) throws DOMException
    {
        

    }

    @ Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId)
        throws DOMException
    {
        

    }

    @ Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException
    {
        

    }
}

