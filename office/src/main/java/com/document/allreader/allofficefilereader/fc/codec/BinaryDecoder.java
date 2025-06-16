

package com.document.allreader.allofficefilereader.fc.codec;

public interface BinaryDecoder extends Decoder {

    byte[] decode(byte[] source) throws DecoderException;
}  

