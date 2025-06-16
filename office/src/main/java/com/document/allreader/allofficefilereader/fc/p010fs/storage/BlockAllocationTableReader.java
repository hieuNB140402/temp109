package com.document.allreader.allofficefilereader.fc.p010fs.storage;

import com.document.allreader.allofficefilereader.fc.p010fs.filesystem.BlockSize;
import java.io.IOException;
import java.util.ArrayList;

/* renamed from: com.allreader.office.allofficefilereader.fc.fs.storage.BlockAllocationTableReader */

public final class BlockAllocationTableReader {
    private final IntList _entries = new IntList();
    private BlockSize bigBlockSize;

    public BlockAllocationTableReader(BlockSize blockSize, int i, int[] iArr, int i2, int i3, BlockList blockList) throws IOException {
        this.bigBlockSize = blockSize;
        int min = Math.min(i, iArr.length);
        RawDataBlock[] rawDataBlockArr = new RawDataBlock[i];
        int i4 = 0;
        while (i4 < min) {
            int i5 = iArr[i4];
            if (i5 <= blockList.blockCount()) {
                rawDataBlockArr[i4] = blockList.remove(i5);
                i4++;
            } else {
                throw new IOException("Your file contains " + blockList.blockCount() + " sectors, but the initial DIFAT array at index " + i4 + " referenced block # " + i5 + ". This isn't allowed and  your file is corrupt");
            }
        }
        if (i4 < i) {
            if (i3 >= 0) {
                int xBATEntriesPerBlock = blockSize.getXBATEntriesPerBlock();
                int nextXBATChainOffset = blockSize.getNextXBATChainOffset();
                for (int i6 = 0; i6 < i2; i6++) {
                    int min2 = Math.min(i - i4, xBATEntriesPerBlock);
                    byte[] data = blockList.remove(i3).getData();
                    int i7 = 0;
                    int i8 = 0;
                    while (i7 < min2) {
                        rawDataBlockArr[i4] = blockList.remove(LittleEndian.getInt(data, i8));
                        i8 += 4;
                        i7++;
                        i4++;
                    }
                    i3 = LittleEndian.getInt(data, nextXBATChainOffset);
                    if (i3 == -2) {
                        break;
                    }
                }
            } else {
                throw new IOException("BAT count exceeds limit, yet XBAT index indicates no valid entries");
            }
        }
        if (i4 == i) {
            setEntries(rawDataBlockArr, blockList);
            return;
        }
        throw new IOException("Could not find all blocks");
    }

    public BlockAllocationTableReader(BlockSize blockSize, RawDataBlock[] rawDataBlockArr, BlockList blockList) throws IOException {
        this.bigBlockSize = blockSize;
        setEntries(rawDataBlockArr, blockList);
    }

    public RawDataBlock[] fetchBlocks(int i, int i2, BlockList blockList) throws IOException {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        while (i != -2) {
            try {
                arrayList.add(blockList.remove(i));
                i = this._entries.get(i);
                z = false;
            } catch (IOException e) {
                if (i != i2 && (i != 0 || !z)) {
                    throw e;
                }
                i = -2;
            }
        }
        return (RawDataBlock[]) arrayList.toArray(new RawDataBlock[arrayList.size()]);
    }

    private void setEntries(RawDataBlock[] rawDataBlockArr, BlockList blockList) throws IOException {
        int bATEntriesPerBlock = this.bigBlockSize.getBATEntriesPerBlock();
        for (int i = 0; i < rawDataBlockArr.length; i++) {
            byte[] data = rawDataBlockArr[i].getData();
            int i2 = 0;
            for (int i3 = 0; i3 < bATEntriesPerBlock; i3++) {
                int i4 = LittleEndian.getInt(data, i2);
                if (i4 == -1) {
                    blockList.zap(this._entries.size());
                }
                this._entries.add(i4);
                i2 += 4;
            }
            rawDataBlockArr[i] = null;
        }
        blockList.setBAT(this);
    }
}
