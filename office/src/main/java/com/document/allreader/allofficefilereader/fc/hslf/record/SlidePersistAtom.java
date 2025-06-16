

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

public final class SlidePersistAtom extends RecordAtom
{
    private byte[] _header;
    private static long _type = 1011l;


    private int refID;
    private boolean hasShapesOtherThanPlaceholders;
    private int numPlaceholderTexts;
    private int slideIdentifier;
    private byte[] reservedFields;

    public int getRefID()
    {
        return refID;
    }

    public int getSlideIdentifier()
    {
        return slideIdentifier;
    }

    public int getNumPlaceholderTexts()
    {
        return numPlaceholderTexts;
    }

    public boolean getHasShapesOtherThanPlaceholders()
    {
        return hasShapesOtherThanPlaceholders;
    }


    public void setRefID(int id)
    {
        refID = id;
    }

    public void setSlideIdentifier(int id)
    {
        slideIdentifier = id;
    }


    public long getRecordType()
    {
        return _type;
    }


    public void writeOut(OutputStream out) throws IOException
    {
        out.write(_header);

        int flags = 0;
        if (hasShapesOtherThanPlaceholders)
        {
            flags = 4;
        }

        writeLittleEndian(refID, out);
        writeLittleEndian(flags, out);
        writeLittleEndian(numPlaceholderTexts, out);
        writeLittleEndian(slideIdentifier, out);
        out.write(reservedFields);
    }
    

    public void dispose()
    {
        _header = null;
        reservedFields = null;
    }
}
