package com.document.allreader.allofficefilereader.fc.p010fs.storage;

import com.document.allreader.allofficefilereader.fc.p010fs.filesystem.BlockSize;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/* renamed from: com.allreader.office.allofficefilereader.fc.fs.storage.BlockList */

public class BlockList {
    private BlockAllocationTableReader _bat;
    private RawDataBlock[] _blocks;

    public BlockList(InputStream inputStream, BlockSize blockSize) throws IOException {
        int read;
        ArrayList arrayList = new ArrayList();
        int bigBlockSize = blockSize.getBigBlockSize();
        do {
            byte[] bArr = new byte[bigBlockSize];
            read = inputStream.read(bArr);
            if (read <= 0) {
                break;
            }
            arrayList.add(new RawDataBlock(bArr));
        } while (read == bigBlockSize);
        this._blocks = (RawDataBlock[]) arrayList.toArray(new RawDataBlock[arrayList.size()]);
    }

    public BlockList(RawDataBlock[] rawDataBlockArr) {
        this._blocks = rawDataBlockArr;
    }

    public void zap(int i) {
        if (i >= 0) {
            RawDataBlock[] rawDataBlockArr = this._blocks;
            if (i < rawDataBlockArr.length) {
                rawDataBlockArr[i] = null;
            }
        }
    }

    protected RawDataBlock get(int i) {
        return this._blocks[i];
    }

    public RawDataBlock remove(int i) throws IOException {
        if (i >= 0) {
            RawDataBlock[] rawDataBlockArr = this._blocks;
            if (i < rawDataBlockArr.length) {
                RawDataBlock rawDataBlock = rawDataBlockArr[i];
                rawDataBlockArr[i] = null;
                return rawDataBlock;
            }
        }
        return null;
    }

    public RawDataBlock[] fetchBlocks(int i, int i2) throws IOException {
        BlockAllocationTableReader blockAllocationTableReader = this._bat;
        if (blockAllocationTableReader != null) {
            return blockAllocationTableReader.fetchBlocks(i, i2, this);
        }
        throw new IOException("Improperly initialized list: no block allocation table provided");
    }

    public void setBAT(BlockAllocationTableReader blockAllocationTableReader) throws IOException {
        this._bat = blockAllocationTableReader;
    }

    public int blockCount() {
        return this._blocks.length;
    }
}
