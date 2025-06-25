

package com.document.allreader.allofficefilereader.fc.dom4j.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultAttribute;


public class DOMAttribute extends DefaultAttribute implements org.w3c.dom.Attr
{
    public DOMAttribute(QName qname)
    {
        super(qname);
    }

    public DOMAttribute(QName qname, String value)
    {
        super(qname, value);
    }

    public DOMAttribute(Element parent, QName qname, String value)
    {
        super(parent, qname, value);
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
        return DOMNodeHelper.getNodeValue(this);
    }

    public void setNodeValue(String nodeValue) throws DOMException
    {
        DOMNodeHelper.setNodeValue(this, nodeValue);
    }

    public org.w3c.dom.Node getParentNode()
    {
      
        return null;
    }

    public NodeList getChildNodes()
    {
        return DOMNodeHelper.getChildNodes(this);
    }

    public org.w3c.dom.Node getFirstChild()
    {
        return DOMNodeHelper.getFirstChild(this);
    }

    public org.w3c.dom.Node getLastChild()
    {
        return DOMNodeHelper.getLastChild(this);
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
        return null;
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

        if (!((nodeType == org.w3c.dom.Node.TEXT_NODE) || (nodeType == org.w3c.dom.Node.ENTITY_REFERENCE_NODE)))
        {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                "The node cannot be a child of attribute");
        }
    }

    public boolean hasChildNodes()
    {
        return DOMNodeHelper.hasChildNodes(this);
    }

    public org.w3c.dom.Node cloneNode(boolean deep)
    {
        return DOMNodeHelper.cloneNode(this, deep);
    }

    public void normalize()
    {
        DOMNodeHelper.normalize(this);
    }

    public boolean isSupported(String feature, String version)
    {
        return DOMNodeHelper.isSupported(this, feature, version);
    }

    public boolean hasAttributes()
    {
        return DOMNodeHelper.hasAttributes(this);
    }

  
    public boolean getSpecified()
    {
        return true;
    }

    public org.w3c.dom.Element getOwnerElement()
    {
        return DOMNodeHelper.asDOMElement(getParent());
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
    public boolean isId()
    {
        
        return false;
    }
}

