

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.AlignmentTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.CharFlagsTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.ParagraphFlagsTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextPropCollection;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

public final class TxMasterStyleAtom extends RecordAtom
{


    private static final int MAX_INDENT = 5;

    protected TxMasterStyleAtom(byte[] source, int start, int len)
    {
        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        _data = new byte[len - 8];
        System.arraycopy(source, start + 8, _data, 0, _data.length);

        try
        {
            init();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public long getRecordType()
    {
        return _type;
    }


    public void writeOut(OutputStream out) throws IOException
    {
        out.write(_header);


        out.write(_data);

    }


    public TextPropCollection[] getCharacterStyles()
    {
        return chstyles;
    }


    public TextPropCollection[] getParagraphStyles()
    {
        return prstyles;
    }


    public int getTextType()
    {

        return LittleEndian.getShort(_header, 0) >> 4;
    }


    protected void init()
    {

        int type = getTextType();

        int mask;
        int pos = 0;


        short levels = LittleEndian.getShort(_data, 0);
        pos += LittleEndian.SHORT_SIZE;

        prstyles = new TextPropCollection[levels];
        chstyles = new TextPropCollection[levels];

        for (short j = 0; j < levels; j++)
        {

            if (type >= TextHeaderAtom.CENTRE_BODY_TYPE)
            {
                short val = LittleEndian.getShort(_data, pos);
                pos += LittleEndian.SHORT_SIZE;
            }

            mask = LittleEndian.getInt(_data, pos);
            pos += LittleEndian.INT_SIZE;
            TextPropCollection prprops = new TextPropCollection(0);
            pos += prprops.buildTextPropList(mask, getParagraphProps(type, j), _data, pos);
            prstyles[j] = prprops;

            mask = LittleEndian.getInt(_data, pos);
            pos += LittleEndian.INT_SIZE;
            TextPropCollection chprops = new TextPropCollection(0);
            pos += chprops.buildTextPropList(mask, getCharacterProps(type, j), _data, pos);
            chstyles[j] = chprops;
        }

    }


    protected TextProp[] getParagraphProps(int type, int level)
    {
        if (level != 0 || type >= MAX_INDENT)
        {
            return new TextProp[]
                {
                    new TextProp(0, 0x1, "hasBullet"), new TextProp(0, 0x2, "hasBulletFont"),
                    new TextProp(0, 0x4, "hasBulletColor"), new TextProp(0, 0x8, "hasBulletSize"),
                    new ParagraphFlagsTextProp(), new TextProp(2, 0x80, "bullet.char"),
                    new TextProp(2, 0x10, "bullet.font"), new TextProp(2, 0x40, "bullet.size"),
                    new TextProp(4, 0x20, "bullet.color"), new AlignmentTextProp(),
                    new TextProp(2, 0x1000, "linespacing"), new TextProp(2, 0x2000, "spacebefore"),
                    new TextProp(2, 0x100, "text.offset"), new TextProp(2, 0x400, "bullet.offset"),
                    new TextProp(2, 0x4000, "spaceafter"), new TextProp(2, 0x8000, "defaultTabSize"),
                    new TextProp(2, 0x100000, "tabStops"), new TextProp(2, 0x10000, "fontAlign"),
                    new TextProp(2, 0xE0000, "wrapFlags"), new TextProp(2, 0x200000, "textDirection"),
                    new TextProp(2, 0x1000000, "buletScheme"), new TextProp(2, 0x2000000, "bulletHasScheme")
                };
            
        }
        return new TextProp[]
            {
                new ParagraphFlagsTextProp(), new TextProp(2, 0x80, "bullet.char"),
                new TextProp(2, 0x10, "bullet.font"), new TextProp(2, 0x40, "bullet.size"),
                new TextProp(4, 0x20, "bullet.color"), new TextProp(2, 0xD00, "alignment"),
                new TextProp(2, 0x1000, "linespacing"), new TextProp(2, 0x2000, "spacebefore"),
                new TextProp(2, 0x4000, "spaceafter"), new TextProp(2, 0x8000, "text.offset"),
                new TextProp(2, 0x10000, "bullet.offset"), new TextProp(2, 0x20000, "defaulttab"),
                new TextProp(2, 0x40000, "tabStops"), new TextProp(2, 0x80000, "fontAlign"), 
                new TextProp(2, 0x100000, "para_unknown_1"),new TextProp(2, 0x200000, "para_unknown_2"),
            };
    }


    protected TextProp[] getCharacterProps(int type, int level)
    {
        if (level != 0 || type >= MAX_INDENT)
        {
            return StyleTextPropAtom.characterTextPropTypes;
        }
        return new TextProp[]
            {
                new CharFlagsTextProp(), new TextProp(2, 0x10000, "font.index"),
                new TextProp(2, 0x20000, "char_unknown_1"), new TextProp(4, 0x40000, "char_unknown_2"),
                new TextProp(2, 0x80000, "font.size"), new TextProp(2, 0x100000, "char_unknown_3"),
                new TextProp(4, 0x200000, "font.color"), new TextProp(2, 0x800000, "char_unknown_4")
            };
    }
    

    
    public void dispose()
    {
        _header = null;
        _data = null;
        if (prstyles != null)
        {
            for(TextPropCollection ptc : prstyles)
            {
                ptc.dispose();
            }
            prstyles = null;
        }
        if (chstyles != null)
        {
            for(TextPropCollection ptc : chstyles)
            {
                ptc.dispose();
            }
            chstyles = null;
        }
    }
    
    private byte[] _header;
    private static long _type = 4003;
    private byte[] _data;

    private TextPropCollection[] prstyles;
    private TextPropCollection[] chstyles;
}
