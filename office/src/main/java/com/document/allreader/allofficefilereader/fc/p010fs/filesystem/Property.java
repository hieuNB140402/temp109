package com.document.allreader.allofficefilereader.fc.p010fs.filesystem;

import com.document.allreader.allofficefilereader.fc.p010fs.storage.LittleEndian;
import com.document.allreader.allofficefilereader.fc.p010fs.storage.RawDataBlock;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.allreader.office.allofficefilereader.fc.fs.filesystem.Property */

public class Property {
    private static final int CHILD_PROPERTY_OFFSET = 76;
    public static final byte DIRECTORY_TYPE = 1;
    public static final byte DOCUMENT_TYPE = 2;
    private static final int NAME_SIZE_OFFSET = 64;
    private static final int NEXT_PROPERTY_OFFSET = 72;
    private static final int PREVIOUS_PROPERTY_OFFSET = 68;
    public static final int PROPERTY_TYPE_OFFSET = 66;
    public static final byte ROOT_TYPE = 5;
    private static final int SIZE_OFFSET = 120;
    private static final int START_BLOCK_OFFSET = 116;
    protected static final byte _NODE_BLACK = 1;
    protected static final byte _NODE_RED = 0;
    protected static final int _NO_INDEX = -1;
    private static int _big_block_minimum_bytes = 4096;
    private int _chlid_property;
    private String _name;
    private short _name_size;
    private int _next_property;
    private int _previous_property;
    private byte _property_type;
    private int _size;
    private int _start_block;
    private int blockSize;
    private RawDataBlock[] blocks;
    private byte[] documentRawData;
    protected Map<String, Property> properties = new HashMap();

    public Property(int i, byte[] bArr, int i2) {
        this._name_size = LittleEndian.getShort(bArr, i2 + 64);
        this._previous_property = LittleEndian.getShort(bArr, i2 + 68);
        this._next_property = LittleEndian.getShort(bArr, i2 + 72);
        this._chlid_property = LittleEndian.getShort(bArr, i2 + 76);
        this._start_block = LittleEndian.getInt(bArr, i2 + 116);
        this._size = LittleEndian.getInt(bArr, i2 + 120);
        byte b = bArr[i2 + 66];
        this._property_type = b;
        int i3 = (this._name_size / 2) - 1;
        if (i3 >= 1) {
            char[] cArr = new char[i3];
            int i4 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                cArr[i5] = (char) LittleEndian.getShort(bArr, i4 + i2);
                i4 += 2;
            }
            this._name = new String(cArr, 0, i3);
        } else if (b == 5) {
            this._name = "Root Entry";
        } else {
            this._name = "aaa";
        }
    }

    public byte[] getDocumentRawData() {
        return this.documentRawData;
    }

    public void setDocumentRawData(byte[] bArr) {
        this.documentRawData = bArr;
    }

    public int getStartBlock() {
        return this._start_block;
    }

    public int getSize() {
        return this._size;
    }

    public int getPreviousPropertyIndex() {
        return this._previous_property;
    }

    public int getNextPropertyIndex() {
        return this._next_property;
    }

    public int getChildPropertyIndex() {
        return this._chlid_property;
    }

    public boolean shouldUseSmallBlocks() {
        return getSize() < _big_block_minimum_bytes;
    }

    public String getName() {
        return this._name;
    }

    public long getPropertyRawDataSize() {
        int length;
        RawDataBlock[] rawDataBlockArr = this.blocks;
        if (rawDataBlockArr != null) {
            length = rawDataBlockArr[0].getData().length * this.blocks.length;
        } else {
            length = this.documentRawData.length;
        }
        return (long) length;
    }

    public RawDataBlock[] getBlocks() {
        return this.blocks;
    }

    public void setBlocks(RawDataBlock[] rawDataBlockArr) {
        this.blocks = rawDataBlockArr;
        this.blockSize = rawDataBlockArr[0].getData().length;
    }

    public boolean isDocument() {
        return this._property_type == 2;
    }

    public boolean isDirectory() {
        return this._property_type == 1;
    }

    public boolean isRoot() {
        return this._property_type == 5;
    }

    public int getUShort(int i) {
        return (getByteForOffset(i + 1) << 8) + (getByteForOffset(i) << 0);
    }

    public long getUInt(int i) {
        return ((long) getInt(i)) & 4294967295L;
    }

    public int getInt(int i) {
        int byteForOffset = getByteForOffset(i);
        int byteForOffset2 = getByteForOffset(i + 1);
        return (getByteForOffset(i + 3) << 24) + (getByteForOffset(i + 2) << 16) + (byteForOffset2 << 8) + (byteForOffset << 0);
    }

    public long getLong(int i) {
        long j = 0;
        for (int i2 = (i + 8) - 1; i2 >= i; i2--) {
            j = (j << 8) | ((long) (getByteForOffset(i2) & 255));
        }
        return j;
    }

    public void writeByte(OutputStream outputStream, int i, int i2) throws IOException {
        byte[] bArr = new byte[Math.min(i2, this.blockSize * 16)];
        int blockIndexForOffset = getBlockIndexForOffset(i);
        int i3 = this.blockSize;
        int i4 = i - (i3 * blockIndexForOffset);
        int min = Math.min(i2, i3 - i4);
        System.arraycopy(this.blocks[blockIndexForOffset].getData(), i4, bArr, 0, min);
        int i5 = 1;
        while (min <= i2) {
            RawDataBlock[] rawDataBlockArr = this.blocks;
            if (blockIndexForOffset >= rawDataBlockArr.length) {
                return;
            }
            if (i5 < 16) {
                blockIndexForOffset++;
                i5++;
                if (this.blockSize + min > i2) {
                    if (i2 > min && blockIndexForOffset < rawDataBlockArr.length) {
                        System.arraycopy(rawDataBlockArr[blockIndexForOffset].getData(), 0, bArr, min, i2 - min);
                    }
                    outputStream.write(bArr, 0, i2);
                    return;
                }
                System.arraycopy(rawDataBlockArr[blockIndexForOffset].getData(), 0, bArr, min, this.blockSize);
                min += this.blockSize;
            } else {
                outputStream.write(bArr, 0, min);
                i2 -= min;
                i5 = 0;
                min = 0;
            }
        }
    }

    private int getBlockIndexForOffset(int i) {
        return i / this.blockSize;
    }

    private int getByteForOffset(int i) {
        int i2 = this.blockSize;
        int i3 = i / i2;
        return this.blocks[i3].getData()[i - (i2 * i3)] & 255;
    }

    public byte[] getRecordData(int i) {
        int uInt = ((int) getUInt(i + 4)) + 8;
        if (uInt < 0) {
            uInt = 0;
        }
        byte[] bArr = this.documentRawData;
        if (bArr == null || bArr.length < uInt) {
            this.documentRawData = new byte[Math.max(uInt, this.blockSize)];
        }
        int i2 = this.blockSize;
        int i3 = i / i2;
        int i4 = i + uInt;
        int i5 = i4 / i2;
        if (i5 > i3) {
            int i6 = i % i2;
            System.arraycopy(this.blocks[i3].getData(), i6, this.documentRawData, 0, this.blockSize - i6);
            int i7 = this.blockSize - i6;
            int i8 = i3 + 1;
            if (i8 < i5) {
                while (i8 < i5) {
                    System.arraycopy(this.blocks[i8].getData(), 0, this.documentRawData, i7, this.blockSize);
                    i7 += this.blockSize;
                    i8++;
                }
            }
            RawDataBlock[] rawDataBlockArr = this.blocks;
            if (i5 < rawDataBlockArr.length) {
                System.arraycopy(rawDataBlockArr[i5].getData(), 0, this.documentRawData, i7, i4 % this.blockSize);
            }
        } else {
            System.arraycopy(this.blocks[i3].getData(), i % i2, this.documentRawData, 0, uInt);
        }
        return this.documentRawData;
    }

    public void addChildProperty(Property property) {
        this.properties.put(property.getName(), property);
    }

    public Property getChlidProperty(String str) {
        return this.properties.get(str);
    }

    public void dispose() {
        this.documentRawData = null;
        this._name = null;
        this.blocks = null;
        Map<String, Property> map = this.properties;
        if (map != null) {
            for (String str : map.keySet()) {
                this.properties.get(str).dispose();
            }
            this.properties.clear();
            this.properties = null;
        }
    }
}
