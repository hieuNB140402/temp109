

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.util.Arrays;

import com.document.allreader.allofficefilereader.fc.util.Internal;


@ Internal
public final class UPX
{
    private byte[] _upx;

    public UPX(byte[] upx)
    {
        _upx = upx;
    }

    public byte[] getUPX()
    {
        return _upx;
    }

    public int size()
    {
        return _upx.length;
    }

    public boolean equals(Object o)
    {
        UPX upx = (UPX)o;
        return Arrays.equals(_upx, upx._upx);
    }
}
