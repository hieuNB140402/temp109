

package com.document.allreader.allofficefilereader.fc.dom4j;

/**
 * <p>
 * <code>ElementHandler</code> interface defines a handler of
 * <code>Element</code> objects. It is used primarily in event based
 * processing models such as for processing large XML documents as they are
 * being parsed rather than waiting until the whole document is parsed.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.8 $
 */
public interface ElementHandler {
    /**
     * Called by an event based processor when an elements openning tag is
     * encountered.
     * 
     * @param elementPath
     *            is the current <code>ElementPath</code> to process
     */
    void onStart(ElementPath elementPath);

    /**
     * Called by an event based processor when an elements closing tag is
     * encountered.
     * 
     * @param elementPath
     *            is the current <code>ElementPath</code> to process
     */
    void onEnd(ElementPath elementPath);
}

