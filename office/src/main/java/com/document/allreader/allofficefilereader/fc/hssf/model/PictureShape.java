


package com.document.allreader.allofficefilereader.fc.hssf.model;

import com.document.allreader.allofficefilereader.common.shape.ShapeTypes;
import com.document.allreader.allofficefilereader.fc.ddf.EscherClientDataRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSimpleProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;
import com.document.allreader.allofficefilereader.fc.hssf.record.CommonObjectDataSubRecord;
import com.document.allreader.allofficefilereader.fc.hssf.record.EndSubRecord;
import com.document.allreader.allofficefilereader.fc.hssf.record.ObjRecord;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFAnchor;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFPicture;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFShape;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFSimpleShape;


/**
 * Represents a picture shape and creates all specific low level records.
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public class PictureShape
        extends AbstractShape
{
    private EscherContainerRecord spContainer;
    private ObjRecord objRecord;

    /**
     * Creates the line shape from the highlevel user shape.  All low level
     * records are created at this point.
     *
     * @param hssfShape     The user model shape.
     * @param shapeId       The identifier to use for this shape.
     */
    PictureShape( HSSFSimpleShape hssfShape, int shapeId )
    {
        spContainer = createSpContainer(hssfShape, shapeId);
        objRecord = createObjRecord(hssfShape, shapeId);
    }

    /**
     * Creates the lowerlevel escher records for this shape.
     */
    private EscherContainerRecord createSpContainer(HSSFSimpleShape hssfShape, int shapeId)
    {
        HSSFPicture shape = (HSSFPicture) hssfShape;

        EscherContainerRecord spContainer = new EscherContainerRecord();
        EscherSpRecord sp = new EscherSpRecord();
        EscherOptRecord opt = new EscherOptRecord();
        EscherRecord anchor;
        EscherClientDataRecord clientData = new EscherClientDataRecord();

        spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
        spContainer.setOptions( (short) 0x000F );
        sp.setRecordId( EscherSpRecord.RECORD_ID );
        sp.setOptions( (short) ( (ShapeTypes.PictureFrame << 4) | 0x2 ) );

        sp.setShapeId( shapeId );
        sp.setFlags( EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE );
        opt.setRecordId( EscherOptRecord.RECORD_ID );
//        opt.addEscherProperty( new EscherBoolProperty( EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 0x00800080 ) );
        opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.BLIP__BLIPTODISPLAY, false, true, shape.getPictureIndex() ) );
//        opt.addEscherProperty( new EscherComplexProperty( EscherProperties.BLIP__BLIPFILENAME, true, new byte[] { (byte)0x74, (byte)0x00, (byte)0x65, (byte)0x00, (byte)0x73, (byte)0x00, (byte)0x74, (byte)0x00, (byte)0x00, (byte)0x00 } ) );
//        opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.FILL__FILLTYPE, 0x00000003 ) );
        addStandardOptions(shape, opt);
        HSSFAnchor userAnchor = shape.getAnchor();
        if (userAnchor.isHorizontallyFlipped())
            sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_FLIPHORIZ);
        if (userAnchor.isVerticallyFlipped())
            sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_FLIPVERT);
        anchor = createAnchor(userAnchor);
        clientData.setRecordId( EscherClientDataRecord.RECORD_ID );
        clientData.setOptions( (short) 0x0000 );

        spContainer.addChildRecord(sp);
        spContainer.addChildRecord(opt);
        spContainer.addChildRecord(anchor);
        spContainer.addChildRecord(clientData);

        return spContainer;
    }

    /**
     * Creates the low level OBJ record for this shape.
     */
    private ObjRecord createObjRecord(HSSFShape hssfShape, int shapeId)
    {
        HSSFShape shape = hssfShape;

        ObjRecord obj = new ObjRecord();
        CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
        c.setObjectType((short) ((HSSFSimpleShape)shape).getShapeType());
        c.setObjectId( getCmoObjectId(shapeId) );
        c.setLocked(true);
        c.setPrintable(true);
        c.setAutofill(true);
        c.setAutoline(true);
        c.setReserved2( 0x0 );
        EndSubRecord e = new EndSubRecord();

        obj.addSubRecord(c);
        obj.addSubRecord(e);

        return obj;
    }

    public EscherContainerRecord getSpContainer()
    {
        return spContainer;
    }

    public ObjRecord getObjRecord()
    {
        return objRecord;
    }

}
