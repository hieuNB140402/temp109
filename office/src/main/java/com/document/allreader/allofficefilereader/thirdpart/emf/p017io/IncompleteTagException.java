package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.IncompleteTagException */

public class IncompleteTagException extends IOException {
    private static final long serialVersionUID = -7808675150856818588L;
    private byte[] rest;
    private Tag tag;

    public IncompleteTagException(Tag tag, byte[] bArr) {
        super("Tag " + tag + " contains " + bArr.length + " unread bytes");
        this.tag = tag;
        this.rest = bArr;
    }

    public Tag getTag() {
        return this.tag;
    }

    public byte[] getBytes() {
        return this.rest;
    }
}
