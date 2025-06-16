

package com.document.allreader.allofficefilereader.fc.dom4j.rule.pattern;

import com.document.allreader.allofficefilereader.fc.dom4j.Node;
import com.document.allreader.allofficefilereader.fc.dom4j.NodeFilter;
import com.document.allreader.allofficefilereader.fc.dom4j.rule.Pattern;


/**
 * <p>
 * <code>DefaultPattern</code> a default implementation of Pattern which can
 * take any XPath implementation or NodeFilter for defining the pattern.
 * <b>WARNING </b> this implementation causes a worst case, brute force XSLT
 * rule evaluation to be performed. Wherever possible the methods {@link
 * #getPriority}, {@link #getMatchType}and {@link #getMatchesNodeName}should
 * be overloaded to allow more rule filtering to occur.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.6 $
 */
public class DefaultPattern implements Pattern
{
    private NodeFilter filter;

    public DefaultPattern(NodeFilter filter)
    {
        this.filter = filter;
    }

    public boolean matches(Node node)
    {
        return filter.matches(node);
    }

    public double getPriority()
    {
        return Pattern.DEFAULT_PRIORITY;
    }

    public Pattern[] getUnionPatterns()
    {
        return null;
    }

    public short getMatchType()
    {
        return ANY_NODE;
    }

    public String getMatchesNodeName()
    {
        return null;
    }
}

