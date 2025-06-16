

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.hwpf.sprm.SectionSprmCompressor;
import com.document.allreader.allofficefilereader.fc.hwpf.sprm.SectionSprmUncompressor;
import com.document.allreader.allofficefilereader.fc.hwpf.sprm.SprmBuffer;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.SectionProperties;
import com.document.allreader.allofficefilereader.fc.util.Internal;


@Internal
public final class SEPX extends PropertyNode<SEPX>
{

    SectionProperties sectionProperties;

    SectionDescriptor _sed;

    public SEPX( SectionDescriptor sed, int start, int end, byte[] grpprl )
    {
        super( start, end, new SprmBuffer( grpprl, 0 ) );
        _sed = sed;
    }

    public byte[] getGrpprl()
    {
        if ( sectionProperties != null )
        {
            byte[] grpprl = SectionSprmCompressor
                    .compressSectionProperty( sectionProperties );
            _buf = new SprmBuffer( grpprl, 0 );
        }

        return ( (SprmBuffer) _buf ).toByteArray();
    }

    public SectionDescriptor getSectionDescriptor()
    {
        return _sed;
    }

    public SectionProperties getSectionProperties()
    {
        if ( sectionProperties == null )
        {
            sectionProperties = SectionSprmUncompressor.uncompressSEP(
                    ( (SprmBuffer) _buf ).toByteArray(), 0 );
        }
        return sectionProperties;
    }

    public boolean equals( Object o )
    {
        SEPX sepx = (SEPX) o;
        if ( super.equals( o ) )
        {
            return sepx._sed.equals( _sed );
        }
        return false;
    }

    public String toString()
    {
        return "SEPX from " + getStart() + " to " + getEnd();
    }
}
