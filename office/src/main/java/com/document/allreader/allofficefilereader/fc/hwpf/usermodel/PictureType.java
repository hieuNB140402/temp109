
package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;


public enum PictureType {
    BMP( "image/bmp", "bmp", new byte[][] { { 'B', 'M' } } ),

    EMF( "image/x-emf", "emf", new byte[][] { { 0x01, 0x00, 0x00, 0x00 } } ),

    GIF( "image/gif", "gif", new byte[][] { { 'G', 'I', 'F' } } ),

    JPEG( "image/jpeg", "jpg", new byte[][] { { (byte) 0xFF, (byte) 0xD8 } } ),

    PNG( "image/png", "png", new byte[][] { { (byte) 0x89, 0x50, 0x4E, 0x47,
            0x0D, 0x0A, 0x1A, 0x0A } } ),

    TIFF( "image/tiff", "tiff", new byte[][] { { 0x49, 0x49, 0x2A, 0x00 },
            { 0x4D, 0x4D, 0x00, 0x2A } } ),

    UNKNOWN( "image/unknown", "", new byte[][] {} ),

    WMF( "image/x-wmf", "wmf", new byte[][] {
            { (byte) 0xD7, (byte) 0xCD, (byte) 0xC6, (byte) 0x9A, 0x00, 0x00 },
            { 0x01, 0x00, 0x09, 0x00, 0x00, 0x03 } } );

    public static PictureType findMatchingType( byte[] pictureContent )
    {
        for ( PictureType pictureType : PictureType.values() )
            for ( byte[] signature : pictureType.getSignatures() )
                if ( matchSignature( pictureContent, signature ) )
                    return pictureType;


        return PictureType.UNKNOWN;
    }

    private static boolean matchSignature( byte[] pictureData, byte[] signature )
    {
        if ( pictureData.length < signature.length )
            return false;

        for ( int i = 0; i < signature.length; i++ )
            if ( pictureData[i] != signature[i] )
                return false;

        return true;
    }

    private String _extension;

    private String _mime;

    private byte[][] _signatures;

    private PictureType( String mime, String extension, byte[][] signatures )
    {
        this._mime = mime;
        this._extension = extension;
        this._signatures = signatures;
    }

    public String getExtension()
    {
        return _extension;
    }

    public String getMime()
    {
        return _mime;
    }

    public byte[][] getSignatures()
    {
        return _signatures;
    }

    public boolean matchSignature( byte[] pictureData )
    {
        for ( byte[] signature : getSignatures() )
            if ( matchSignature( signature, pictureData ) )
                return true;
        return false;
    }
}
