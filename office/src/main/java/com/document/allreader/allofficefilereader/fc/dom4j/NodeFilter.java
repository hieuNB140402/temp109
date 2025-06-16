

package com.document.allreader.allofficefilereader.fc.dom4j;

/**
 * <p>
 * <code>NodeFilter</code> defines the behavior for a filter or predicate
 * which acts on a DOM4J Node. Instances can be generated from an {@link
 * DocumentFactory}.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public interface NodeFilter {
    /**
     * <p>
     * <code>matches</code> returns true if the given node matches the filter
     * condition.
     * </p>
     * 
     * @param node
     *            DOCUMENT ME!
     * 
     * @return true if this filter matches the given node
     */
    boolean matches(Node node);
}

