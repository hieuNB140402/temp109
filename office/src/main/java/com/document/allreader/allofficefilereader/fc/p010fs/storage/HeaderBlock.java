package com.document.allreader.allofficefilereader.fc.p010fs.storage;

import com.document.allreader.allofficefilereader.fc.p010fs.filesystem.BlockSize;
import com.document.allreader.allofficefilereader.fc.p010fs.filesystem.CFBConstants;
import com.document.allreader.allofficefilereader.fc.util.HexDump;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.allreader.office.allofficefilereader.fc.fs.storage.HeaderBlock */

public final class HeaderBlock {
    public static final int _bat_array_offset = 76;
    public static final int _bat_count_offset = 44;
    public static final int _max_bats_in_header = 109;
    public static final int _property_start_offset = 48;
    public static final int _sbat_block_count_offset = 64;
    public static final int _sbat_start_offset = 60;
    public static final long _signature = -2226271756974174256L;
    public static final int _signature_offset = 0;
    public static final int _xbat_count_offset = 72;
    public static final int _xbat_start_offset = 68;
    private int _bat_count;
    private byte[] _data;
    private int _property_start;
    private int _sbat_count;
    private int _sbat_start;
    private int _xbat_count;
    private int _xbat_start;
    private BlockSize bigBlockSize;

    public HeaderBlock(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[512];
        this._data = bArr;
        inputStream.read(bArr);
        long j = LittleEndian.getLong(this._data, 0);
        if (j == -2226271756974174256L) {
            byte[] bArr2 = this._data;
            if (bArr2[30] == 12) {
                this.bigBlockSize = CFBConstants.LARGER_BIG_BLOCK_SIZE_DETAILS;
            } else if (bArr2[30] == 9) {
                this.bigBlockSize = CFBConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS;
            } else {
                throw new IOException("Unsupported blocksize  (2^" + ((int) this._data[30]) + "). Expected 2^9 or 2^12.");
            }
            this._bat_count = LittleEndian.getInt(this._data, 44);
            this._property_start = LittleEndian.getInt(this._data, 48);
            this._sbat_start = LittleEndian.getInt(this._data, 60);
            this._sbat_count = LittleEndian.getInt(this._data, 64);
            this._xbat_start = LittleEndian.getInt(this._data, 68);
            this._xbat_count = LittleEndian.getInt(this._data, 72);
            return;
        }
        throw new IOException("Invalid header signature; read " + longToHex(j) + ", expected " + longToHex(-2226271756974174256L));
    }

    private String longToHex(long j) {
        return new String(HexDump.longToHex(j));
    }

    public int getPropertyStart() {
        return this._property_start;
    }

    public void setPropertyStart(int i) {
        this._property_start = i;
    }

    public int getSBATStart() {
        return this._sbat_start;
    }

    public int getSBATCount() {
        return this._sbat_count;
    }

    public void setSBATStart(int i) {
        this._sbat_start = i;
    }

    public void setSBATBlockCount(int i) {
        this._sbat_count = i;
    }

    public int getBATCount() {
        return this._bat_count;
    }

    public void setBATCount(int i) {
        this._bat_count = i;
    }

    public int[] getBATArray() {
        int min = Math.min(this._bat_count, 109);
        int[] iArr = new int[min];
        int i = 76;
        for (int i2 = 0; i2 < min; i2++) {
            iArr[i2] = LittleEndian.getInt(this._data, i);
            i += 4;
        }
        return iArr;
    }

    public int getXBATCount() {
        return this._xbat_count;
    }

    public int getXBATIndex() {
        return this._xbat_start;
    }

    public BlockSize getBigBlockSize() {
        return this.bigBlockSize;
    }

    public void dispose() {
        this._data = null;
        this.bigBlockSize = null;
    }
}
