package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.UndefinedTag */

public class UndefinedTag extends Tag {
    private int[] bytes;

    @Override // com.allreader.office.allofficefilereader.thirdpart.emf.p017io.Tag
    public int getTagType() {
        return 0;
    }

    public UndefinedTag() {
        this(-1, new int[0]);
    }

    public UndefinedTag(int i, int[] iArr) {
        super(i, 3);
        this.bytes = iArr;
    }

    @Override // com.allreader.office.allofficefilereader.thirdpart.emf.p017io.Tag
    public Tag read(int i, TaggedInputStream taggedInputStream, int i2) throws IOException {
        return new UndefinedTag(i, taggedInputStream.readUnsignedByte(i2));
    }

    @Override // com.allreader.office.allofficefilereader.thirdpart.emf.p017io.Tag
    public String toString() {
        return "UNDEFINED TAG: " + getTag() + " length: " + this.bytes.length;
    }
}
