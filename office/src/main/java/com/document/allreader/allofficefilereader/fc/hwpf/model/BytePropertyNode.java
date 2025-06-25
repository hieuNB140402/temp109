

package com.document.allreader.allofficefilereader.fc.hwpf.model;



@Deprecated
public abstract class BytePropertyNode<T extends BytePropertyNode<T>> extends
        PropertyNode<T>
{
        private final int startBytes;
        private final int endBytes;


	public BytePropertyNode(int fcStart, int fcEnd, CharIndexTranslator translator, Object buf) {
		super(
				translator.getCharIndex(fcStart),
				translator.getCharIndex(fcEnd, translator.getCharIndex(fcStart)),
				buf
		);

        if ( fcStart > fcEnd )
            throw new IllegalArgumentException( "fcStart (" + fcStart
                    + ") > fcEnd (" + fcEnd + ")" );

                this.startBytes = fcStart;
                this.endBytes = fcEnd;
	}

    public BytePropertyNode( int charStart, int charEnd, Object buf )
    {
        super( charStart, charEnd, buf );

        if ( charStart > charEnd )
            throw new IllegalArgumentException( "charStart (" + charStart
                    + ") > charEnd (" + charEnd + ")" );

        this.startBytes = -1;
        this.endBytes = -1;
    }


	@Deprecated
    public int getStartBytes()
    {
        return startBytes;
    }


    @Deprecated
    public int getEndBytes()
    {
        return endBytes;
    }
}
