package com.document.allreader.allofficefilereader.fc.dom4j.p009io;

import com.document.allreader.allofficefilereader.fc.dom4j.ElementPath;

/* renamed from: com.allreader.office.allofficefilereader.fc.dom4j.io.PruningDispatchHandler */

class PruningDispatchHandler extends DispatchHandler {
    PruningDispatchHandler() {
    }

    @Override // fc4j.p009io.DispatchHandler, fc4j.ElementHandler
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        if (getActiveHandlerCount() == 0) {
            elementPath.getCurrent().detach();
        }
    }
}
