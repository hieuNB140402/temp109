package com.document.allreader.allofficefilereader.fc.p010fs.storage;

/* renamed from: com.allreader.office.allofficefilereader.fc.fs.storage.IntList */

public class IntList {
    private static final int _default_size = 128;
    private int[] _array;
    private int _limit;
    private int fillval;

    public IntList() {
        this(128, 0);
    }

    public IntList(int i, int i2) {
        this.fillval = 0;
        this._array = new int[i];
        this._limit = 0;
    }

    private void fillArray(int i, int[] iArr, int i2) {
        while (i2 < iArr.length) {
            iArr[i2] = i;
            i2++;
        }
    }

    public boolean add(int i) {
        int i2 = this._limit;
        if (i2 == this._array.length) {
            growArray(i2 * 2);
        }
        int[] iArr = this._array;
        int i3 = this._limit;
        this._limit = i3 + 1;
        iArr[i3] = i;
        return true;
    }

    public int get(int i) {
        if (i >= this._limit) {
            return -2;
        }
        return this._array[i];
    }

    public int size() {
        return this._limit;
    }

    private void growArray(int i) {
        int[] iArr = this._array;
        if (i == iArr.length) {
            i++;
        }
        int[] iArr2 = new int[i];
        int i2 = this.fillval;
        if (i2 != 0) {
            fillArray(i2, iArr2, iArr.length);
        }
        System.arraycopy(this._array, 0, iArr2, 0, this._limit);
        this._array = iArr2;
    }
}
