package com.document.allreader.allofficefilereader.fc.dom4j.p009io;

import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/* renamed from: com.allreader.office.allofficefilereader.fc.dom4j.io.XPPReader */

public class XPPReader {
    private DispatchHandler dispatchHandler;
    private DocumentFactory factory;

    public XPPReader() {
    }

    public XPPReader(DocumentFactory documentFactory) {
        this.factory = documentFactory;
    }

    public DocumentFactory getDocumentFactory() {
        if (this.factory == null) {
            this.factory = DocumentFactory.getInstance();
        }
        return this.factory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.factory = documentFactory;
    }

    public void addHandler(String str, ElementHandler elementHandler) {
        getDispatchHandler().addHandler(str, elementHandler);
    }

    public void removeHandler(String str) {
        getDispatchHandler().removeHandler(str);
    }

    public void setDefaultHandler(ElementHandler elementHandler) {
        getDispatchHandler().setDefaultHandler(elementHandler);
    }

    protected DispatchHandler getDispatchHandler() {
        if (this.dispatchHandler == null) {
            this.dispatchHandler = new DispatchHandler();
        }
        return this.dispatchHandler;
    }

    protected void setDispatchHandler(DispatchHandler dispatchHandler) {
        this.dispatchHandler = dispatchHandler;
    }

    protected Reader createReader(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
