

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.document.allreader.allofficefilereader.fc.dom4j.CDATA;
import com.document.allreader.allofficefilereader.fc.dom4j.Visitor;


/**
 * <p>
 * <code>AbstractCDATA</code> is an abstract base class for tree implementors
 * to use for implementation inheritence.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.12 $
 */
public abstract class AbstractCDATA extends AbstractCharacterData implements CDATA
{
    public AbstractCDATA()
    {
    }

    public short getNodeType()
    {
        return CDATA_SECTION_NODE;
    }

    public String toString()
    {
        return super.toString() + " [CDATA: \"" + getText() + "\"]";
    }

    public String asXML()
    {
        StringWriter writer = new StringWriter();

        try
        {
            write(writer);
        }
        catch(IOException e)
        {
            // will not happen since we are using a StringWriter!
        }

        return writer.toString();
    }

    public void write(Writer writer) throws IOException
    {
        writer.write("<![CDATA[");

        if (getText() != null)
        {
            writer.write(getText());
        }

        writer.write("]]>");
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}

