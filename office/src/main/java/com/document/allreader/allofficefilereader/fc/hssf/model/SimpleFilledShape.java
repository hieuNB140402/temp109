

package com.document.allreader.allofficefilereader.fc.hssf.model;

import com.document.allreader.allofficefilereader.common.shape.ShapeTypes;
import com.document.allreader.allofficefilereader.fc.ddf.EscherClientDataRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;
import com.document.allreader.allofficefilereader.fc.hssf.record.CommonObjectDataSubRecord;
import com.document.allreader.allofficefilereader.fc.hssf.record.EndSubRecord;
import com.document.allreader.allofficefilereader.fc.hssf.record.ObjRecord;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFShape;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFSimpleShape;


public class SimpleFilledShape
        extends AbstractShape
{
    private EscherContainerRecord spContainer;
    private ObjRecord objRecord;

    /**
     * Creates the low evel records for an oval.
     *
     * @param hssfShape  The highlevel shape.
     * @param shapeId    The shape id to use for this shape.
     */
    SimpleFilledShape( HSSFSimpleShape hssfShape, int shapeId )
    {
        spContainer = createSpContainer( hssfShape, shapeId );
        objRecord = createObjRecord( hssfShape, shapeId );
    }

    /**
     * Generates the shape records for this shape.
     *
     * @param hssfShape
     * @param shapeId
     */
    private EscherContainerRecord createSpContainer( HSSFSimpleShape hssfShape, int shapeId )
    {
        HSSFShape shape = hssfShape;

        EscherContainerRecord spContainer = new EscherContainerRecord();
        EscherSpRecord sp = new EscherSpRecord();
        EscherOptRecord opt = new EscherOptRecord();
        EscherClientDataRecord clientData = new EscherClientDataRecord();

        spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
        spContainer.setOptions( (short) 0x000F );
        sp.setRecordId( EscherSpRecord.RECORD_ID );
        short shapeType = objTypeToShapeType( hssfShape.getShapeType() );
        sp.setOptions( (short) ( ( shapeType << 4 ) | 0x2 ) );
        sp.setShapeId( shapeId );
        sp.setFlags( EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE );
        opt.setRecordId( EscherOptRecord.RECORD_ID );
        addStandardOptions(shape, opt);
        EscherRecord anchor = createAnchor( shape.getAnchor() );
        clientData.setRecordId( EscherClientDataRecord.RECORD_ID );
        clientData.setOptions( (short) 0x0000 );

        spContainer.addChildRecord( sp );
        spContainer.addChildRecord( opt );
        spContainer.addChildRecord( anchor );
        spContainer.addChildRecord( clientData );

        return spContainer;
    }

    private short objTypeToShapeType( int objType )
    {
        short shapeType;
        if (objType == HSSFSimpleShape.OBJECT_TYPE_OVAL)
            shapeType = ShapeTypes.Ellipse;
        else if (objType == HSSFSimpleShape.OBJECT_TYPE_RECTANGLE)
            shapeType = ShapeTypes.Rectangle;
        else
            throw new IllegalArgumentException("Unable to handle an object of this type");
        return shapeType;
    }

    /**
     * Creates the low level OBJ record for this shape.
     */
    private ObjRecord createObjRecord( HSSFShape hssfShape, int shapeId )
    {
        HSSFShape shape = hssfShape;

        ObjRecord obj = new ObjRecord();
        CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
        c.setObjectType( (short) ( (HSSFSimpleShape) shape ).getShapeType() );
        c.setObjectId(  getCmoObjectId(shapeId) );
        c.setLocked( true );
        c.setPrintable( true );
        c.setAutofill( true );
        c.setAutoline( true );
        EndSubRecord e = new EndSubRecord();

        obj.addSubRecord( c );
        obj.addSubRecord( e );

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
