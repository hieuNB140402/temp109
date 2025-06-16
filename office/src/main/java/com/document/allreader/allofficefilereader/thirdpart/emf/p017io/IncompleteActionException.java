package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.IncompleteActionException */

public class IncompleteActionException extends IOException {
    private static final long serialVersionUID = -6817511986951461967L;
    private Action action;
    private byte[] rest;

    public IncompleteActionException(Action action, byte[] bArr) {
        super("Action " + action + " contains " + bArr.length + " unread bytes");
        this.action = action;
        this.rest = bArr;
    }

    public Action getAction() {
        return this.action;
    }

    public byte[] getBytes() {
        return this.rest;
    }
}
