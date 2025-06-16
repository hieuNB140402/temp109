package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.RoutedInputStream */

public class RoutedInputStream extends DecodingInputStream {
    private static final int CLOSED = 5;
    private static final int CLOSING = 4;
    private static final int ROUTED = 3;
    private static final int ROUTEFOUND = 1;
    private static final int ROUTEINFORM = 2;
    private static final int UNROUTED = 0;
    private byte[] buffer = new byte[20];
    private int eob = 0;

    /* renamed from: in */
    private InputStream f342in;
    private int index = 0;
    private Map listeners = new HashMap();
    private Map routes = new HashMap();
    private int sob = -1;
    private byte[] start;
    private int state = 0;

    public RoutedInputStream(InputStream inputStream) {
        this.f342in = inputStream;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        while (true) {
            int i = this.state;
            if (i == 1) {
                int i2 = this.sob;
                if (i2 == this.index) {
                    this.state = 2;
                } else {
                    byte[] bArr = this.buffer;
                    byte b = bArr[i2];
                    this.sob = (i2 + 1) % bArr.length;
                    return b;
                }
            } else if (i == 2) {
                this.state = 3;
                byte[] bArr2 = this.start;
                ((RouteListener) this.listeners.get(this.start)).routeFound(new Route(bArr2, (byte[]) this.routes.get(bArr2)));
                this.state = 0;
                int i3 = this.sob;
                if (i3 == this.eob) {
                    this.sob = -1;
                    this.eob = 0;
                } else {
                    byte[] bArr3 = this.buffer;
                    byte b2 = bArr3[i3];
                    this.sob = (i3 + 1) % bArr3.length;
                    return b2;
                }
            } else if (i == 3) {
                int i4 = this.sob;
                if (i4 == this.eob) {
                    int read = this.f342in.read();
                    if (read >= 0) {
                        return read;
                    }
                    this.state = 5;
                } else {
                    byte[] bArr4 = this.buffer;
                    byte b3 = bArr4[i4];
                    this.sob = (i4 + 1) % bArr4.length;
                    return b3;
                }
            } else if (i != 4) {
                if (i == 5) {
                    return -1;
                }
                while (true) {
                    int i5 = this.sob;
                    if (i5 != this.eob) {
                        if (i5 < 0) {
                            this.sob = 0;
                        }
                        int read2 = this.f342in.read();
                        if (read2 < 0) {
                            this.state = 4;
                            break;
                        }
                        byte[] bArr5 = this.buffer;
                        int i6 = this.eob;
                        bArr5[i6] = (byte) read2;
                        this.eob = (i6 + 1) % bArr5.length;
                        Iterator it = this.routes.keySet().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                byte[] bArr6 = (byte[]) it.next();
                                this.start = bArr6;
                                int i7 = this.eob;
                                byte[] bArr7 = this.buffer;
                                int length = ((i7 + bArr7.length) - bArr6.length) % bArr7.length;
                                this.index = length;
                                if (equals(bArr6, bArr7, length)) {
                                    this.state = 1;
                                    break;
                                }
                            }
                        }
                    } else {
                        byte[] bArr8 = this.buffer;
                        byte b4 = bArr8[i5];
                        this.sob = (i5 + 1) % bArr8.length;
                        return b4;
                    }
                }
            } else {
                int i8 = this.sob;
                if (i8 == this.eob) {
                    this.state = 5;
                } else {
                    byte[] bArr9 = this.buffer;
                    byte b5 = bArr9[i8];
                    this.sob = (i8 + 1) % bArr9.length;
                    return b5;
                }
            }
        }
    }

    public void addRoute(String str, String str2, RouteListener routeListener) {
        addRoute(str.getBytes(), str2 == null ? null : str2.getBytes(), routeListener);
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x0010  */
    public void addRoute(byte[] bArr, byte[] bArr2, RouteListener routeListener) {
        for (Object bArr3 : this.routes.keySet()) {
            String str = new String((byte[]) bArr3);
            String str2 = new String(bArr);
            if (str.startsWith(str2) || str2.startsWith(str)) {
                throw new IllegalArgumentException("Route '" + str2 + "' cannot be added since it overlaps with '" + str + "'.");
            }

        }
        this.routes.put(bArr, bArr2);
        this.listeners.put(bArr, routeListener);
        int length = bArr.length;
        byte[] bArr4 = this.buffer;
        if (length > bArr4.length - 1) {
            byte[] bArr5 = new byte[(bArr.length + 1)];
            System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
            this.buffer = bArr5;
        }
    }

    
    public static boolean equals(byte[] bArr, byte[] bArr2, int i) {
        for (int length = bArr.length - 1; length > 0; length--) {
            if (bArr2[((bArr2.length + i) + length) % bArr2.length] != bArr[length]) {
                return false;
            }
        }
        if (bArr2[(i + bArr2.length) % bArr2.length] == bArr[0]) {
            return true;
        }
        return false;
    }

    /* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.RoutedInputStream$Route */
    
    public class Route extends InputStream {
        private byte[] buffer;
        private boolean closed;
        private byte[] end;
        private int index;
        private byte[] start;

        public Route(byte[] bArr, byte[] bArr2) {
            this.start = bArr;
            this.end = bArr2;
            if (bArr2 != null) {
                this.buffer = new byte[bArr2.length];
            }
            this.index = 0;
            this.closed = false;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (this.closed) {
                return -1;
            }
            int read = RoutedInputStream.this.read();
            if (read < 0) {
                this.closed = true;
                return read;
            }
            byte[] bArr = this.end;
            if (bArr == null) {
                return read;
            }
            byte[] bArr2 = this.buffer;
            int i = this.index;
            bArr2[i] = (byte) read;
            int length = (i + 1) % bArr2.length;
            this.index = length;
            this.closed = RoutedInputStream.equals(bArr, bArr2, length);
            return read;
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            do {
            } while (read() >= 0);
            this.closed = true;
        }

        public byte[] getStart() {
            return this.start;
        }

        public byte[] getEnd() {
            return this.end;
        }
    }
}
