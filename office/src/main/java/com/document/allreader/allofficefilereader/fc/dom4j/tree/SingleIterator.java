

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.util.Iterator;

/**
 * <p>
 * <code>SingleIterator</code> is an {@link Iterator}over a single object
 * instance.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class SingleIterator implements Iterator {
    private boolean first = true;

    private Object object;

    public SingleIterator(Object object) {
        this.object = object;
    }

    public boolean hasNext() {
        return first;
    }

    public Object next() {
        Object answer = object;
        object = null;
        first = false;

        return answer;
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported by "
                + "this iterator");
    }
}

