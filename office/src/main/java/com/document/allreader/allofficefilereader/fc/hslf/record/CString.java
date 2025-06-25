

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.fc.util.StringUtil;

public final class CString extends RecordAtom
{
    private byte[] _header;
    private static long _type = 4026l;

    private byte[] _text;

    public String getText()
    {
        return StringUtil.getFromUnicodeLE(_text);
    }

    public void setText(String text)
    {
        _text = new byte[text.length() * 2];
        StringUtil.putUnicodeLE(text, _text, 0);

        LittleEndian.putInt(_header, 4, _text.length);
    }

    public int getOptions()
    {
        return LittleEndian.getShort(_header);
    }

    public void setOptions(int count)
    {
        LittleEndian.putShort(_header, (short)count);
    }


    protected CString(byte[] source, int start, int len)
    {
        if (len < 8)
        {
            len = 8;
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        _text = new byte[len - 8];
        System.arraycopy(source, start + 8, _text, 0, len - 8);
    }


    public CString()
    {

        _header = new byte[]{0, 0, 0xBA - 256, 0x0f, 0, 0, 0, 0};

        _text = new byte[0];
    }

    public long getRecordType()
    {
        return _type;
    }

    public String toString()
    {
        return getText();
    }
    

    public void dispose()
    {
        _header = null;
        _text = null;
    }
}
