

package com.document.allreader.allofficefilereader.fc.dom4j.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;


public class DOMAttributeNodeMap implements org.w3c.dom.NamedNodeMap
{
    private DOMElement element;

    public DOMAttributeNodeMap(DOMElement element)
    {
        this.element = element;
    }


    public void foo() throws DOMException
    {
        DOMNodeHelper.notSupported();
    }

    public Node getNamedItem(String name)
    {
        return element.getAttributeNode(name);
    }

    public Node setNamedItem(Node arg) throws DOMException
    {
        if (arg instanceof Attr)
        {
            return element.setAttributeNode((org.w3c.dom.Attr)arg);
        }
        else
        {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Node is not an Attr: " + arg);
        }
    }

    public Node removeNamedItem(String name) throws DOMException
    {
        org.w3c.dom.Attr attr = element.getAttributeNode(name);

        if (attr == null)
        {
            throw new DOMException(DOMException.NOT_FOUND_ERR, "No attribute named " + name);
        }

        return element.removeAttributeNode(attr);
    }

    public Node item(int index)
    {
        return DOMNodeHelper.asDOMAttr(element.attribute(index));
    }

    public int getLength()
    {
        return element.attributeCount();
    }

    public Node getNamedItemNS(String namespaceURI, String localName)
    {
        return element.getAttributeNodeNS(namespaceURI, localName);
    }

    public Node setNamedItemNS(Node arg) throws DOMException
    {
        if (arg instanceof Attr)
        {
            return element.setAttributeNodeNS((org.w3c.dom.Attr)arg);
        }
        else
        {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Node is not an Attr: " + arg);
        }
    }

    public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException
    {
        org.w3c.dom.Attr attr = element.getAttributeNodeNS(namespaceURI, localName);

        if (attr != null)
        {
            return element.removeAttributeNode(attr);
        }

        return attr;
    }
}

