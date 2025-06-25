

package com.document.allreader.allofficefilereader.fc.hwpf.model.io;

import java.io.ByteArrayOutputStream;

import com.document.allreader.allofficefilereader.fc.util.Internal;


@ Internal
public final class HWPFOutputStream extends ByteArrayOutputStream
{

    int _offset;

    public HWPFOutputStream()
    {
        super();
    }

    public int getOffset()
    {
        return _offset;
    }

    public synchronized void reset()
    {
        super.reset();
        _offset = 0;
    }

    public synchronized void write(byte[] buf, int off, int len)
    {
        super.write(buf, off, len);
        _offset += len;
    }

    public synchronized void write(int b)
    {
        super.write(b);
        _offset++;
    }
}
