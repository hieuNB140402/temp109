

package com.document.allreader.allofficefilereader.fc.hssf.model;


import com.document.allreader.allofficefilereader.fc.ddf.EscherChildAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherClientAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFAnchor;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFChildAnchor;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFClientAnchor;


/**
 *
 */
public class ConvertAnchor
{
    public static EscherRecord createAnchor( HSSFAnchor userAnchor )
    {
        if (userAnchor instanceof HSSFClientAnchor)
        {
            HSSFClientAnchor a = (HSSFClientAnchor) userAnchor;

            EscherClientAnchorRecord anchor = new EscherClientAnchorRecord();
            anchor.setRecordId( EscherClientAnchorRecord.RECORD_ID );
            anchor.setOptions( (short) 0x0000 );
            anchor.setFlag( (short) a.getAnchorType() );
            anchor.setCol1( (short) Math.min(a.getCol1(), a.getCol2()) );
            anchor.setDx1( (short) a.getDx1() );
            anchor.setRow1( (short) Math.min(a.getRow1(), a.getRow2()) );
            anchor.setDy1( (short) a.getDy1() );

            anchor.setCol2( (short) Math.max(a.getCol1(), a.getCol2()) );
            anchor.setDx2( (short) a.getDx2() );
            anchor.setRow2( (short) Math.max(a.getRow1(), a.getRow2()) );
            anchor.setDy2( (short) a.getDy2() );
            return anchor;
        }
        HSSFChildAnchor a = (HSSFChildAnchor) userAnchor;
        EscherChildAnchorRecord anchor = new EscherChildAnchorRecord();
        anchor.setRecordId( EscherChildAnchorRecord.RECORD_ID );
        anchor.setOptions( (short) 0x0000 );
        anchor.setDx1( (short) Math.min(a.getDx1(), a.getDx2()) );
        anchor.setDy1( (short) Math.min(a.getDy1(), a.getDy2()) );
        anchor.setDx2( (short) Math.max(a.getDx2(), a.getDx1()) );
        anchor.setDy2( (short) Math.max(a.getDy2(), a.getDy1()) );
        return anchor;
    }
}
