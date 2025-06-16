

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.io.IOException;
import java.io.Writer;

import com.document.allreader.allofficefilereader.fc.dom4j.Comment;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Visitor;


/**
 * <p>
 * <code>AbstractComment</code> is an abstract base class for tree
 * implementors to use for implementation inheritence.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.13 $
 */
public abstract class AbstractComment extends AbstractCharacterData implements Comment
{
    public AbstractComment()
    {
    }

    public short getNodeType()
    {
        return COMMENT_NODE;
    }

    public String getPath(Element context)
    {
        Element parent = getParent();

        return ((parent != null) && (parent != context)) ? (parent.getPath(context) + "/comment()")
            : "comment()";
    }

    public String getUniquePath(Element context)
    {
        Element parent = getParent();

        return ((parent != null) && (parent != context))
            ? (parent.getUniquePath(context) + "/comment()") : "comment()";
    }

    public String toString()
    {
        return super.toString() + " [Comment: \"" + getText() + "\"]";
    }

    public String asXML()
    {
        return "<!--" + getText() + "-->";
    }

    public void write(Writer writer) throws IOException
    {
        writer.write("<!--");
        writer.write(getText());
        writer.write("-->");
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}

