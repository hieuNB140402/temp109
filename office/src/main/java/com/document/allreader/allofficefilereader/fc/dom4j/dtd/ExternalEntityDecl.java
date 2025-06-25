

package com.document.allreader.allofficefilereader.fc.dom4j.dtd;

public class ExternalEntityDecl {

    private String name;


    private String publicID;


    private String systemID;

    public ExternalEntityDecl() {
    }

    public ExternalEntityDecl(String name, String publicID, String systemID) {
        this.name = name;
        this.publicID = publicID;
        this.systemID = systemID;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPublicID() {
        return publicID;
    }


    public void setPublicID(String publicID) {
        this.publicID = publicID;
    }


    public String getSystemID() {
        return systemID;
    }


    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer("<!ENTITY ");

        if (name.startsWith("%")) {
            buffer.append("% ");
            buffer.append(name.substring(1));
        } else {
            buffer.append(name);
        }

        if (publicID != null) {
            buffer.append(" PUBLIC \"");
            buffer.append(publicID);
            buffer.append("\" ");

            if (systemID != null) {
                buffer.append("\"");
                buffer.append(systemID);
                buffer.append("\" ");
            }
        } else if (systemID != null) {
            buffer.append(" SYSTEM \"");
            buffer.append(systemID);
            buffer.append("\" ");
        }

        buffer.append(">");

        return buffer.toString();
    }
}

