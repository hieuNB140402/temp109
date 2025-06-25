

package com.document.allreader.allofficefilereader.fc.ddf;

import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public final class EscherArrayProperty extends EscherComplexProperty {

    private static final int FIXED_SIZE = 3 * 2;

    private boolean sizeIncludesHeaderSize = true;


    private boolean emptyComplexPart = false;

    public EscherArrayProperty(short id, byte[] complexData) {
        super(id, checkComplexData(complexData));
        emptyComplexPart = complexData.length == 0;
    }

    public EscherArrayProperty(short propertyNumber, boolean isBlipId, byte[] complexData) {
        super(propertyNumber, isBlipId, checkComplexData(complexData));
    }

    private static byte[] checkComplexData(byte[] complexData) {
        if (complexData == null || complexData.length == 0) {
            return new byte[6];
        }

        return complexData;
    }

    public int getNumberOfElementsInArray() {
        _complexData = checkComplexData(_complexData);
        return LittleEndian.getUShort(_complexData, 0);
    }

    public void setNumberOfElementsInArray(int numberOfElements) {
        int expectedArraySize = numberOfElements * getActualSizeOfElements(getSizeOfElements()) + FIXED_SIZE;
        if (expectedArraySize != _complexData.length) {
            byte[] newArray = new byte[expectedArraySize];
            System.arraycopy(_complexData, 0, newArray, 0, _complexData.length);
            _complexData = newArray;
        }
        LittleEndian.putShort(_complexData, 0, (short) numberOfElements);
    }

    public int getNumberOfElementsInMemory() {
        _complexData = checkComplexData(_complexData);
        return LittleEndian.getUShort(_complexData, 2);
    }

    public void setNumberOfElementsInMemory(int numberOfElements) {
        int expectedArraySize = numberOfElements * getActualSizeOfElements(getSizeOfElements()) + FIXED_SIZE;
        if (expectedArraySize != _complexData.length) {
            byte[] newArray = new byte[expectedArraySize];
            System.arraycopy(_complexData, 0, newArray, 0, expectedArraySize);
            _complexData = newArray;
        }
        LittleEndian.putShort(_complexData, 2, (short) numberOfElements);
    }

    public short getSizeOfElements() {
        _complexData = checkComplexData(_complexData);
        return LittleEndian.getShort( _complexData, 4 );
    }

    public void setSizeOfElements(int sizeOfElements) {
        LittleEndian.putShort( _complexData, 4, (short) sizeOfElements );

        int expectedArraySize = getNumberOfElementsInArray() * getActualSizeOfElements(getSizeOfElements()) + FIXED_SIZE;
        if (expectedArraySize != _complexData.length) {
            // Keep just the first 6 bytes.  The rest is no good to us anyway.
            byte[] newArray = new byte[expectedArraySize];
            System.arraycopy( _complexData, 0, newArray, 0, 6 );
            _complexData = newArray;
        }
    }

    public byte[] getElement(int index) 
    {
        int actualSize = getActualSizeOfElements(getSizeOfElements());
        byte[] result = new byte[actualSize];
        int srcPos = FIXED_SIZE + index * actualSize; 
        if (srcPos + result.length <= _complexData.length)
        {
            System.arraycopy(_complexData, srcPos, result, 0, result.length );
        }
        return result;
    }

    public void setElement(int index, byte[] element) {
        int actualSize = getActualSizeOfElements(getSizeOfElements());
        System.arraycopy( element, 0, _complexData, FIXED_SIZE + index * actualSize, actualSize);
    }

    public String toString() {
        StringBuffer results = new StringBuffer();
        results.append("    {EscherArrayProperty:" + '\n');
        results.append("     Num Elements: " + getNumberOfElementsInArray() + '\n');
        results.append("     Num Elements In Memory: " + getNumberOfElementsInMemory() + '\n');
        results.append("     Size of elements: " + getSizeOfElements() + '\n');
        for (int i = 0; i < getNumberOfElementsInArray(); i++) {
            results.append("     Element " + i + ": " + HexDump.toHex(getElement(i)) + '\n');
        }
        results.append("}" + '\n');

        return "propNum: " + getPropertyNumber()
                + ", propName: " + EscherProperties.getPropertyName( getPropertyNumber() )
                + ", complex: " + isComplex()
                + ", blipId: " + isBlipId()
                + ", data: " + '\n' + results.toString();
    }


    public int setArrayData(byte[] data, int offset) {
        if (emptyComplexPart){
            _complexData = new byte[0];
        } else {
            short numElements = LittleEndian.getShort(data, offset);
            LittleEndian.getShort(data, offset + 2); // numReserved
            short sizeOfElements = LittleEndian.getShort(data, offset + 4);

            int arraySize = getActualSizeOfElements(sizeOfElements) * numElements;
            if (arraySize == _complexData.length) {
                // The stored data size in the simple block excludes the header size
                _complexData = new byte[arraySize + 6];
                sizeIncludesHeaderSize = false;
            }
            System.arraycopy(data, offset, _complexData, 0, _complexData.length );
        }
        return _complexData.length;
    }


    public int serializeSimplePart(byte[] data, int pos) {
        LittleEndian.putShort(data, pos, getId());
        int recordSize = _complexData.length;
        if(!sizeIncludesHeaderSize) {
            recordSize -= 6;
        }
        LittleEndian.putInt(data, pos + 2, recordSize);
        return 6;
    }


    public static int getActualSizeOfElements(short sizeOfElements) {
        if (sizeOfElements < 0) {
            return (short) ( ( -sizeOfElements ) >> 2 );
        }
        return sizeOfElements;
    }
}
