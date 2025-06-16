package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.Field;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.Base64InputStream */

public class Base64InputStream extends DecodingInputStream {
    private static final int CARRIAGERETURN = -4;
    private static final int EQUALS = -5;
    private static final int ILLEGAL = -1;
    private static final int LINEFEED = -3;
    private static final byte[] base64toInt = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -3, -1, -1, -4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, Field.USERADDRESS, -1, -1, -1, Field.BARCODE, Field.AUTONUMOUT, Field.AUTONUMLGL, Field.AUTONUM, Field.IMPORT, 56, Field.SYMBOL, 58, 59, 60, 61, -1, -1, -1, -5, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, Field.NUMWORDS, Field.NUMCHARS, 29, 30, 31, 32, 33, 34, Field.QUOTE, 36, Field.PAGEREF, Field.ASK, Field.FILLIN, Field.DATA, 41, 42, 43, 44, Field.DDE, Field.DDEAUTO, Field.GLOSSARY, 48, 49, Field.GOTOBUTTON, Field.MACROBUTTON, -1, -1, -1, -1, -1};

    /* renamed from: b */
    private int[] f322b = new int[3];
    private int bIndex;
    private int bLength;
    private boolean endReached;

    /* renamed from: in */
    private InputStream f323in;
    private int lineNo;

    public Base64InputStream(InputStream inputStream) {
        this.f323in = inputStream;
        this.endReached = false;
        this.bIndex = 0;
        this.bLength = 0;
        this.lineNo = 1;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.bIndex >= this.bLength) {
            if (this.endReached) {
                return -1;
            }
            int readTuple = readTuple();
            this.bLength = readTuple;
            if (readTuple <= 0) {
                return -1;
            }
            this.bIndex = 0;
        }
        int[] iArr = this.f322b;
        int i = this.bIndex;
        int i2 = iArr[i];
        this.bIndex = i + 1;
        if (i2 >= 0 && i2 <= 255) {
            return i2;
        }
        throw new EncodingException(getClass() + " internal error, byte output out of range: " + i2);
    }

    public int getLineNo() {
        return this.lineNo;
    }

    private int readTuple() throws IOException, EncodingException {
        byte[] bArr = new byte[4];
        if (this.endReached) {
            return 0;
        }
        int i = 0;
        int i2 = 0;
        byte b = 0;
        while (i < 4) {
            int read = this.f323in.read();
            if (read < 0) {
                this.endReached = true;
                if (i2 == 0) {
                    return 0;
                }
                throw new EncodingException("Improperly padded Base64 Input.");
            }
            byte b2 = base64toInt[read & 127];
            if (b2 != -5) {
                if (b2 == -4) {
                    this.lineNo++;
                } else if (b2 != -3) {
                    if (b2 != -1) {
                        bArr[i2] = (byte) (b2 & 255);
                        i2++;
                    } else if (read < 0) {
                        throw new EncodingException("Illegal character in Base64 encoding '" + read + "'.");
                    }
                } else if (b != -4) {
                    this.lineNo++;
                }
                b = b2;
            }
            i++;
            b = b2;
        }
        if (i2 == 2) {
            this.f322b[0] = (((bArr[0] << 18) | (bArr[1] << 12)) >>> 16) & 255;
            return 1;
        } else if (i2 == 3) {
            int i3 = (bArr[0] << 18) | (bArr[1] << 12) | (bArr[2] << 6);
            int[] iArr = this.f322b;
            iArr[0] = (i3 >>> 16) & 255;
            iArr[1] = (i3 >>> 8) & 255;
            return 2;
        } else if (i2 == 4) {
            int i4 = (bArr[0] << 18) | (bArr[1] << 12) | (bArr[2] << 6) | bArr[3];
            int[] iArr2 = this.f322b;
            iArr2[0] = (i4 >>> 16) & 255;
            iArr2[1] = (i4 >>> 8) & 255;
            iArr2[2] = i4 & 255;
            return 3;
        } else {
            throw new EncodingException("Base64InputStream: internal error.");
        }
    }
}
