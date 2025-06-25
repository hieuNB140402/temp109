package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.UndefinedTagException */

public class UndefinedTagException extends IOException {
    private static final long serialVersionUID = 7504997713135869344L;

    public UndefinedTagException() {
    }

    public UndefinedTagException(String str) {
        super(str);
    }

    public UndefinedTagException(int i) {
        super("Code: (" + i + ")");
    }
}
