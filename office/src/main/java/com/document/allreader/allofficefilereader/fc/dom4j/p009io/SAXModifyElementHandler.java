package com.document.allreader.allofficefilereader.fc.dom4j.p009io;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementHandler;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementPath;

/* renamed from: com.allreader.office.allofficefilereader.fc.dom4j.io.SAXModifyElementHandler */

class SAXModifyElementHandler implements ElementHandler {
    private ElementModifier elemModifier;
    private Element modifiedElement;

    public SAXModifyElementHandler(ElementModifier elementModifier) {
        this.elemModifier = elementModifier;
    }

    @Override
    public void onStart(ElementPath elementPath) {
        this.modifiedElement = elementPath.getCurrent();
    }

    @Override
    public void onEnd(ElementPath elementPath) {
        try {
            Element current = elementPath.getCurrent();
            Element parent = current.getParent();
            if (parent != null) {
                Element modifyElement = this.elemModifier.modifyElement((Element) current.clone());
                this.modifiedElement = modifyElement;
                if (modifyElement != null) {
                    modifyElement.setParent(current.getParent());
                    this.modifiedElement.setDocument(current.getDocument());
                    parent.content().set(parent.indexOf(current), this.modifiedElement);
                }
                current.detach();
            } else if (current.isRootElement()) {
                Element modifyElement2 = this.elemModifier.modifyElement((Element) current.clone());
                this.modifiedElement = modifyElement2;
                if (modifyElement2 != null) {
                    modifyElement2.setDocument(current.getDocument());
                    current.getDocument().setRootElement(this.modifiedElement);
                }
                current.detach();
            }
            if (elementPath instanceof ElementStack) {
                ElementStack elementStack = (ElementStack) elementPath;
                elementStack.popElement();
                elementStack.pushElement(this.modifiedElement);
            }
        } catch (Exception e) {
            throw new SAXModifyException(e);
        }
    }

    protected Element getModifiedElement() {
        return this.modifiedElement;
    }
}
