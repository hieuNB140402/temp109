

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.ArrayList;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.util.POILogger;




public final class FontCollection extends RecordContainer
{
    protected FontCollection(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        _children = Record.findChildRecords(source, start + 8, len - 8);

        fonts = new ArrayList<String>();
        for (int i = 0; i < _children.length; i++)
        {
            if (_children[i] instanceof FontEntityAtom)
            {
                FontEntityAtom atom = (FontEntityAtom)_children[i];
                fonts.add(atom.getFontName());
            }
            else
            {
                logger.log(POILogger.WARN,
                    "Warning: FontCollection child wasn't a FontEntityAtom, was " + _children[i]);
            }
        }
    }


    public long getRecordType()
    {
        return RecordTypes.FontCollection.typeID;
    }


    public int addFont(String name)
    {
        int idx = getFontIndex(name);
        if (idx != -1)
            return idx;

        return addFont(name, 0, 0, 4, 34);
    }

    public int addFont(String name, int charset, int flags, int type, int pitch)
    {
        FontEntityAtom fnt = new FontEntityAtom();
        fnt.setFontIndex(fonts.size() << 4);
        fnt.setFontName(name);
        fnt.setCharSet(charset);
        fnt.setFontFlags(flags);
        fnt.setFontType(type);
        fnt.setPitchAndFamily(pitch);
        fonts.add(name);


        appendChildRecord(fnt);

        return fonts.size() - 1;
    }


    public int getFontIndex(String name)
    {
        for (int i = 0; i < fonts.size(); i++)
        {
            if (fonts.get(i).equals(name))
            {
                //if the font is already present return its index
                return i;
            }
        }
        return -1;
    }

    public int getNumberOfFonts()
    {
        return fonts.size();
    }


    public String getFontWithId(int id)
    {
        if (id >= fonts.size())
        {

            return null;
        }
        return fonts.get(id);
    }
    

    public void dispose()
    {
        super.dispose();
        _header = null;
        if (fonts != null)
        {
            fonts.clear();
            fonts = null;
        }
    }
    
    private List<String> fonts;
    private byte[] _header;
}
