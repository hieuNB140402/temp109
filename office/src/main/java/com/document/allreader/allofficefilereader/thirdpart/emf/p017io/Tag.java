package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.Tag */

public abstract class Tag {
    public static final int DEFAULT_TAG = -1;
    private String name = null;
    private int tagID;
    private int version;

    public int getTagType() {
        return 0;
    }

    public abstract Tag read(int i, TaggedInputStream taggedInputStream, int i2) throws IOException;

    public abstract String toString();

    protected Tag(int i, int i2) {
        this.tagID = i;
        this.version = i2;
    }

    public int getTag() {
        return this.tagID;
    }

    public int getVersion() {
        return this.version;
    }

    public String getName() {
        if (this.name == null) {
            String name = getClass().getName();
            this.name = name;
            int lastIndexOf = name.lastIndexOf(".");
            this.name = lastIndexOf >= 0 ? this.name.substring(lastIndexOf + 1) : this.name;
        }
        return this.name;
    }
}
