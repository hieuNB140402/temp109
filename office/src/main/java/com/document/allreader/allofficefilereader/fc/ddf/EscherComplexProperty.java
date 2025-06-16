

package com.document.allreader.allofficefilereader.fc.ddf;

import java.util.Arrays;

import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public class EscherComplexProperty extends EscherProperty {

    protected byte[] _complexData;


    public EscherComplexProperty(short id, byte[] complexData) {
        super(id);
        _complexData = complexData;
    }


    public EscherComplexProperty(short propertyNumber, boolean isBlipId, byte[] complexData) {
        super(propertyNumber, true, isBlipId);
        _complexData = complexData;
    }


    public int serializeSimplePart(byte[] data, int pos) {
        LittleEndian.putShort(data, pos, getId());
        LittleEndian.putInt(data, pos + 2, _complexData.length);
        return 6;
    }


    public int serializeComplexPart(byte[] data, int pos) {
        System.arraycopy(_complexData, 0, data, pos, _complexData.length);
        return _complexData.length;
    }

    public byte[] getComplexData() {
        return _complexData;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EscherComplexProperty)) {
            return false;
        }

        EscherComplexProperty escherComplexProperty = (EscherComplexProperty) o;

        if ( !Arrays.equals( _complexData, escherComplexProperty._complexData ) ) return false;

        return true;
    }


    public int getPropertySize() {
        return 6 + _complexData.length;
    }

    public int hashCode() {
        return getId() * 11;
    }


    public String toString() {
        String dataStr = HexDump.toHex( _complexData, 32);

        return "propNum: " + getPropertyNumber()
                + ", propName: " + EscherProperties.getPropertyName( getPropertyNumber() )
                + ", complex: " + isComplex()
                + ", blipId: " + isBlipId()
                + ", data: " + System.getProperty("line.separator") + dataStr;
    }

}
