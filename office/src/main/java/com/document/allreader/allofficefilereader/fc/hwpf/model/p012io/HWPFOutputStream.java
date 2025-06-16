package com.document.allreader.allofficefilereader.fc.hwpf.model.p012io;

import com.document.allreader.allofficefilereader.fc.util.Internal;
import java.io.ByteArrayOutputStream;

@Internal


public final class HWPFOutputStream extends ByteArrayOutputStream {
    int _offset;

    public int getOffset() {
        return this._offset;
    }

    @Override
    public synchronized void reset() {
        super.reset();
        this._offset = 0;
    }

    @Override
    public synchronized void write(byte[] bArr, int i, int i2) {
        super.write(bArr, i, i2);
        this._offset += i2;
    }

    @Override
    public synchronized void write(int i) {
        super.write(i);
        this._offset++;
    }
}
