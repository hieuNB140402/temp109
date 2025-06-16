package com.document.allreader.allofficefilereader.fc.dom4j.p009io;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementHandler;

/* renamed from: com.allreader.office.allofficefilereader.fc.dom4j.io.PruningElementStack */

class PruningElementStack extends ElementStack {
    private ElementHandler elementHandler;
    private int matchingElementIndex;
    private String[] path;

    public PruningElementStack(String[] strArr, ElementHandler elementHandler) {
        this.path = strArr;
        this.elementHandler = elementHandler;
        checkPath();
    }

    public PruningElementStack(String[] strArr, ElementHandler elementHandler, int i) {
        super(i);
        this.path = strArr;
        this.elementHandler = elementHandler;
        checkPath();
    }

    @Override // fc4j.p009io.ElementStack
    public Element popElement() {
        Element popElement = super.popElement();
        if (this.lastElementIndex == this.matchingElementIndex && this.lastElementIndex >= 0 && validElement(popElement, this.lastElementIndex + 1)) {
            int i = 0;
            Element element = null;
            Element element2 = null;
            while (true) {
                if (i > this.lastElementIndex) {
                    element = element2;
                    break;
                }
                element2 = this.stack[i];
                if (!validElement(element2, i)) {
                    break;
                }
                i++;
            }
            if (element != null) {
                pathMatches(element, popElement);
            }
        }
        return popElement;
    }

    protected void pathMatches(Element element, Element element2) {
        this.elementHandler.onEnd(this);
        element.remove(element2);
    }

    protected boolean validElement(Element element, int i) {
        String str = this.path[i];
        String name = element.getName();
        if (str == name) {
            return true;
        }
        if (str == null || name == null) {
            return false;
        }
        return str.equals(name);
    }

    private void checkPath() {
        String[] strArr = this.path;
        if (strArr.length >= 2) {
            this.matchingElementIndex = strArr.length - 2;
            return;
        }
        throw new RuntimeException("Invalid path of length: " + this.path.length + " it must be greater than 2");
    }
}
