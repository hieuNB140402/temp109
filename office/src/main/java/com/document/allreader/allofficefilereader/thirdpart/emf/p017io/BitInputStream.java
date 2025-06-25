package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.BitInputStream */

public class BitInputStream extends DecompressableInputStream {
    protected static final int[] BIT_MASK = new int[8];
    protected static final int[] FIELD_MASK = new int[8];
    protected static final int MASK_SIZE = 8;
    protected static final int ONES = -1;
    protected static final int ZERO = 0;
    private int bits = 0;
    private int validBits = 0;

    static {
        int i = 1;
        int i2 = 1;
        for (int i3 = 0; i3 < 8; i3++) {
            BIT_MASK[i3] = i;
            FIELD_MASK[i3] = i2;
            i <<= 1;
            i2 = (i2 << 1) + 1;
        }
    }

    public BitInputStream(InputStream inputStream) {
        super(inputStream);
    }

    protected void fetchByte() throws IOException {
        int read = read();
        this.bits = read;
        if (read >= 0) {
            this.validBits = 8;
            return;
        }
        throw new EOFException();
    }

    public void byteAlign() {
        this.validBits = 0;
    }

    public boolean readBitFlag() throws IOException {
        if (this.validBits == 0) {
            fetchByte();
        }
        int i = this.bits;
        int[] iArr = BIT_MASK;
        int i2 = this.validBits - 1;
        this.validBits = i2;
        return (i & iArr[i2]) != 0;
    }

    public long readSBits(int i) throws IOException {
        if (i == 0) {
            return 0;
        }
        int i2 = i - 1;
        return ((long) ((readBitFlag() ? -1 : 0) << i2)) | readUBits(i2);
    }

    public float readFBits(int i) throws IOException {
        if (i == 0) {
            return 0.0f;
        }
        return ((float) readSBits(i)) / 4096.0f;
    }

    public long readUBits(int i) throws IOException {
        long j = 0;
        while (i > 0) {
            if (this.validBits == 0) {
                fetchByte();
            }
            int i2 = this.validBits;
            int i3 = i > i2 ? i2 : i;
            this.validBits = i2 - i3;
            i -= i3;
            j = (j << i3) | ((long) ((this.bits >> (i2 - i3)) & FIELD_MASK[i3 - 1]));
        }
        return j;
    }
}
