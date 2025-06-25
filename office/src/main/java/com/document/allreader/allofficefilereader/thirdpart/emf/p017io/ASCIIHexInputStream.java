package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.ASCIIHexInputStream */

public class ASCIIHexInputStream extends DecodingInputStream {
    private boolean endReached;
    private boolean ignoreIllegalChars;

    /* renamed from: in */
    private InputStream f321in;
    private int lineNo;
    private int prev;

    public ASCIIHexInputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    public ASCIIHexInputStream(InputStream inputStream, boolean z) {
        this.f321in = inputStream;
        this.ignoreIllegalChars = z;
        this.endReached = false;
        this.prev = -1;
        this.lineNo = 1;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int readPart;
        if (this.endReached || (readPart = readPart()) == -1) {
            return -1;
        }
        int readPart2 = readPart();
        if (readPart2 == -1) {
            readPart2 = 0;
        }
        return ((readPart << 4) | readPart2) & 255;
    }

    public int getLineNo() {
        return this.lineNo;
    }

    
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0056, code lost:
        return 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0057, code lost:
        return 12;
     */
    private int readPart() throws IOException, EncodingException {
        while (true) {
            int read = this.f321in.read();
            if (read != -1) {
                if (!(read == 0 || read == 9)) {
                    if (read == 10) {
                        if (this.prev != 13) {
                            this.lineNo++;
                        }
                        this.prev = read;
                    } else if (read != 12) {
                        if (read == 13) {
                            this.lineNo++;
                            this.prev = read;
                        } else if (read != 32) {
                            if (read != 62) {
                                switch (read) {
                                    case 48:
                                        return 0;
                                    case 49:
                                        return 1;
                                    case 50:
                                        return 2;
                                    case 51:
                                        return 3;
                                    case 52:
                                        return 4;
                                    case 53:
                                        return 5;
                                    case 54:
                                        return 6;
                                    case 55:
                                        return 7;
                                    case 56:
                                        return 8;
                                    case 57:
                                        return 9;
                                    default:
                                        switch (read) {
                                            case 65:
                                                break;
                                            case 66:
                                                return 11;
                                            case 67:
                                                break;
                                            case 68:
                                                break;
                                            case 69:
                                                return 14;
                                            case 70:
                                                return 15;
                                            default:
                                                switch (read) {
                                                    case 97:
                                                        break;
                                                    case 98:
                                                        return 11;
                                                    case 99:
                                                        break;
                                                    case 100:
                                                        break;
                                                    case 101:
                                                        return 14;
                                                    case 102:
                                                        return 15;
                                                    default:
                                                        if (this.ignoreIllegalChars) {
                                                            this.prev = read;
                                                            continue;
                                                        } else {
                                                            throw new EncodingException("Illegal char " + read + " in HexStream");
                                                        }
                                                }
                                        }
                                }
                            } else {
                                this.endReached = true;
                                return -1;
                            }
                        }
                    }
                }
                this.prev = read;
            } else {
                this.endReached = true;
                if (this.ignoreIllegalChars) {
                    return -1;
                }
                throw new EncodingException("missing '>' at end of ASCII HEX stream");
            }
        }

    }
}
