

package com.document.allreader.allofficefilereader.fc.dom4j.xpath;

import com.document.allreader.allofficefilereader.fc.dom4j.Node;
import com.document.allreader.allofficefilereader.fc.dom4j.rule.Pattern;


/**
 * <p>
 * <code>XPathPattern</code> is an implementation of Pattern which uses an
 * XPath xpath.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.18.2.1 $
 */
public class XPathPattern implements Pattern
{
    private String text;

    private Pattern pattern;

    //private Context context;

    public XPathPattern(Pattern pattern)
    {
        /*this.pattern = pattern;
        this.text = pattern.getText();
        this.context = new Context(getContextSupport());*/
    }

    public XPathPattern(String text)
    {
        /*this.text = text;
        this.context = new Context(getContextSupport());

        try
        {
            this.pattern = PatternParser.parse(text);
        }
        catch(SAXPathException e)
        {
            throw new InvalidXPathException(text, e.getMessage());
        }
        catch(Throwable t)
        {
            throw new InvalidXPathException(text, t);
        }*/
    }

    public boolean matches(Node node)
    {
        /*try
        {
            ArrayList list = new ArrayList(1);
            list.add(node);
            context.setNodeSet(list);

            return pattern.matches(node, context);
        }
        catch(JaxenException e)
        {
            handleJaxenException(e);

            return false;
        }*/
        return false;
    }

    public String getText()
    {
        return text;
    }

    public double getPriority()
    {
        return pattern.getPriority();
    }

    public Pattern[] getUnionPatterns()
    {
        Pattern[] patterns = pattern.getUnionPatterns();

        if (patterns != null)
        {
            int size = patterns.length;
            XPathPattern[] answer = new XPathPattern[size];

            for (int i = 0; i < size; i++)
            {
                answer[i] = new XPathPattern(patterns[i]);
            }

            return answer;
        }

        return null;
    }

    public short getMatchType()
    {
        return pattern.getMatchType();
    }

    public String getMatchesNodeName()
    {
        return pattern.getMatchesNodeName();
    }

    /*public void setVariableContext(VariableContext variableContext)
    {
        context.getContextSupport().setVariableContext(variableContext);
    }

    public String toString()
    {
        return "[XPathPattern: text: " + text + " Pattern: " + pattern + "]";
    }

    protected ContextSupport getContextSupport()
    {
        return new ContextSupport(new SimpleNamespaceContext(), XPathFunctionContext.getInstance(),
            new SimpleVariableContext(), DocumentNavigator.getInstance());
    }

    protected void handleJaxenException(JaxenException exception) throws XPathException
    {
        throw new XPathException(text, exception);
    }*/
}

