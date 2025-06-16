

package com.document.allreader.allofficefilereader.fc.hwpf.sprm;

import com.document.allreader.allofficefilereader.fc.util.Internal;

@ Internal
public final class SprmIterator
{
    private byte[] _grpprl;
    int _offset;

    public SprmIterator(byte[] grpprl, int offset)
    {
        _grpprl = grpprl;
        _offset = offset;
    }

    public boolean hasNext()
    {

        return _offset < (_grpprl.length - 1);
    }

    public SprmOperation next()
    {
        SprmOperation op = new SprmOperation(_grpprl, _offset);
        _offset += op.size();
        return op;
    }

}
