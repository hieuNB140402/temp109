

package com.document.allreader.allofficefilereader.fc.dom4j;

/**
 * This interface is used by {@link ElementHandler}instances to retrieve
 * information about the current path hierarchy they are to process. It's
 * primary use is to retrieve the current {@link Element}being processed.
 * 
 * @author <a href="mailto:dwhite@equipecom.com">Dave White </a>
 * @version $Revision: 1.6 $
 */
public interface ElementPath {
    /**
     * DOCUMENT ME!
     * 
     * @return the number of elements in the path
     */
    int size();

    /**
     * DOCUMENT ME!
     * 
     * @param depth
     *            DOCUMENT ME!
     * 
     * @return the element at the specified depth index, 0 = root element
     */
    Element getElement(int depth);

    /**
     * DOCUMENT ME!
     * 
     * @return the path as a string
     */
    String getPath();

    /**
     * DOCUMENT ME!
     * 
     * @return the current element
     */
    Element getCurrent();

    /**
     * Adds the <code>ElementHandler</code> to be called when the specified
     * path is encounted. The path can be either an absolute path (i.e. prefixed
     * with "/") or a relative path (i.e. assummed to be a child of the current
     * path as retrieved by <b>getPath </b>.
     * 
     * @param path
     *            is the path to be handled
     * @param handler
     *            is the <code>ElementHandler</code> to be called by the event
     *            based processor.
     */
    void addHandler(String path, ElementHandler handler);

    /**
     * Removes the <code>ElementHandler</code> from the event based processor,
     * for the specified path. The path can be either an absolute path (i.e.
     * prefixed with "/") or a relative path (i.e. assummed to be a child of the
     * current path as retrieved by <b>getPath </b>.
     * 
     * @param path
     *            is the path to remove the <code>ElementHandler</code> for.
     */
    void removeHandler(String path);
}

