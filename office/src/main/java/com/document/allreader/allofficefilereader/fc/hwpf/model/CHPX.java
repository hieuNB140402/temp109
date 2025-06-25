

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.hwpf.sprm.CharacterSprmUncompressor;
import com.document.allreader.allofficefilereader.fc.hwpf.sprm.SprmBuffer;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.CharacterProperties;
import com.document.allreader.allofficefilereader.fc.util.Internal;



@ Internal
@ SuppressWarnings("deprecation")
public final class CHPX extends BytePropertyNode<CHPX>
{

    @ Deprecated
    public CHPX(int fcStart, int fcEnd, CharIndexTranslator translator, byte[] grpprl)
    {
        super(fcStart, translator.lookIndexBackward(fcEnd), translator, new SprmBuffer(grpprl, 0));
    }

    @ Deprecated
    public CHPX(int fcStart, int fcEnd, CharIndexTranslator translator, SprmBuffer buf)
    {
        super(fcStart, translator.lookIndexBackward(fcEnd), translator, buf);
    }

    CHPX(int charStart, int charEnd, SprmBuffer buf)
    {
        super(charStart, charEnd, buf);
    }

    public byte[] getGrpprl()
    {
        return ((SprmBuffer)_buf).toByteArray();
    }

    public SprmBuffer getSprmBuf()
    {
        return (SprmBuffer)_buf;
    }

    public CharacterProperties getCharacterProperties(StyleSheet ss, short istd)
    {
        if (ss == null)
        {
            return new CharacterProperties();
        }

        CharacterProperties baseStyle = ss.getCharacterStyle(istd);
        if (baseStyle == null)
            baseStyle = new CharacterProperties();

        CharacterProperties props = CharacterSprmUncompressor.uncompressCHP(baseStyle, getGrpprl(),
            0);
        return props;
    }

    public String toString()
    {
        return "CHPX from " + getStart() + " to " + getEnd() + " (in bytes " + getStartBytes()
            + " to " + getEndBytes() + ")";
    }
}
