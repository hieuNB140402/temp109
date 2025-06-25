package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.ByteCountInputStream */

public class ByteCountInputStream extends ByteOrderInputStream {
    private int index = -1;
    private long len = 0;
    private int[] size;

    public ByteCountInputStream(InputStream inputStream, boolean z, int i) {
        super(inputStream, z);
        this.size = new int[i];
    }

    @Override // com.allreader.office.allofficefilereader.thirdpart.emf.p017io.DecompressableInputStream, java.io.InputStream
    public int read() throws IOException {
        int i = this.index;
        if (i == -1) {
            this.len++;
            return super.read();
        }
        int[] iArr = this.size;
        if (iArr[i] <= 0) {
            return -1;
        }
        iArr[i] = iArr[i] - 1;
        this.len++;
        return super.read();
    }

    public void pushBuffer(int i) {
        int i2 = this.index;
        int[] iArr = this.size;
        if (i2 >= iArr.length - 1) {
            PrintStream printStream = System.err;
            printStream.println("ByteCountInputStream: trying to push more buffers than stackDepth: " + this.size.length);
            return;
        }
        if (i2 >= 0) {
            if (iArr[i2] < i) {
                PrintStream printStream2 = System.err;
                printStream2.println("ByteCountInputStream: trying to set a length: " + i + ", longer than the underlying buffer: " + this.size[this.index]);
                return;
            }
            iArr[i2] = iArr[i2] - i;
        }
        int i3 = i2 + 1;
        this.index = i3;
        iArr[i3] = i;
    }

    public byte[] popBuffer() throws IOException {
        int i = this.index;
        if (i < 0) {
            return null;
        }
        int i2 = this.size[i];
        if (i2 > 0) {
            return readByte(i2);
        }
        if (i2 < 0) {
            System.err.println("ByteCountInputStream: Internal Error");
        }
        this.index--;
        return null;
    }

    public long getLength() {
        int i = this.index;
        return i >= 0 ? (long) this.size[i] : this.len;
    }
}
