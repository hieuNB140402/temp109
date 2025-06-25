
package com.document.allreader.allofficefilereader.fc.ddf;

public class EscherOptRecord extends AbstractEscherOptRecord
{
    public static final short RECORD_ID = (short) 0xF00B;
    public static final String RECORD_DESCRIPTION = "msofbtOPT";


    public short getOptions()
    {
        setOptions( (short) ( ( properties.size() << 4 ) | 0x3 ) );
        return super.getOptions();
    }

    public String getRecordName()
    {
        return "Opt";
    }
    
    
    public void dispose()
    { 
    }
}
