

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.AlignmentTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.CharFlagsTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.ParagraphFlagsTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextPropCollection;
import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class StyleTextPropAtom extends RecordAtom
{
    private byte[] _header;
    private static long _type = 4001l;
    private byte[] reserved;

    private byte[] rawContents;


    private boolean initialised = false;


    private LinkedList<TextPropCollection> paragraphStyles;

    public LinkedList<TextPropCollection> getParagraphStyles()
    {
        return paragraphStyles;
    }


    public void setParagraphStyles(LinkedList<TextPropCollection> ps)
    {
        paragraphStyles = ps;
    }


    private LinkedList<TextPropCollection> charStyles;

    public LinkedList<TextPropCollection> getCharacterStyles()
    {
        return charStyles;
    }


    public void setCharacterStyles(LinkedList<TextPropCollection> cs)
    {
        charStyles = cs;
    }

    public int getParagraphTextLengthCovered()
    {
        return getCharactersCovered(paragraphStyles);
    }


    public int getCharacterTextLengthCovered()
    {
        return getCharactersCovered(charStyles);
    }

    private int getCharactersCovered(LinkedList<TextPropCollection> styles)
    {
        int length = 0;
        for (TextPropCollection tpc : styles)
        {
            length += tpc.getCharactersCovered();
        }
        return length;
    }

    public static TextProp[] paragraphTextPropTypes = new TextProp[]
        {
            new TextProp(0, 0x1, "hasBullet"), new TextProp(0, 0x2, "hasBulletFont"),
            new TextProp(0, 0x4, "hasBulletColor"), new TextProp(0, 0x8, "hasBulletSize"),
            new ParagraphFlagsTextProp(), new TextProp(2, 0x80, "bullet.char"),
            new TextProp(2, 0x10, "bullet.font"), new TextProp(2, 0x40, "bullet.size"),
            new TextProp(4, 0x20, "bullet.color"), new AlignmentTextProp(),
            new TextProp(2, 0x100, "text.offset"), new TextProp(2, 0x400, "bullet.offset"),
            new TextProp(2, 0x1000, "linespacing"), new TextProp(2, 0x2000, "spacebefore"),
            new TextProp(2, 0x4000, "spaceafter"), new TextProp(2, 0x8000, "defaultTabSize"),
            new TextProp(2, 0x100000, "tabStops"), new TextProp(2, 0x10000, "fontAlign"),
            new TextProp(2, 0xE0000, "wrapFlags"), new TextProp(2, 0x200000, "textDirection"),
            new TextProp(2, 0x1000000, "buletScheme"), new TextProp(2, 0x2000000, "bulletHasScheme")
        };
   public static TextProp[] characterTextPropTypes = new TextProp[]
        {
            new TextProp(0, 0x1, "bold"), new TextProp(0, 0x2, "italic"), 
            new TextProp(0, 0x4, "underline"), new TextProp(0, 0x8, "unused1"), 
            new TextProp(0, 0x10, "shadow"), new TextProp(0, 0x20, "fehint"), 
            new TextProp(0, 0x40, "unused2"), new TextProp(0, 0x80, "kumi"), 
            new TextProp(0, 0x100, "unused3"), new TextProp(0, 0x200, "emboss"), 
            new TextProp(0, 0x400, "nibble1"), new TextProp(0, 0x800, "nibble2"), 
            new TextProp(0, 0x1000, "nibble3"), new TextProp(0, 0x2000, "nibble4"), 
            new TextProp(0, 0x4000, "unused4"), new TextProp(0, 0x8000, "unused5"), 
            new CharFlagsTextProp(), new TextProp(2, 0x10000, "font.index"), 
            new TextProp(0, 0x100000, "pp10ext"), new TextProp(2, 0x200000, "asian.font.index"),
            new TextProp(2, 0x400000, "ansi.font.index"), new TextProp(2, 0x800000, "symbol.font.index"), 
            new TextProp(2, 0x20000, "font.size"), new TextProp(4, 0x40000, "font.color"), 
            new TextProp(2, 0x80000, "superscript"),
    };



    public StyleTextPropAtom(int parentTextSize)
    {
        _header = new byte[8];
        rawContents = new byte[0];
        reserved = new byte[0];

        LittleEndian.putInt(_header, 2, (short)_type);

        LittleEndian.putInt(_header, 4, 10);

        paragraphStyles = new LinkedList<TextPropCollection>();
        charStyles = new LinkedList<TextPropCollection>();

        TextPropCollection defaultParagraphTextProps = new TextPropCollection(parentTextSize,
            (short)0);
        paragraphStyles.add(defaultParagraphTextProps);

        TextPropCollection defaultCharacterTextProps = new TextPropCollection(parentTextSize);
        charStyles.add(defaultCharacterTextProps);


        initialised = true;
    }

    public long getRecordType()
    {
        return _type;
    }


    public void writeOut(OutputStream out) throws IOException
    {

        updateRawContents();


        int newSize = rawContents.length + reserved.length;
        LittleEndian.putInt(_header, 4, newSize);

        out.write(_header);

        out.write(rawContents);

        out.write(reserved);
    }

    public void setParentTextSize(int size)
    {
        int pos = 0;
        int textHandled = 0;
        int para = 0;
        int autoNumber = 0;
        paraAutoNumberIndexs.clear();

        int prsize = size;
        while (pos < rawContents.length && textHandled < prsize)
        {
            int textLen = LittleEndian.getInt(rawContents, pos);
            textHandled += textLen;
            pos += 4;

            short indent = LittleEndian.getShort(rawContents, pos);
            pos += 2;

            int paraFlags = LittleEndian.getInt(rawContents, pos);
            pos += 4;

            TextPropCollection thisCollection = new TextPropCollection(textLen, indent);
            int plSize = thisCollection.buildTextPropList(paraFlags, paragraphTextPropTypes,
                rawContents, pos);
            pos += plSize;


            paragraphStyles.add(thisCollection);


            if (pos < rawContents.length && textHandled == size)
            {
                prsize++;
            }


            if (para > 0)
            {
                int para_flag = 0;
                TextProp temp = thisCollection.findByName("paragraph_flags");
                if (temp != null)
                {
                    para_flag = temp.getValue();
                }
                if (para_flag != 1)
                {
                    int bulletChar = 0;
                    temp = thisCollection.findByName("bullet.char");
                    if (temp != null)
                    {
                        bulletChar = temp.getValue();
                    }
                    if (para_flag != 2)
                    {
                        if (bulletChar == 8226 || bulletChar == 8211)
                        {
                            autoNumber++;
                        }
                        else
                        {
                            TextPropCollection collection= paragraphStyles.get(para - 1);
                            if (collection != null)
                            {
                                temp = collection.findByName("bullet.char");
                                if (temp != null)
                                {
                                    bulletChar = temp.getValue();
                                }
                            }
                            if (bulletChar == 8226 || bulletChar == 8211)
                            {
                                autoNumber++;
                            }
                        }
                    }
                }
                else
                {
                    autoNumber++;
                }
            }
            paraAutoNumberIndexs.put(para, autoNumber);
            para++;
        }


        textHandled = 0;
        int chsize = size;
        while (pos < rawContents.length && textHandled < chsize)
        {

            int textLen = LittleEndian.getInt(rawContents, pos);
            textHandled += textLen;
            pos += 4;


            short no_val = -1;


            int charFlags = LittleEndian.getInt(rawContents, pos);
            pos += 4;


            TextPropCollection thisCollection = new TextPropCollection(textLen, no_val);
            int chSize = thisCollection.buildTextPropList(charFlags, characterTextPropTypes,
                rawContents, pos);
            pos += chSize;

            charStyles.add(thisCollection);

            if (pos < rawContents.length && textHandled == size)
            {
                chsize++;
            }
        }

        if (pos < rawContents.length)
        {
            reserved = new byte[rawContents.length - pos];
            System.arraycopy(rawContents, pos, reserved, 0, reserved.length);
        }

        initialised = true;
    }


    private void updateRawContents() throws IOException
    {
        if (!initialised)
        {

            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (int i = 0; i < paragraphStyles.size(); i++)
        {
            TextPropCollection tpc = paragraphStyles.get(i);

        }

        for (int i = 0; i < charStyles.size(); i++)
        {
            TextPropCollection tpc = charStyles.get(i);

        }

        rawContents = baos.toByteArray();
    }

    public void setRawContents(byte[] bytes)
    {
        rawContents = bytes;
        reserved = new byte[0];
        initialised = false;
    }


    public TextPropCollection addParagraphTextPropCollection(int charactersCovered)
    {
        TextPropCollection tpc = new TextPropCollection(charactersCovered, (short)0);
        paragraphStyles.add(tpc);
        return tpc;
    }


    public TextPropCollection addCharacterTextPropCollection(int charactersCovered)
    {
        TextPropCollection tpc = new TextPropCollection(charactersCovered);
        charStyles.add(tpc);
        return tpc;
    }



    public String toString()
    {
        StringBuffer out = new StringBuffer();

        out.append("StyleTextPropAtom:\n");
        if (!initialised)
        {
            out.append("Uninitialised, dumping Raw Style Data\n");
        }
        else
        {

            out.append("Paragraph properties\n");

            for (TextPropCollection pr : getParagraphStyles())
            {
                out.append("  chars covered: " + pr.getCharactersCovered());
                out.append("  special mask flags: 0x" + HexDump.toHex(pr.getSpecialMask()) + "\n");
                for (TextProp p : pr.getTextPropList())
                {
                    out.append("    " + p.getName() + " = " + p.getValue());
                    out.append(" (0x" + HexDump.toHex(p.getValue()) + ")\n");
                }

                out.append("  para bytes that would be written: \n");

                try
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    //pr.writeOut(baos);
                    byte[] b = baos.toByteArray();
                    out.append(HexDump.dump(b, 0, 0));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            out.append("Character properties\n");
            for (TextPropCollection pr : getCharacterStyles())
            {
                out.append("  chars covered: " + pr.getCharactersCovered());
                out.append("  special mask flags: 0x" + HexDump.toHex(pr.getSpecialMask()) + "\n");
                for (TextProp p : pr.getTextPropList())
                {
                    out.append("    " + p.getName() + " = " + p.getValue());
                    out.append(" (0x" + HexDump.toHex(p.getValue()) + ")\n");
                }

                out.append("  char bytes that would be written: \n");

                try
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    //pr.writeOut(baos);
                    byte[] b = baos.toByteArray();
                    out.append(HexDump.dump(b, 0, 0));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        out.append("  original byte stream \n");
        out.append(HexDump.dump(rawContents, 0, 0));

        return out.toString();
    }
    

    public int getAutoNumberIndex(int charcterIndex)
    {
        int paraIndex = 0;
        if (paragraphStyles != null)
        {
            int start = 0;
            for (int i = 0; i < paragraphStyles.size(); i++)
            {
                int end = start + paragraphStyles.get(i).getCharactersCovered() - 1;
                if (charcterIndex >= start  && charcterIndex <= end)
                {
                    paraIndex = i;
                    break;
                }
                start = end + 1;
            }
        }
        if (paraIndex >= 0 && paraIndex < paraAutoNumberIndexs.size())
        {
            Integer index = paraAutoNumberIndexs.get(paraIndex);
            if (index != null)
            {
                return index;
            }
        }
        return -1;
    }
    

    public void dispose()
    {
        _header = null;
        reserved = null;
        rawContents = null;
        if (paraAutoNumberIndexs != null)
        {
            paraAutoNumberIndexs.clear();
            paraAutoNumberIndexs = null;
        }
    }
    

    private Map<Integer, Integer> paraAutoNumberIndexs = new HashMap<Integer, Integer>();;
}
