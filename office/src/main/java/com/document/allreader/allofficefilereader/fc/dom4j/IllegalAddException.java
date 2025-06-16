

package com.document.allreader.allofficefilereader.fc.dom4j;

/**
 * <p>
 * <code>IllegalAddException</code> is thrown when a node is added incorrectly
 * to an <code>{@link Element}</code>
 * </p>
 * 
 * @version $Revision: 1.6 $
 */
public class IllegalAddException extends IllegalArgumentException {
    public IllegalAddException(String reason) {
        super(reason);
    }

    public IllegalAddException(Element parent, Node node, String reason) {
        super("The node \"" + node.toString()
                + "\" could not be added to the element \""
                + parent.getQualifiedName() + "\" because: " + reason);
    }

    public IllegalAddException(Branch parent, Node node, String reason) {
        super("The node \"" + node.toString()
                + "\" could not be added to the branch \"" + parent.getName()
                + "\" because: " + reason);
    }
}

