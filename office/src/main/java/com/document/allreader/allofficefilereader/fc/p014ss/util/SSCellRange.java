package com.document.allreader.allofficefilereader.fc.p014ss.util;

import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.CellRange;
import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.ICell;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Internal
/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.SSCellRange */

public final class SSCellRange<K extends ICell> implements CellRange<K> {
    private final int _firstColumn;
    private final int _firstRow;
    private final K[] _flattenedArray;
    private final int _height;
    private final int _width;

    private SSCellRange(int i, int i2, int i3, int i4, K[] kArr) {
        this._firstRow = i;
        this._firstColumn = i2;
        this._height = i3;
        this._width = i4;
        this._flattenedArray = kArr;
    }

    public static <B extends ICell> SSCellRange<B> create(int i, int i2, int i3, int i4, List<B> list, Class<B> cls) {
        int size = list.size();
        if (i3 * i4 == size) {
            ICell[] iCellArr = (ICell[]) Array.newInstance((Class<?>) cls, size);
            list.toArray(iCellArr);
            return new SSCellRange(i, i2, i3, i4, iCellArr);
        }
        throw new IllegalArgumentException("Array size mismatch.");
    }

    @Override // fc4ss.usermodel.CellRange
    public int getHeight() {
        return this._height;
    }

    @Override // fc4ss.usermodel.CellRange
    public int getWidth() {
        return this._width;
    }

    @Override // fc4ss.usermodel.CellRange
    public int size() {
        return this._height * this._width;
    }

    @Override // fc4ss.usermodel.CellRange
    public String getReferenceText() {
        int i = this._firstRow;
        int i2 = this._firstColumn;
        return new HSSFCellRangeAddress(i, (this._height + i) - 1, i2, (this._width + i2) - 1).formatAsString();
    }

    @Override // fc4ss.usermodel.CellRange
    public K getTopLeftCell() {
        return this._flattenedArray[0];
    }

    @Override // fc4ss.usermodel.CellRange
    public K getCell(int i, int i2) {
        int i3;
        if (i < 0 || i >= this._height) {
            StringBuilder sb = new StringBuilder();
            sb.append("Specified row ");
            sb.append(i);
            sb.append(" is outside the allowable range (0..");
            sb.append(this._height - 1);
            sb.append(").");
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        } else if (i2 >= 0 && i2 < (i3 = this._width)) {
            return this._flattenedArray[(i3 * i) + i2];
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Specified colummn ");
            sb2.append(i2);
            sb2.append(" is outside the allowable range (0..");
            sb2.append(this._width - 1);
            sb2.append(").");
            throw new ArrayIndexOutOfBoundsException(sb2.toString());
        }
    }

    @Override // fc4ss.usermodel.CellRange
    public K[] getFlattenedCells() {
        return (K[]) ((ICell[]) this._flattenedArray.clone());
    }

    @Override // fc4ss.usermodel.CellRange
    public K[][] getCells() {
        Class<?> cls = this._flattenedArray.getClass();
        K[][] kArr = (K[][]) ((ICell[][]) Array.newInstance(cls, this._height));
        Class<?> componentType = cls.getComponentType();
        for (int i = this._height - 1; i >= 0; i--) {
            int i2 = this._width;
            System.arraycopy(this._flattenedArray, i2 * i, (ICell[]) Array.newInstance(componentType, this._width), 0, i2);
        }
        return kArr;
    }

    @Override // fc4ss.usermodel.CellRange, java.lang.Iterable
    public Iterator<K> iterator() {
        return new ArrayIterator(this._flattenedArray);
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.SSCellRange$ArrayIterator */

    private static final class ArrayIterator<D> implements Iterator<D> {
        private final D[] _array;
        private int _index = 0;

        public ArrayIterator(D[] dArr) {
            this._array = dArr;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this._index < this._array.length;
        }

        @Override // java.util.Iterator
        public D next() {
            int i = this._index;
            D[] dArr = this._array;
            if (i < dArr.length) {
                this._index = i + 1;
                return dArr[i];
            }
            throw new NoSuchElementException(String.valueOf(this._index));
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove cells from this CellRange.");
        }
    }
}
