

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.ProcessingInstruction;
import com.document.allreader.allofficefilereader.fc.dom4j.Visitor;


/**
 * <p>
 * <code>AbstractProcessingInstruction</code> is an abstract base class for
 * tree implementors to use for implementation inheritence.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.17 $
 */
public abstract class AbstractProcessingInstruction extends AbstractNode implements
    ProcessingInstruction
{
    public AbstractProcessingInstruction()
    {
    }

    public short getNodeType()
    {
        return PROCESSING_INSTRUCTION_NODE;
    }

    public String getPath(Element context)
    {
        Element parent = getParent();

        return ((parent != null) && (parent != context))
            ? (parent.getPath(context) + "/processing-instruction()") : "processing-instruction()";
    }

    public String getUniquePath(Element context)
    {
        Element parent = getParent();

        return ((parent != null) && (parent != context))
            ? (parent.getUniquePath(context) + "/processing-instruction()")
            : "processing-instruction()";
    }

    public String toString()
    {
        return super.toString() + " [ProcessingInstruction: &" + getName() + ";]";
    }

    public String asXML()
    {
        return "<?" + getName() + " " + getText() + "?>";
    }

    public void write(Writer writer) throws IOException
    {
        writer.write("<?");
        writer.write(getName());
        writer.write(" ");
        writer.write(getText());
        writer.write("?>");
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }

    public void setValue(String name, String value)
    {
        throw new UnsupportedOperationException("This PI is read-only and " + "cannot be modified");
    }

    public void setValues(Map data)
    {
        throw new UnsupportedOperationException("This PI is read-only and " + "cannot be modified");
    }

    public String getName()
    {
        return getTarget();
    }

    public void setName(String name)
    {
        setTarget(name);
    }

    public boolean removeValue(String name)
    {
        return false;
    }

    // Helper methods

    /**
     * <p>
     * This will convert the Map to a string representation.
     * </p>
     * 
     * @param values
     *            is a <code>Map</code> of PI data to convert
     * 
     * @return DOCUMENT ME!
     */
    protected String toString(Map values)
    {
        StringBuffer buffer = new StringBuffer();

        for (Iterator iter = values.entrySet().iterator(); iter.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iter.next();
            String name = (String)entry.getKey();
            String value = (String)entry.getValue();

            buffer.append(name);
            buffer.append("=\"");
            buffer.append(value);
            buffer.append("\" ");
        }

        // remove the last space
        buffer.setLength(buffer.length() - 1);

        return buffer.toString();
    }

    /**
     * <p>
     * Parses the raw data of PI as a <code>Map</code>.
     * </p>
     * 
     * @param text
     *            <code>String</code> PI data to parse
     * 
     * @return DOCUMENT ME!
     */
    protected Map parseValues(String text)
    {
        Map data = new HashMap();

        StringTokenizer s = new StringTokenizer(text, " =\'\"", true);

        while (s.hasMoreTokens())
        {
            String name = getName(s);

            if (s.hasMoreTokens())
            {
                String value = getValue(s);
                data.put(name, value);
            }
        }

        return data;
    }

    private String getName(StringTokenizer tokenizer)
    {
        String token = tokenizer.nextToken();
        StringBuffer name = new StringBuffer(token);

        while (tokenizer.hasMoreTokens())
        {
            token = tokenizer.nextToken();

            if (!token.equals("="))
            {
                name.append(token);
            }
            else
            {
                break;
            }
        }

        return name.toString().trim();
    }

    private String getValue(StringTokenizer tokenizer)
    {
        String token = tokenizer.nextToken();
        StringBuffer value = new StringBuffer();

        /* get the quote */
        while (tokenizer.hasMoreTokens() && !token.equals("\'") && !token.equals("\""))
        {
            token = tokenizer.nextToken();
        }

        String quote = token;

        while (tokenizer.hasMoreTokens())
        {
            token = tokenizer.nextToken();

            if (!quote.equals(token))
            {
                value.append(token);
            }
            else
            {
                break;
            }
        }

        return value.toString();
    }
}

