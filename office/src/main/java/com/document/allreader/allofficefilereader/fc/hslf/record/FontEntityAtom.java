

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.UnsupportedEncodingException;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class FontEntityAtom extends RecordAtom
{

    private byte[] _header;


    private byte[] _recdata;



    public FontEntityAtom()
    {
        _recdata = new byte[68];

        _header = new byte[8];
        LittleEndian.putShort(_header, 2, (short)getRecordType());
        LittleEndian.putInt(_header, 4, _recdata.length);
    }

    public long getRecordType()
    {
        return RecordTypes.FontEntityAtom.typeID;
    }

    public String getFontName()
    {
        String name = null;
        try
        {
            int i = 0;
            while (i < 64)
            {

                if (_recdata[i] == 0 && _recdata[i + 1] == 0)
                {
                    name = new String(_recdata, 0, i, "UTF-16LE");
                    break;
                }
                i += 2;
            }
        }
        catch(UnsupportedEncodingException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
        return name;
    }


    public void setFontName(String name)
    {

        if (!name.endsWith("\000"))
        {
            name = name + "\000";
        }


        if (name.length() > 32)
        {
            throw new RuntimeException(
                "The length of the font name, including null termination, must not exceed 32 characters");
        }


        try
        {
            byte[] bytes = name.getBytes("UTF-16LE");
            System.arraycopy(bytes, 0, _recdata, 0, bytes.length);
        }
        catch(UnsupportedEncodingException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void setFontIndex(int idx)
    {
        LittleEndian.putShort(_header, 0, (short)idx);
    }

    public int getFontIndex()
    {
        return LittleEndian.getShort(_header, 0) >> 4;
    }

    public void setCharSet(int charset)
    {
        _recdata[64] = (byte)charset;
    }

    public int getCharSet()
    {
        return _recdata[64];
    }

    public void setFontFlags(int flags)
    {
        _recdata[65] = (byte)flags;
    }

    public int getFontFlags()
    {
        return _recdata[65];
    }

    public void setFontType(int type)
    {
        _recdata[66] = (byte)type;
    }

    public int getFontType()
    {
        return _recdata[66];
    }


    public void setPitchAndFamily(int val)
    {
        _recdata[67] = (byte)val;
    }


    public int getPitchAndFamily()
    {
        return _recdata[67];
    }
    
    
    
    public void dispose()
    {
        _header = null;
        _recdata = null;
    }
}
