

package com.document.allreader.allofficefilereader.fc.ddf;


public abstract class EscherProperty {
    private short  _id;

    public EscherProperty(short id) {
        _id   = id;
    }

    public EscherProperty(short propertyNumber, boolean isComplex, boolean isBlipId) {
        _id   = (short)(propertyNumber +
                (isComplex ? 0x8000 : 0x0) +
                (isBlipId ? 0x4000 : 0x0));
    }

    public short getId() {
        return _id;
    }

    public short getPropertyNumber() {
        return (short) (_id & (short) 0x3FFF);
    }

    public boolean isComplex() {
        return (_id & (short) 0x8000) != 0;
    }

    public boolean isBlipId() {
        return (_id & (short) 0x4000) != 0;
    }

    public String getName() {
        return EscherProperties.getPropertyName(getPropertyNumber());
    }


    public int getPropertySize() {
        return 6;
    }

    abstract public int serializeSimplePart( byte[] data, int pos );

    abstract public int serializeComplexPart( byte[] data, int pos );
}
