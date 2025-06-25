package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.ActionHeader */

public class ActionHeader {
    int actionCode;
    long length;

    public ActionHeader(int i, long j) {
        this.actionCode = i;
        this.length = j;
    }

    public void setAction(int i) {
        this.actionCode = i;
    }

    public int getAction() {
        return this.actionCode;
    }

    public void setLength(long j) {
        this.length = j;
    }

    public long getLength() {
        return this.length;
    }
}
