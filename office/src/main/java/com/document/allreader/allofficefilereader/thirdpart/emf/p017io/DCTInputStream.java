package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.DCTInputStream */

public class DCTInputStream extends FilterInputStream {
    public DCTInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        throw new IOException(getClass() + ": read() not implemented, use readImage().");
    }
}
