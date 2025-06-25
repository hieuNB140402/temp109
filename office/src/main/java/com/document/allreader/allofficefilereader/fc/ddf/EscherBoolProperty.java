


package com.document.allreader.allofficefilereader.fc.ddf;

public class EscherBoolProperty
        extends EscherSimpleProperty
{
    public EscherBoolProperty( short propertyNumber, int value )
    {
        super(propertyNumber, value);
    }


    public boolean isTrue()
    {
        return propertyValue != 0;
    }


    public boolean isFalse()
    {
        return propertyValue == 0;
    }


}
