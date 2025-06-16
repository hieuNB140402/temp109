

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Node;
import com.document.allreader.allofficefilereader.fc.dom4j.Text;

/**
 * <p>
 * <code>FlyweightText</code> is a Flyweight pattern implementation of a
 * singly linked, read-only XML Text.
 * </p>
 * 
 * <p>
 * This node could be shared across documents and elements though it does not
 * support the parent relationship.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public class FlyweightText extends AbstractText implements Text
{
    /** Text of the <code>Text</code> node */
    protected String text;

    /**
     * DOCUMENT ME!
     * 
     * @param text
     *            is the Text text
     */
    public FlyweightText(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    protected Node createXPathResult(Element parent)
    {
        return new DefaultText(parent, getText());
    }
}

