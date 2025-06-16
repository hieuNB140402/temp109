

package com.document.allreader.allofficefilereader.fc.dom4j;

/**
 * <p>
 * <code>VisitorSupport</code> is an abstract base class which is useful for
 * implementation inheritence or when using anonymous inner classes to create
 * simple <code>Visitor</code> implementations.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.6 $
 */
public abstract class VisitorSupport implements Visitor {
    public VisitorSupport() {
    }

    public void visit(Document document) {
    }

    public void visit(DocumentType documentType) {
    }

    public void visit(Element node) {
    }

    public void visit(Attribute node) {
    }

    public void visit(CDATA node) {
    }

    public void visit(Comment node) {
    }

    public void visit(Entity node) {
    }

    public void visit(Namespace namespace) {
    }

    public void visit(ProcessingInstruction node) {
    }

    public void visit(Text node) {
    }
}

