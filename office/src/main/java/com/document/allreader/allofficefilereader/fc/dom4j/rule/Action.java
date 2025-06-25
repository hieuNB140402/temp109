

package com.document.allreader.allofficefilereader.fc.dom4j.rule;

import com.document.allreader.allofficefilereader.fc.dom4j.Node;

/**
 * <p>
 * <code>Action</code> represents some default action which should occur when
 * a rule matches a node in the XSLT processing model.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public interface Action
{
    void run(Node node) throws Exception;
}

