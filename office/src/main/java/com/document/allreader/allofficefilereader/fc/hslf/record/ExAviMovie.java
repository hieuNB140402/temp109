

package com.document.allreader.allofficefilereader.fc.hslf.record;

public final class ExAviMovie extends ExMCIMovie
{

    protected ExAviMovie(byte[] source, int start, int len)
    {
        super(source, start, len);
    }


    public ExAviMovie()
    {
        super();

    }


    public long getRecordType()
    {
        return RecordTypes.ExAviMovie.typeID;
    }
}
