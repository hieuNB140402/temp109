

package com.document.allreader.allofficefilereader.fc.hslf.record;


import com.document.allreader.allofficefilereader.fc.util.StringUtil;


public final class DocumentEncryptionAtom extends RecordAtom
{
    private byte[] _header;
    private static long _type = 12052l;

    private byte[] data;
    private String encryptionProviderName;


    protected DocumentEncryptionAtom(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        data = new byte[len - 8];
        System.arraycopy(source, start + 8, data, 0, len - 8);

        int endPos = -1;
        int pos = start + 8 + 44;
        while (pos < (start + len) && endPos < 0)
        {
            if (source[pos] == 0 && source[pos + 1] == 0)
            {
                // Hit the end
                endPos = pos;
            }
            pos += 2;
        }
        pos = start + 8 + 44;
        int stringLen = (endPos - pos) / 2;
        encryptionProviderName = StringUtil.getFromUnicodeLE(source, pos, stringLen);
    }

    public int getKeyLength()
    {
        return data[28];
    }

    public String getEncryptionProviderName()
    {
        return encryptionProviderName;
    }

    public long getRecordType()
    {
        return _type;
    }

    
    public void dispose()
    {
        _header = null;
        data = null;
        encryptionProviderName = null;
    }

    
}
