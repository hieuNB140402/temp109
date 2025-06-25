

package com.document.allreader.allofficefilereader.fc.dom4j.util;

import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.BaseElement;


/**
 * <p>
 * <code>NonLazyElement</code> is the default DOM4J default implementation of
 * an XML element.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.8 $
 */
public class NonLazyElement extends BaseElement
{
    public NonLazyElement(String name)
    {
        super(name);
        this.attributes = createAttributeList();
        this.content = createContentList();
    }

    public NonLazyElement(QName qname)
    {
        super(qname);
        this.attributes = createAttributeList();
        this.content = createContentList();
    }

    public NonLazyElement(String name, Namespace namespace)
    {
        super(name, namespace);
        this.attributes = createAttributeList();
        this.content = createContentList();
    }

    public NonLazyElement(QName qname, int attributeCount)
    {
        super(qname);
        this.attributes = createAttributeList(attributeCount);
        this.content = createContentList();
    }
}

