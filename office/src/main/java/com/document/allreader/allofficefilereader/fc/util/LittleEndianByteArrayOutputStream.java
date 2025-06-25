

package com.document.allreader.allofficefilereader.fc.util;

/**
 * Adapts a plain byte array to {@link LittleEndianOutput} 
 * 
 * 
 * @author Josh Micich
 */
public final class LittleEndianByteArrayOutputStream implements LittleEndianOutput,
    DelayableLittleEndianOutput
{
    private final byte[] _buf;
    private final int _endIndex;
    private int _writeIndex;

    public LittleEndianByteArrayOutputStream(byte[] buf, int startOffset, int maxWriteLen)
    {
        if (startOffset < 0 || startOffset > buf.length)
        {
            throw new IllegalArgumentException("Specified startOffset (" + startOffset
                + ") is out of allowable range (0.." + buf.length + ")");
        }
        _buf = buf;
        _writeIndex = startOffset;
        _endIndex = startOffset + maxWriteLen;
        if (_endIndex < startOffset || _endIndex > buf.length)
        {
            throw new IllegalArgumentException("calculated end index (" + _endIndex
                + ") is out of allowable range (" + _writeIndex + ".." + buf.length + ")");
        }
    }

    public LittleEndianByteArrayOutputStream(byte[] buf, int startOffset)
    {
        this(buf, startOffset, buf.length - startOffset);
    }

    private void checkPosition(int i)
    {
        if (i > _endIndex - _writeIndex)
        {
            throw new RuntimeException("Buffer overrun");
        }
    }

    public void writeByte(int v)
    {
        checkPosition(1);
        _buf[_writeIndex++] = (byte)v;
    }

    public void writeDouble(double v)
    {
        writeLong(Double.doubleToLongBits(v));
    }

    public void writeInt(int v)
    {
        checkPosition(4);
        int i = _writeIndex;
        _buf[i++] = (byte)((v >>> 0) & 0xFF);
        _buf[i++] = (byte)((v >>> 8) & 0xFF);
        _buf[i++] = (byte)((v >>> 16) & 0xFF);
        _buf[i++] = (byte)((v >>> 24) & 0xFF);
        _writeIndex = i;
    }

    public void writeLong(long v)
    {
        writeInt((int)(v >> 0));
        writeInt((int)(v >> 32));
    }

    public void writeShort(int v)
    {
        checkPosition(2);
        int i = _writeIndex;
        _buf[i++] = (byte)((v >>> 0) & 0xFF);
        _buf[i++] = (byte)((v >>> 8) & 0xFF);
        _writeIndex = i;
    }

    public void write(byte[] b)
    {
        int len = b.length;
        checkPosition(len);
        System.arraycopy(b, 0, _buf, _writeIndex, len);
        _writeIndex += len;
    }

    public void write(byte[] b, int offset, int len)
    {
        checkPosition(len);
        System.arraycopy(b, offset, _buf, _writeIndex, len);
        _writeIndex += len;
    }

    public int getWriteIndex()
    {
        return _writeIndex;
    }

    public LittleEndianOutput createDelayedOutput(int size)
    {
        checkPosition(size);
        LittleEndianOutput result = new LittleEndianByteArrayOutputStream(_buf, _writeIndex, size);
        _writeIndex += size;
        return result;
    }
}
