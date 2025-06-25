

package com.document.allreader.allofficefilereader.fc.dom4j.dtd;

public class ElementDecl {
    private String name;

    private String model;

    public ElementDecl() {
    }

    public ElementDecl(String name, String model) {
        this.name = name;
        this.model = model;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }

    public String toString() {
        return "<!ELEMENT " + name + " " + model + ">";
    }
}

