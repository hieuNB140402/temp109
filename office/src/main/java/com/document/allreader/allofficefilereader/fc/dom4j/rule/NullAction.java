

package com.document.allreader.allofficefilereader.fc.dom4j.rule;

import com.document.allreader.allofficefilereader.fc.dom4j.Node;

/**
 * <p>
 * <code>NullAction</code> represents an empty action that does nothing.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public class NullAction implements Action
{
    /** Singleton instance */
    public static final NullAction SINGLETON = new NullAction();

    public void run(Node node) throws Exception
    {
    }
}

