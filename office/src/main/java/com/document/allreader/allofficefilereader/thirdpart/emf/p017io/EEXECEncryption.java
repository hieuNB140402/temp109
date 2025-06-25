package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.EEXECEncryption */

public class EEXECEncryption extends OutputStream implements EEXECConstants {

    /* renamed from: c1 */
    private int f337c1;

    /* renamed from: c2 */
    private int f338c2;
    private boolean first;

    /* renamed from: n */
    private int f339n;
    private OutputStream out;

    /* renamed from: r */
    private int f340r;

    public EEXECEncryption(OutputStream outputStream) {
        this(outputStream, 55665, 4);
    }

    public EEXECEncryption(OutputStream outputStream, int i) {
        this(outputStream, i, 4);
    }

    public EEXECEncryption(OutputStream outputStream, int i, int i2) {
        this.first = true;
        this.out = outputStream;
        this.f337c1 = 52845;
        this.f338c2 = 22719;
        this.f340r = i;
        this.f339n = i2;
    }

    private int encrypt(int i) {
        int i2 = this.f340r;
        int i3 = (i ^ (i2 >>> 8)) % 256;
        this.f340r = (((i2 + i3) * this.f337c1) + this.f338c2) % 65536;
        return i3;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        if (this.first) {
            for (int i2 = 0; i2 < this.f339n; i2++) {
                this.out.write(encrypt(0));
            }
            this.first = false;
        }
        this.out.write(encrypt(i));
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        super.flush();
        this.out.flush();
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        flush();
        super.close();
        this.out.close();
    }

    /* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.EEXECEncryption$IntOutputStream */

    private static class IntOutputStream extends OutputStream {
        int[] chars;

        /* renamed from: i */
        int f341i;

        private IntOutputStream(int i) {
            this.chars = new int[i];
            this.f341i = 0;
        }

        @Override // java.io.OutputStream
        public void write(int i) {
            int[] iArr = this.chars;
            int i2 = this.f341i;
            this.f341i = i2 + 1;
            iArr[i2] = i;
        }

        
        public int[] getInts() {
            return this.chars;
        }
    }

    public static int[] encryptString(int[] iArr, int i, int i2) throws IOException {
        IntOutputStream intOutputStream = new IntOutputStream(iArr.length + 4);
        EEXECEncryption eEXECEncryption = new EEXECEncryption(intOutputStream, i, i2);
        for (int i3 : iArr) {
            eEXECEncryption.write(i3);
        }
        return intOutputStream.getInts();
    }
}
