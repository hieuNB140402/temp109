package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.EEXECDecryption */

public class EEXECDecryption extends InputStream implements EEXECConstants {

    /* renamed from: c1 */
    private int f332c1;

    /* renamed from: c2 */
    private int f333c2;
    private boolean first;

    /* renamed from: in */
    private InputStream f334in;

    /* renamed from: n */
    private int f335n;

    /* renamed from: r */
    private int f336r;

    public EEXECDecryption(InputStream inputStream) {
        this(inputStream, 55665, 4);
    }

    public EEXECDecryption(InputStream inputStream, int i, int i2) {
        this.first = true;
        this.f334in = inputStream;
        this.f336r = i;
        this.f335n = i2;
        this.f332c1 = 52845;
        this.f333c2 = 22719;
    }

    private int decrypt(int i) {
        int i2 = this.f336r;
        int i3 = ((i2 >>> 8) ^ i) % 256;
        this.f336r = (((i + i2) * this.f332c1) + this.f333c2) % 65536;
        return i3;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.first) {
            int i = this.f335n;
            byte[] bArr = new byte[i];
            boolean z = false;
            for (int i2 = 0; i2 < i; i2++) {
                int read = this.f334in.read();
                bArr[i2] = (byte) read;
                if (!Character.isDigit((char) read) && ((read < 97 || read > 102) && (read < 65 || read > 70))) {
                    z = true;
                }
            }
            if (z) {
                for (int i3 = 0; i3 < i; i3++) {
                    decrypt(bArr[i3] & 255);
                }
            } else {
                ASCIIHexInputStream aSCIIHexInputStream = new ASCIIHexInputStream(new ByteArrayInputStream(bArr), true);
                int i4 = 0;
                while (true) {
                    int read2 = aSCIIHexInputStream.read();
                    if (read2 < 0) {
                        break;
                    }
                    decrypt(read2);
                    i4++;
                }
                this.f334in = new ASCIIHexInputStream(this.f334in, true);
                while (i4 < this.f335n) {
                    decrypt(this.f334in.read());
                    i4++;
                }
            }
            this.first = false;
        }
        int read3 = this.f334in.read();
        if (read3 == -1) {
            return -1;
        }
        return decrypt(read3);
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.f334in.close();
    }
}
