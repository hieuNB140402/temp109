

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.util.Internal;

@ Internal
public final class UnhandledDataStructure
{
    byte[] _buf;

    public UnhandledDataStructure(byte[] buf, int offset, int length)
    {
        _buf = new byte[length];
        if (offset + length > buf.length)
        {
            throw new IndexOutOfBoundsException("buffer length is " + buf.length
                + "but code is trying to read " + length + " from offset " + offset);
        }
        System.arraycopy(buf, offset, _buf, 0, length);
    }

    byte[] getBuf()
    {
        return _buf;
    }
}
