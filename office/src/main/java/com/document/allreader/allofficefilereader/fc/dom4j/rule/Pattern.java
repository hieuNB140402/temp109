

package com.document.allreader.allofficefilereader.fc.dom4j.rule;

import com.document.allreader.allofficefilereader.fc.dom4j.Node;
import com.document.allreader.allofficefilereader.fc.dom4j.NodeFilter;

/**
 * <p>
 * <code>Pattern</code> defines the behaviour for pattern in the XSLT
 * processing model.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.6 $
 */
public interface Pattern extends NodeFilter
{
    // These node numbers are compatable with DOM4J's Node types

    /** Matches any node */
    short ANY_NODE = 0;

    /** Matches no nodes */
    short NONE = 9999;

    /** Count of the number of node types */
    short NUMBER_OF_TYPES = Node.UNKNOWN_NODE;

    /**
     * According to the <a href="http://www.w3.org/TR/xslt11/#conflict">spec
     * </a> we should return 0.5 if we cannot determine the priority
     */
    double DEFAULT_PRIORITY = 0.5;

    /**
     * DOCUMENT ME!
     * 
     * @param node
     *            DOCUMENT ME!
     * 
     * @return true if the pattern matches the given DOM4J node.
     */
    boolean matches(Node node);

    /**
     * Returns the default resolution policy of the pattern according to the <a
     * href="http://www.w3.org/TR/xslt11/#conflict"> XSLT conflict resolution
     * spec </a>.
     * 
     * @return DOCUMENT ME!
     */
    double getPriority();

    /**
     * If this pattern is a union pattern then this method should return an
     * array of patterns which describe the union pattern, which should contain
     * more than one pattern. Otherwise this method should return null.
     * 
     * @return an array of the patterns which make up this union pattern or null
     *         if this pattern is not a union pattern
     */
    Pattern[] getUnionPatterns();

    /**
     * DOCUMENT ME!
     * 
     * @return the type of node the pattern matches which by default should
     *         return ANY_NODE if it can match any kind of node.
     */
    short getMatchType();

    /**
     * For patterns which only match an ATTRIBUTE_NODE or an ELEMENT_NODE then
     * this pattern may return the name of the element or attribute it matches.
     * This allows a more efficient rule matching algorithm to be performed,
     * rather than a brute force approach of evaluating every pattern for a
     * given Node.
     * 
     * @return the name of the element or attribute this pattern matches or null
     *         if this pattern matches any or more than one name.
     */
    String getMatchesNodeName();
}

