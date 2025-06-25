

package com.document.allreader.allofficefilereader.fc.dom4j.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.document.allreader.allofficefilereader.fc.dom4j.Attribute;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Node;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.BackedList;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.DefaultElement;


/**
 * <p>
 * <code>IndexedElement</code> is an implementation of {@link Element}which
 * maintains an index of the attributes and elements it contains to optimise
 * lookups via name.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
public class IndexedElement extends DefaultElement
{
    /** Lazily constructed index for elements */
    private Map elementIndex;

    /** Lazily constructed index for attributes */
    private Map attributeIndex;

    public IndexedElement(String name)
    {
        super(name);
    }

    public IndexedElement(QName qname)
    {
        super(qname);
    }

    public IndexedElement(QName qname, int attributeCount)
    {
        super(qname, attributeCount);
    }

    public Attribute attribute(String name)
    {
        return (Attribute)attributeIndex().get(name);
    }

    public Attribute attribute(QName qName)
    {
        return (Attribute)attributeIndex().get(qName);
    }

    public Element element(String name)
    {
        return asElement(elementIndex().get(name));
    }

    public Element element(QName qName)
    {
        return asElement(elementIndex().get(qName));
    }

    public List elements(String name)
    {
        return asElementList(elementIndex().get(name));
    }

    public List elements(QName qName)
    {
        return asElementList(elementIndex().get(qName));
    }


    // -------------------------------------------------------------------------
    protected Element asElement(Object object)
    {
        if (object instanceof Element)
        {
            return (Element)object;
        }
        else if (object != null)
        {
            List list = (List)object;

            if (list.size() >= 1)
            {
                return (Element)list.get(0);
            }
        }

        return null;
    }

    protected List asElementList(Object object)
    {
        if (object instanceof Element)
        {
            return createSingleResultList(object);
        }
        else if (object != null)
        {
            List list = (List)object;
            BackedList answer = createResultList();

            for (int i = 0, size = list.size(); i < size; i++)
            {
                answer.addLocal(list.get(i));
            }

            return answer;
        }

        return createEmptyList();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param object
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @deprecated WILL BE REMOVED IN dom4j-1.6 !!
     */
    protected Iterator asElementIterator(Object object)
    {
        return asElementList(object).iterator();
    }

    // #### could we override the add(Element) remove(Element methods?
    protected void addNode(Node node)
    {
        super.addNode(node);

        if ((elementIndex != null) && node instanceof Element)
        {
            addToElementIndex((Element)node);
        }
        else if ((attributeIndex != null) && node instanceof Attribute)
        {
            addToAttributeIndex((Attribute)node);
        }
    }

    protected boolean removeNode(Node node)
    {
        if (super.removeNode(node))
        {
            if ((elementIndex != null) && node instanceof Element)
            {
                removeFromElementIndex((Element)node);
            }
            else if ((attributeIndex != null) && node instanceof Attribute)
            {
                removeFromAttributeIndex((Attribute)node);
            }

            return true;
        }

        return false;
    }

    protected Map attributeIndex()
    {
        if (attributeIndex == null)
        {
            attributeIndex = createAttributeIndex();

            for (Iterator iter = attributeIterator(); iter.hasNext();)
            {
                addToAttributeIndex((Attribute)iter.next());
            }
        }

        return attributeIndex;
    }

    protected Map elementIndex()
    {
        if (elementIndex == null)
        {
            elementIndex = createElementIndex();

            for (Iterator iter = elementIterator(); iter.hasNext();)
            {
                addToElementIndex((Element)iter.next());
            }
        }

        return elementIndex;
    }

    /**
     * A Factory Method to create the index for attributes
     * 
     * @return DOCUMENT ME!
     */
    protected Map createAttributeIndex()
    {
        Map answer = createIndex();

        return answer;
    }

    /**
     * A Factory Method to create the index for elements
     * 
     * @return DOCUMENT ME!
     */
    protected Map createElementIndex()
    {
        Map answer = createIndex();

        return answer;
    }

    protected void addToElementIndex(Element element)
    {
        QName qName = element.getQName();
        String name = qName.getName();
        addToElementIndex(qName, element);
        addToElementIndex(name, element);
    }

    protected void addToElementIndex(Object key, Element value)
    {
        Object oldValue = elementIndex.get(key);

        if (oldValue == null)
        {
            elementIndex.put(key, value);
        }
        else
        {
            if (oldValue instanceof List)
            {
                List list = (List)oldValue;
                list.add(value);
            }
            else
            {
                List list = createList();
                list.add(oldValue);
                list.add(value);
                elementIndex.put(key, list);
            }
        }
    }

    protected void removeFromElementIndex(Element element)
    {
        QName qName = element.getQName();
        String name = qName.getName();
        removeFromElementIndex(qName, element);
        removeFromElementIndex(name, element);
    }

    protected void removeFromElementIndex(Object key, Element value)
    {
        Object oldValue = elementIndex.get(key);

        if (oldValue instanceof List)
        {
            List list = (List)oldValue;
            list.remove(value);
        }
        else
        {
            elementIndex.remove(key);
        }
    }

    protected void addToAttributeIndex(Attribute attribute)
    {
        QName qName = attribute.getQName();
        String name = qName.getName();
        addToAttributeIndex(qName, attribute);
        addToAttributeIndex(name, attribute);
    }

    protected void addToAttributeIndex(Object key, Attribute value)
    {
        Object oldValue = attributeIndex.get(key);

        if (oldValue != null)
        {
            attributeIndex.put(key, value);
        }
    }

    protected void removeFromAttributeIndex(Attribute attribute)
    {
        QName qName = attribute.getQName();
        String name = qName.getName();
        removeFromAttributeIndex(qName, attribute);
        removeFromAttributeIndex(name, attribute);
    }

    protected void removeFromAttributeIndex(Object key, Attribute value)
    {
        Object oldValue = attributeIndex.get(key);

        if ((oldValue != null) && oldValue.equals(value))
        {
            attributeIndex.remove(key);
        }
    }

    /**
     * Factory method to return a new map implementation for indices
     * 
     * @return DOCUMENT ME!
     */
    protected Map createIndex()
    {
        return new HashMap();
    }

    /**
     * Factory method to return a list implementation for indices
     * 
     * @return DOCUMENT ME!
     */
    protected List createList()
    {
        return new ArrayList();
    }
}

