package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.DecompressableInputStream */

public class DecompressableInputStream extends DecodingInputStream {

    /* renamed from: b */
    private byte[] f326b = null;
    private boolean decompress;

    /* renamed from: i */
    private int f327i = 0;
    private InflaterInputStream iis;

    /* renamed from: in */
    private InputStream f328in;
    private int len = 0;

    public DecompressableInputStream(InputStream inputStream) {
        this.f328in = inputStream;
        this.decompress = false;
        try {
            int available = inputStream.available();
            this.len = available;
            byte[] bArr = new byte[available];
            this.f326b = bArr;
            this.f328in.read(bArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i = this.f327i;
        if (i >= this.len) {
            return -1;
        }
        byte[] bArr = this.f326b;
        this.f327i = i + 1;
        return bArr[i] & 255;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        this.f327i = (int) (((long) this.f327i) + j);
        return j;
    }

    public void startDecompressing() throws IOException {
        this.decompress = true;
        this.iis = new InflaterInputStream(this.f328in);
    }
}
