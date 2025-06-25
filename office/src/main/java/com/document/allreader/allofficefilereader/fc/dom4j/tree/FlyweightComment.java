

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import com.document.allreader.allofficefilereader.fc.dom4j.Comment;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Node;

/**
 * <p>
 * <code>FlyweightComment</code> is a Flyweight pattern implementation of a
 * singly linked, read-only XML Comment.
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
public class FlyweightComment extends AbstractComment implements Comment
{
    /** Text of the <code>Comment</code> node */
    protected String text;

    /**
     * DOCUMENT ME!
     * 
     * @param text
     *            is the Comment text
     */
    public FlyweightComment(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    protected Node createXPathResult(Element parent)
    {
        return new DefaultComment(parent, getText());
    }
}

