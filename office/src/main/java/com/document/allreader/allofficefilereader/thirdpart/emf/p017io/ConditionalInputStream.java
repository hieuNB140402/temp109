package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.ConditionalInputStream */

public class ConditionalInputStream extends DecodingInputStream {
    private int[] buffer = new int[4096];
    private Properties defines;
    private boolean escape;

    /* renamed from: in */
    private InputStream f324in;
    private int index;
    private int len;
    private int nesting;

    /* renamed from: ok */
    private boolean[] f325ok = new boolean[50];

    public ConditionalInputStream(InputStream inputStream, Properties properties) {
        this.f324in = inputStream;
        this.defines = properties;
        this.nesting = 0;
        this.escape = false;
        this.index = 0;
        this.len = 0;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i;
        int i2 = this.index;
        if (i2 < this.len) {
            i = this.buffer[i2];
            this.index = i2 + 1;
        } else {
            i = this.f324in.read();
        }
        if (i < 0) {
            return -1;
        }
        int i3 = 32;
        int i4 = 64;
        if (i == 92) {
            int read = this.f324in.read();
            if (read == 64) {
                this.escape = true;
                i = 32;
            }
            this.buffer[0] = read;
            this.index = 0;
            this.len = 1;
        }
        if (i == 64) {
            if (this.escape) {
                this.escape = false;
            } else {
                this.index = 0;
                StringBuffer stringBuffer = new StringBuffer();
                int read2 = this.f324in.read();
                while (read2 >= 0) {
                    char c = (char) read2;
                    if (Character.isWhitespace(c)) {
                        break;
                    }
                    stringBuffer.append(c);
                    this.buffer[this.index] = read2;
                    read2 = this.f324in.read();
                    this.index++;
                }
                int[] iArr = this.buffer;
                int i5 = this.index;
                iArr[i5] = read2;
                this.index = i5 + 1;
                String stringBuffer2 = stringBuffer.toString();
                if (stringBuffer2.equals("ifdef") || stringBuffer2.equals("ifndef")) {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    int read3 = this.f324in.read();
                    while (read3 >= 0 && Character.isWhitespace((char) read3)) {
                        this.buffer[this.index] = read3;
                        read3 = this.f324in.read();
                        this.index++;
                    }
                    while (read3 >= 0) {
                        char c2 = (char) read3;
                        if (Character.isWhitespace(c2)) {
                            break;
                        }
                        stringBuffer3.append(c2);
                        this.buffer[this.index] = read3;
                        read3 = this.f324in.read();
                        this.index++;
                    }
                    int[] iArr2 = this.buffer;
                    int i6 = this.index;
                    iArr2[i6] = read3;
                    this.index = i6 + 1;
                    if (this.defines.getProperty(stringBuffer3.toString()) != null) {
                        boolean[] zArr = this.f325ok;
                        int i7 = this.nesting;
                        zArr[i7] = (i7 <= 0 || zArr[i7 + -1]) && stringBuffer2.equals("ifdef");
                    } else {
                        boolean[] zArr2 = this.f325ok;
                        int i8 = this.nesting;
                        zArr2[i8] = (i8 <= 0 || zArr2[i8 + -1]) && stringBuffer2.equals("ifndef");
                    }
                    this.nesting++;
                    replaceBufferWithWhitespace(this.index);
                } else if (stringBuffer2.equals("else")) {
                    int i9 = this.nesting;
                    if (i9 > 0) {
                        boolean[] zArr3 = this.f325ok;
                        zArr3[i9 - 1] = (i9 <= 1 || zArr3[i9 + -2]) && !zArr3[i9 - 1];
                        replaceBufferWithWhitespace(this.index);
                    } else {
                        throw new RuntimeException("@else without corresponding @ifdef");
                    }
                } else {
                    if (stringBuffer2.equals("endif")) {
                        int i10 = this.nesting;
                        if (i10 > 0) {
                            this.nesting = i10 - 1;
                            replaceBufferWithWhitespace(this.index);
                        } else {
                            throw new RuntimeException("@endif without corresponding @ifdef");
                        }
                    }
                    this.len = this.index;
                    this.index = 0;
                    i = i4;
                }
                i4 = 32;
                this.len = this.index;
                this.index = 0;
                i = i4;
            }
        }
        int i11 = this.nesting;
        if (i11 <= 0 || this.f325ok[i11 - 1] || Character.isWhitespace((char) i)) {
            i3 = i;
        }
        return i3 & 255;
    }

    private void replaceBufferWithWhitespace(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (!Character.isWhitespace((char) this.buffer[i2])) {
                this.buffer[i2] = 32;
            }
        }
    }
}
