package com.document.allreader.allofficefilereader.fc.p010fs.filesystem;

/* renamed from: com.allreader.office.allofficefilereader.fc.fs.filesystem.BlockSize */

public class BlockSize {
    private int bigBlockSize;
    private short headerValue;

    protected BlockSize(int i, short s) {
        this.bigBlockSize = i;
        this.headerValue = s;
    }

    public int getBigBlockSize() {
        return this.bigBlockSize;
    }

    public short getHeaderValue() {
        return this.headerValue;
    }

    public int getPropertiesPerBlock() {
        return this.bigBlockSize / 128;
    }

    public int getBATEntriesPerBlock() {
        return this.bigBlockSize / 4;
    }

    public int getXBATEntriesPerBlock() {
        return getBATEntriesPerBlock() - 1;
    }

    public int getNextXBATChainOffset() {
        return getXBATEntriesPerBlock() * 4;
    }
}
