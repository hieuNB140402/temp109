

package com.document.allreader.allofficefilereader.fc.dom4j.dtd;


public class InternalEntityDecl {

    private String name;


    private String value;

    public InternalEntityDecl() {
    }

    public InternalEntityDecl(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer("<!ENTITY ");

        if (name.startsWith("%")) {
            buffer.append("% ");
            buffer.append(name.substring(1));
        } else {
            buffer.append(name);
        }

        buffer.append(" \"");
        buffer.append(escapeEntityValue(value));
        buffer.append("\">");

        return buffer.toString();
    }

    private String escapeEntityValue(String text) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            switch (c) {
                case '<':
                    result.append("&#38;#60;");

                    break;

                case '>':
                    result.append("&#62;");

                    break;

                case '&':
                    result.append("&#38;#38;");

                    break;

                case '\'':
                    result.append("&#39;");

                    break;

                case '\"':
                    result.append("&#34;");

                    break;

                default:

                    if (c < 32) {
                        result.append("&#" + (int) c + ";");
                    } else {
                        result.append(c);
                    }

                    break;
            }
        }

        return result.toString();
    }
}

