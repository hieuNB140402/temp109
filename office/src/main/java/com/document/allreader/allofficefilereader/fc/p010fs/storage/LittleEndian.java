package com.document.allreader.allofficefilereader.fc.p010fs.storage;

/* renamed from: com.allreader.office.allofficefilereader.fc.fs.storage.LittleEndian */

public class LittleEndian {
    public static final int BYTE_SIZE = 1;
    public static final int DOUBLE_SIZE = 8;
    public static final int INT_SIZE = 4;
    public static final int LONG_SIZE = 8;
    public static final int SHORT_SIZE = 2;

    public static short getShort(byte[] bArr, int i) {
        return (short) (((bArr[i + 1] & 255) << 8) + ((bArr[i] & 255) << 0));
    }

    public static int getUShort(byte[] bArr, int i) {
        return ((bArr[i + 1] & 255) << 8) + ((bArr[i] & 255) << 0);
    }

    public static short getShort(byte[] bArr) {
        return getShort(bArr, 0);
    }

    public static int getUShort(byte[] bArr) {
        return getUShort(bArr, 0);
    }

    public static int getInt(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return ((bArr[i3 + 1] & 255) << 24) + ((bArr[i3] & 255) << 16) + ((bArr[i2] & 255) << 8) + ((bArr[i] & 255) << 0);
    }

    public static int getInt(byte[] bArr) {
        return getInt(bArr, 0);
    }

    public static long getUInt(byte[] bArr, int i) {
        return ((long) getInt(bArr, i)) & 4294967295L;
    }

    public static long getUInt(byte[] bArr) {
        return getUInt(bArr, 0);
    }

    public static long getLong(byte[] bArr, int i) {
        long j = 0;
        for (int i2 = (i + 8) - 1; i2 >= i; i2--) {
            j = (j << 8) | ((long) (bArr[i2] & 255));
        }
        return j;
    }
}
