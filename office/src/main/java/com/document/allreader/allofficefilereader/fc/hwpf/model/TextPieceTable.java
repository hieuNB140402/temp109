
package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.hwpf.model.io.HWPFOutputStream;
import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSConstants;
import com.document.allreader.allofficefilereader.fc.util.Internal;


@Internal
public class TextPieceTable implements CharIndexTranslator
{


    int _cpMin;
    protected ArrayList<TextPiece> _textPieces = new ArrayList<TextPiece>();
    protected ArrayList<TextPiece> _textPiecesFCOrder = new ArrayList<TextPiece>();

    public TextPieceTable()
    {
    }

    public TextPieceTable( byte[] documentStream, byte[] tableStream,
            int offset, int size, int fcMin )
    {

        PlexOfCps pieceTable = new PlexOfCps( tableStream, offset, size,
                PieceDescriptor.getSizeInBytes() );

        int length = pieceTable.length();
        PieceDescriptor[] pieces = new PieceDescriptor[length];


        for ( int x = 0; x < length; x++ )
        {
            GenericPropertyNode node = pieceTable.getProperty( x );
            pieces[x] = new PieceDescriptor( node.getBytes(), 0 );
        }

        _cpMin = pieces[0].getFilePosition() - fcMin;
        for ( int x = 0; x < pieces.length; x++ )
        {
            int start = pieces[x].getFilePosition() - fcMin;
            if ( start < _cpMin )
            {
                _cpMin = start;
            }
        }

        for ( int x = 0; x < pieces.length; x++ )
        {
            int start = pieces[x].getFilePosition();
            GenericPropertyNode node = pieceTable.getProperty( x );

            int nodeStartChars = node.getStart();
            int nodeEndChars = node.getEnd();

            int multiple = pieces[x].isUnicode() ? 2 : 1;

            int textSizeChars = ( nodeEndChars - nodeStartChars );
            int textSizeBytes = textSizeChars * multiple;


            _textPieces.add( new TextPiece( nodeStartChars, nodeEndChars, documentStream, start, textSizeBytes,
                    pieces[x] ) );
        }


        Collections.sort( _textPieces );
        _textPiecesFCOrder = new ArrayList<TextPiece>( _textPieces );
        Collections.sort( _textPiecesFCOrder, new FCComparator() );
    }

    public void add( TextPiece piece )
    {
        _textPieces.add( piece );
        _textPiecesFCOrder.add( piece );
        Collections.sort( _textPieces );
        Collections.sort( _textPiecesFCOrder, new FCComparator() );
    }



    public boolean equals( Object o )
    {
        TextPieceTable tpt = (TextPieceTable) o;

        int size = tpt._textPieces.size();
        if ( size == _textPieces.size() )
        {
            for ( int x = 0; x < size; x++ )
            {
                if ( !tpt._textPieces.get( x ).equals( _textPieces.get( x ) ) )
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int getByteIndex( int charPos )
    {
        int byteCount = 0;
        for ( TextPiece tp : _textPieces )
        {
            if ( charPos >= tp.getEnd() )
            {
                byteCount = tp.getPieceDescriptor().getFilePosition()
                        + ( tp.getEnd() - tp.getStart() )
                        * ( tp.isUnicode() ? 2 : 1 );

                if ( charPos == tp.getEnd() )
                    break;

                continue;
            }
            if ( charPos < tp.getEnd() )
            {
                int left = charPos - tp.getStart();
                byteCount = tp.getPieceDescriptor().getFilePosition() + left
                        * ( tp.isUnicode() ? 2 : 1 );
                break;
            }
        }
        return byteCount;
    }

    public int getCharIndex( int bytePos )
    {
        return getCharIndex( bytePos, 0 );
    }

    public int getCharIndex( int startBytePos, int startCP )
    {
        int charCount = 0;

        int bytePos = lookIndexForward( startBytePos );

        for ( TextPiece tp : _textPieces )
        {
            int pieceStart = tp.getPieceDescriptor().getFilePosition();

            int bytesLength = tp.bytesLength();
            int pieceEnd = pieceStart + bytesLength;

            int toAdd;

            if ( bytePos < pieceStart || bytePos > pieceEnd )
            {
                toAdd = bytesLength;
            }
            else if ( bytePos > pieceStart && bytePos < pieceEnd )
            {
                toAdd = ( bytePos - pieceStart );
            }
            else
            {
                toAdd = bytesLength - ( pieceEnd - bytePos );
            }

            if ( tp.isUnicode() )
            {
                charCount += toAdd / 2;
            }
            else
            {
                charCount += toAdd;
            }

            if ( bytePos >= pieceStart && bytePos <= pieceEnd
                    && charCount >= startCP )
            {
                break;
            }
        }

        return charCount;
    }

    public int getCpMin()
    {
        return _cpMin;
    }

    public StringBuilder getText()
    {
        final long start = System.currentTimeMillis();

        // rebuild document paragraphs structure
        StringBuilder docText = new StringBuilder();
        for ( TextPiece textPiece : _textPieces )
        {
            String toAppend = textPiece.getStringBuilder().toString();
            int toAppendLength = toAppend.length();

            if ( toAppendLength != textPiece.getEnd() - textPiece.getStart() )
            {
                /*logger.log(
                        POILogger.WARN,
                        "Text piece has boundaries [",
                        Integer.valueOf( textPiece.getStart() ),
                        "; ",
                        Integer.valueOf( textPiece.getEnd() ),
                        ") but length ",
                        Integer.valueOf( textPiece.getEnd()
                                - textPiece.getStart() ) );*/
            }

            docText.replace( textPiece.getStart(), textPiece.getStart()
                    + toAppendLength, toAppend );
        }


        return docText;
    }

    public List<TextPiece> getTextPieces()
    {
        return _textPieces;
    }

    @Override
    public int hashCode()
    {
        return _textPieces.size();
    }

    public boolean isIndexInTable( int bytePos )
    {
        for ( TextPiece tp : _textPiecesFCOrder )
        {
            int pieceStart = tp.getPieceDescriptor().getFilePosition();

            if ( bytePos > pieceStart + tp.bytesLength() )
            {
                continue;
            }

            if ( pieceStart > bytePos )
            {
                return false;
            }

            return true;
        }

        return false;
    }

    boolean isIndexInTable( int startBytePos, int endBytePos )
    {
        for ( TextPiece tp : _textPiecesFCOrder )
        {
            int pieceStart = tp.getPieceDescriptor().getFilePosition();

            if ( startBytePos >= pieceStart + tp.bytesLength() )
            {
                continue;
            }

            int left = Math.max( startBytePos, pieceStart );
            int right = Math.min( endBytePos, pieceStart + tp.bytesLength() );

            if ( left >= right )
                return false;

            return true;
        }

        return false;
    }

    public int lookIndexBackward( final int startBytePos )
    {
        int bytePos = startBytePos;
        int lastEnd = 0;

        for ( TextPiece tp : _textPiecesFCOrder )
        {
            int pieceStart = tp.getPieceDescriptor().getFilePosition();

            if ( bytePos > pieceStart + tp.bytesLength() )
            {
                lastEnd = pieceStart + tp.bytesLength();
                continue;
            }

            if ( pieceStart > bytePos )
            {
                bytePos = lastEnd;
            }

            break;
        }

        return bytePos;
    }

    public int lookIndexForward( final int startBytePos )
    {
        int bytePos = startBytePos;
        for ( TextPiece tp : _textPiecesFCOrder )
        {
            int pieceStart = tp.getPieceDescriptor().getFilePosition();

            if ( bytePos >= pieceStart + tp.bytesLength() )
            {
                continue;
            }

            if ( pieceStart > bytePos )
            {
                bytePos = pieceStart;
            }

            break;
        }
        return bytePos;
    }

    public byte[] writeTo( HWPFOutputStream docStream ) throws IOException
    {
        PlexOfCps textPlex = new PlexOfCps( PieceDescriptor.getSizeInBytes() );


        int size = _textPieces.size();
        for ( int x = 0; x < size; x++ )
        {
            TextPiece next = _textPieces.get( x );
            PieceDescriptor pd = next.getPieceDescriptor();

            int offset = docStream.getOffset();
            int mod = ( offset % POIFSConstants.SMALLER_BIG_BLOCK_SIZE );
            if ( mod != 0 )
            {
                mod = POIFSConstants.SMALLER_BIG_BLOCK_SIZE - mod;
                byte[] buf = new byte[mod];
                docStream.write( buf );
            }

            pd.setFilePosition( docStream.getOffset() );


            docStream.write( next.getRawBytes() );

            int nodeStart = next.getStart();
            int nodeEnd = next.getEnd();
            textPlex.addProperty( new GenericPropertyNode( nodeStart, nodeEnd,
                    pd.toByteArray() ) );
        }

        return textPlex.toByteArray();
    }

    private static class FCComparator implements Comparator<TextPiece>
    {
        public int compare( TextPiece textPiece, TextPiece textPiece1 )
        {
            if ( textPiece.getPieceDescriptor().fc > textPiece1
                    .getPieceDescriptor().fc )
            {
                return 1;
            }
            else if ( textPiece.getPieceDescriptor().fc < textPiece1
                    .getPieceDescriptor().fc )
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }
}
