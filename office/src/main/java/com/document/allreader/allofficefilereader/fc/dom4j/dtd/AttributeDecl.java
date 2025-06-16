

package com.document.allreader.allofficefilereader.fc.dom4j.dtd;

public class AttributeDecl {
    private String elementName;

    private String attributeName;

    private String type;

    private String value;

    private String valueDefault;

    public AttributeDecl() {
    }

    public AttributeDecl(String elementName, String attributeName, String type,
            String valueDefault, String value) {
        this.elementName = elementName;
        this.attributeName = attributeName;
        this.type = type;
        this.value = value;
        this.valueDefault = valueDefault;
    }


    public String getElementName() {
        return elementName;
    }


    public void setElementName(String elementName) {
        this.elementName = elementName;
    }


    public String getAttributeName() {
        return attributeName;
    }


    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public String getValueDefault() {
        return valueDefault;
    }


    public void setValueDefault(String valueDefault) {
        this.valueDefault = valueDefault;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer("<!ATTLIST ");
        buffer.append(elementName);
        buffer.append(" ");
        buffer.append(attributeName);
        buffer.append(" ");
        buffer.append(type);
        buffer.append(" ");

        if (valueDefault != null) {
            buffer.append(valueDefault);

            if (valueDefault.equals("#FIXED")) {
                buffer.append(" \"");
                buffer.append(value);
                buffer.append("\"");
            }
        } else {
            buffer.append("\"");
            buffer.append(value);
            buffer.append("\"");
        }

        buffer.append(">");

        return buffer.toString();
    }
}

