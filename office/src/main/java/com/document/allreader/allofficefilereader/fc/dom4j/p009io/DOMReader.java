package com.document.allreader.allofficefilereader.fc.dom4j.p009io;

import com.document.allreader.allofficefilereader.fc.dom4j.Branch;
import com.document.allreader.allofficefilereader.fc.dom4j.Document;
import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;
import com.document.allreader.allofficefilereader.fc.dom4j.tree.NamespaceStack;
import java.util.ArrayList;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* renamed from: com.allreader.office.allofficefilereader.fc.dom4j.io.DOMReader */

public class DOMReader {
    private DocumentFactory factory;
    private NamespaceStack namespaceStack;

    public DOMReader() {
        DocumentFactory instance = DocumentFactory.getInstance();
        this.factory = instance;
        this.namespaceStack = new NamespaceStack(instance);
    }

    public DOMReader(DocumentFactory documentFactory) {
        this.factory = documentFactory;
        this.namespaceStack = new NamespaceStack(documentFactory);
    }

    public DocumentFactory getDocumentFactory() {
        return this.factory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.factory = documentFactory;
        this.namespaceStack.setDocumentFactory(documentFactory);
    }

    public Document read(org.w3c.dom.Document document) {
        if (document instanceof Document) {
            return (Document) document;
        }
        Document createDocument = createDocument();
        clearNamespaceStack();
        NodeList childNodes = document.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            readTree(childNodes.item(i), createDocument);
        }
        return createDocument;
    }

    protected void readTree(Node node, Branch branch) {
        Document document;
        boolean z = branch instanceof Element;
        Element element = null;
        if (z) {
            document = null;
            element = (Element) branch;
        } else {
            document = (Document) branch;
        }
        switch (node.getNodeType()) {
            case 1:
                readElement(node, branch);
                return;
            case 2:
            case 9:
            default:
                return;
            case 3:
                element.addText(node.getNodeValue());
                return;
            case 4:
                element.addCDATA(node.getNodeValue());
                return;
            case 5:
                Node firstChild = node.getFirstChild();
                if (firstChild != null) {
                    element.addEntity(node.getNodeName(), firstChild.getNodeValue());
                    return;
                } else {
                    element.addEntity(node.getNodeName(), "");
                    return;
                }
            case 6:
                element.addEntity(node.getNodeName(), node.getNodeValue());
                return;
            case 7:
                if (z) {
                    ((Element) branch).addProcessingInstruction(node.getNodeName(), node.getNodeValue());
                    return;
                } else {
                    ((Document) branch).addProcessingInstruction(node.getNodeName(), node.getNodeValue());
                    return;
                }
            case 8:
                if (z) {
                    ((Element) branch).addComment(node.getNodeValue());
                    return;
                } else {
                    ((Document) branch).addComment(node.getNodeValue());
                    return;
                }
            case 10:
                DocumentType documentType = (DocumentType) node;
                document.addDocType(documentType.getName(), documentType.getPublicId(), documentType.getSystemId());
                return;
        }
    }

    protected void readElement(Node node, Branch branch) {
        Node namedItem;
        int size = this.namespaceStack.size();
        String namespaceURI = node.getNamespaceURI();
        node.getPrefix();
        NamedNodeMap attributes = node.getAttributes();
        if (!(attributes == null || namespaceURI != null || (namedItem = attributes.getNamedItem("xmlns")) == null)) {
            namespaceURI = namedItem.getNodeValue();
        }
        Element addElement = branch.addElement(this.namespaceStack.getQName(namespaceURI, node.getLocalName(), node.getNodeName()));
        if (attributes != null) {
            int length = attributes.getLength();
            ArrayList arrayList = new ArrayList(length);
            for (int i = 0; i < length; i++) {
                Node item = attributes.item(i);
                String nodeName = item.getNodeName();
                if (nodeName.startsWith("xmlns")) {
                    addElement.add(this.namespaceStack.addNamespace(getPrefix(nodeName), item.getNodeValue()));
                } else {
                    arrayList.add(item);
                }
            }
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Node node2 = (Node) arrayList.get(i2);
                addElement.addAttribute(this.namespaceStack.getQName(node2.getNamespaceURI(), node2.getLocalName(), node2.getNodeName()), node2.getNodeValue());
            }
        }
        NodeList childNodes = node.getChildNodes();
        int length2 = childNodes.getLength();
        for (int i3 = 0; i3 < length2; i3++) {
            readTree(childNodes.item(i3), addElement);
        }
        while (this.namespaceStack.size() > size) {
            this.namespaceStack.pop();
        }
    }

    protected Namespace getNamespace(String str, String str2) {
        return getDocumentFactory().createNamespace(str, str2);
    }

    protected Document createDocument() {
        return getDocumentFactory().createDocument();
    }

    protected void clearNamespaceStack() {
        this.namespaceStack.clear();
        if (!this.namespaceStack.contains(Namespace.XML_NAMESPACE)) {
            this.namespaceStack.push(Namespace.XML_NAMESPACE);
        }
    }

    private String getPrefix(String str) {
        int indexOf = str.indexOf(58, 5);
        return indexOf != -1 ? str.substring(indexOf + 1) : "";
    }
}
