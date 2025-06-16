

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import com.document.allreader.allofficefilereader.fc.dom4j.CDATA;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Node;

/**
 * <p>
 * <code>FlyweightCDATA</code> is a Flyweight pattern implementation of a
 * singly linked, read-only XML CDATA.
 * </p>
 * 
 * <p>
 * This node could be shared across documents and elements though it does not
 * support the parent relationship.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.8 $
 */
public class FlyweightCDATA extends AbstractCDATA implements CDATA
{
    /** Text of the <code>CDATA</code> node */
    protected String text;

    /**
     * DOCUMENT ME!
     * 
     * @param text
     *            is the CDATA text
     */
    public FlyweightCDATA(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    protected Node createXPathResult(Element parent)
    {
        return new DefaultCDATA(parent, getText());
    }
}

