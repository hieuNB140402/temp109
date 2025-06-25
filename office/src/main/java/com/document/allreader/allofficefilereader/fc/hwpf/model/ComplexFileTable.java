

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.hwpf.sprm.SprmBuffer;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


@ Internal
public final class ComplexFileTable
{

    private static final byte GRPPRL_TYPE = 1;
    private static final byte TEXT_PIECE_TABLE_TYPE = 2;

    protected TextPieceTable _tpt;

    private SprmBuffer[] _grpprls;

    public ComplexFileTable()
    {
        _tpt = new TextPieceTable();
    }

    public ComplexFileTable(byte[] documentStream, byte[] tableStream, int offset, int fcMin)
        throws IOException
    {

        List<SprmBuffer> sprmBuffers = new LinkedList<SprmBuffer>();
        while (tableStream[offset] == GRPPRL_TYPE)
        {
            offset++;
            int size = LittleEndian.getShort(tableStream, offset);
            offset += LittleEndian.SHORT_SIZE;
            byte[] bs = LittleEndian.getByteArray(tableStream, offset, size);
            offset += size;

            SprmBuffer sprmBuffer = new SprmBuffer(bs, false, 0);
            sprmBuffers.add(sprmBuffer);
        }
        this._grpprls = sprmBuffers.toArray(new SprmBuffer[sprmBuffers.size()]);

        if (tableStream[offset] != TEXT_PIECE_TABLE_TYPE)
        {
            throw new IOException("The text piece table is corrupted");
        }
        int pieceTableSize = LittleEndian.getInt(tableStream, ++offset);
        offset += LittleEndian.INT_SIZE;
        _tpt = new TextPieceTable(documentStream, tableStream, offset, pieceTableSize, fcMin);
    }

    public TextPieceTable getTextPieceTable()
    {
        return _tpt;
    }

    public SprmBuffer[] getGrpprls()
    {
        return _grpprls;
    }

}
