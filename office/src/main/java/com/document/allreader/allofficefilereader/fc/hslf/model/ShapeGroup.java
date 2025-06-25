

package com.document.allreader.allofficefilereader.fc.hslf.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.document.allreader.allofficefilereader.common.shape.ShapeTypes;
import com.document.allreader.allofficefilereader.constant.MainConstant;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherChildAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherClientAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpgrRecord;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.java.awt.Rectangle;
import com.document.allreader.allofficefilereader.java.awt.geom.Rectangle2D;


public class ShapeGroup extends Shape
{

    public ShapeGroup()
    {
        this(null, null);
        _escherContainer = createSpContainer(false);
    }


    protected ShapeGroup(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);
    }


    public int getShapeId()
    {
        Iterator<EscherRecord> iter = _escherContainer.getChildIterator();

        // group shape ID
        int grpShapeID = 0;
        if (iter.hasNext())
        {
            EscherRecord r = iter.next();
            if (r instanceof EscherContainerRecord)
            {
                // Create the Shape for it
                EscherContainerRecord container = (EscherContainerRecord)r;
                EscherSpRecord spRecord = container.getChildById(EscherSpRecord.RECORD_ID);
                grpShapeID = spRecord.getShapeId();
            }
        }      
        return grpShapeID;
    }
    

    public Shape[] getShapes()
    {

        Iterator<EscherRecord> iter = _escherContainer.getChildIterator();

        if (iter.hasNext())
        {
            iter.next();
        }
        List<Shape> shapeList = new ArrayList<Shape>();
        while (iter.hasNext())
        {
            EscherRecord r = iter.next();
            if (r instanceof EscherContainerRecord)
            {
                // Create the Shape for it
                EscherContainerRecord container = (EscherContainerRecord)r;
                Shape shape = ShapeFactory.createShape(container, this);
                shape.setSheet(getSheet());
                shapeList.add(shape);
            }
        }


        Shape[] shapes = shapeList.toArray(new Shape[shapeList.size()]);
        return shapes;
    }


    public void setAnchor(Rectangle anchor)
    {
        EscherContainerRecord spContainer = (EscherContainerRecord)_escherContainer.getChild(0);

        EscherClientAnchorRecord clientAnchor = (EscherClientAnchorRecord)ShapeKit.getEscherChild(
            spContainer, EscherClientAnchorRecord.RECORD_ID);

        byte[] header = new byte[16];
        LittleEndian.putUShort(header, 0, 0);
        LittleEndian.putUShort(header, 2, 0);
        LittleEndian.putInt(header, 4, 8);
        clientAnchor.fillFields(header, 0, null);

        clientAnchor.setFlag((short)(anchor.y * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
        clientAnchor.setCol1((short)(anchor.x * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
        clientAnchor.setDx1((short)((anchor.width + anchor.x) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
        clientAnchor.setRow1((short)((anchor.height + anchor.y) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));

        EscherSpgrRecord spgr = (EscherSpgrRecord)ShapeKit.getEscherChild(spContainer,
            EscherSpgrRecord.RECORD_ID);

        spgr.setRectX1((int)(anchor.x * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
        spgr.setRectY1((int)(anchor.y * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
        spgr.setRectX2((int)((anchor.x + anchor.width) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
        spgr.setRectY2((int)((anchor.y + anchor.height) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
    }


    public void setCoordinates(Rectangle2D anchor)
    {
        EscherContainerRecord spContainer = (EscherContainerRecord)_escherContainer.getChild(0);
        EscherSpgrRecord spgr = (EscherSpgrRecord)ShapeKit.getEscherChild(spContainer,
            EscherSpgrRecord.RECORD_ID);

        int x1 = (int)Math.round(anchor.getX() * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI);
        int y1 = (int)Math.round(anchor.getY() * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI);
        int x2 = (int)Math.round((anchor.getX() + anchor.getWidth()) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI);
        int y2 = (int)Math.round((anchor.getY() + anchor.getHeight()) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI);

        spgr.setRectX1(x1);
        spgr.setRectY1(y1);
        spgr.setRectX2(x2);
        spgr.setRectY2(y2);
    }

    public Rectangle2D getCoordinates()
    {
        EscherContainerRecord spContainer = (EscherContainerRecord)_escherContainer.getChild(0);
        EscherSpgrRecord spgr = (EscherSpgrRecord)ShapeKit.getEscherChild(spContainer,
            EscherSpgrRecord.RECORD_ID);

        Rectangle2D.Float anchor = new Rectangle2D.Float();
        anchor.x = (float)spgr.getRectX1() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI;
        anchor.y = (float)spgr.getRectY1() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI;
        anchor.width = (float)(spgr.getRectX2() - spgr.getRectX1()) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI;
        anchor.height = (float)(spgr.getRectY2() - spgr.getRectY1()) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI;

        return anchor;
    }
    

    public Rectangle2D getClientAnchor2D(Shape shape)
    {
        Rectangle2D anchor = shape.getAnchor2D();
        if (shape != null && shape.getParent() != null)
        {
            Rectangle2D clientAnchor = ((ShapeGroup)shape.getParent()).getClientAnchor2D(shape.getParent());
            Rectangle2D spgrAnchor = ((ShapeGroup)shape.getParent()).getCoordinates();

            double scalex = spgrAnchor.getWidth() / clientAnchor.getWidth();
            double scaley = spgrAnchor.getHeight() / clientAnchor.getHeight();

            double x = clientAnchor.getX() + (anchor.getX() - spgrAnchor.getX()) / scalex;
            double y = clientAnchor.getY() + (anchor.getY() - spgrAnchor.getY()) / scaley;
            double width = anchor.getWidth() / scalex;
            double height = anchor.getHeight() / scaley;

            anchor = new Rectangle2D.Double(x, y, width, height);
        }
        return anchor;
    }


    protected EscherContainerRecord createSpContainer(boolean isChild)
    {
        EscherContainerRecord spgr = new EscherContainerRecord();
        spgr.setRecordId(EscherContainerRecord.SPGR_CONTAINER);
        spgr.setOptions((short)15);

        EscherContainerRecord spcont = new EscherContainerRecord();
        spcont.setRecordId(EscherContainerRecord.SP_CONTAINER);
        spcont.setOptions((short)15);

        EscherSpgrRecord spg = new EscherSpgrRecord();
        spg.setOptions((short)1);
        spcont.addChildRecord(spg);

        EscherSpRecord sp = new EscherSpRecord();
        short type = (ShapeTypes.NotPrimitive << 4) + 2;
        sp.setOptions(type);
        sp.setFlags(EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_GROUP);
        spcont.addChildRecord(sp);

        EscherClientAnchorRecord anchor = new EscherClientAnchorRecord();
        spcont.addChildRecord(anchor);

        spgr.addChildRecord(spcont);
        return spgr;
    }

    public void addShape(Shape shape)
    {
        _escherContainer.addChildRecord(shape.getSpContainer());

        Sheet sheet = getSheet();
        shape.setSheet(sheet);
        shape.setShapeId(sheet.allocateShapeId());
        shape.afterInsert(sheet);
    }

    public void moveTo(int x, int y)
    {
        Rectangle anchor = getAnchor();
        int dx = x - anchor.x;
        int dy = y - anchor.y;
        anchor.translate(dx, dy);
        setAnchor(anchor);

        Shape[] shape = getShapes();
        for (int i = 0; i < shape.length; i++)
        {
            Rectangle chanchor = shape[i].getAnchor();
            chanchor.translate(dx, dy);
            shape[i].setAnchor(chanchor);
        }
    }

    public Rectangle2D getAnchor2D()
    {
        EscherContainerRecord spContainer = (EscherContainerRecord)_escherContainer.getChild(0);
        EscherClientAnchorRecord clientAnchor = (EscherClientAnchorRecord)ShapeKit.getEscherChild(
            spContainer, EscherClientAnchorRecord.RECORD_ID);
        Rectangle2D.Float anchor = new Rectangle2D.Float();
        if (clientAnchor == null)
        {
              EscherChildAnchorRecord rec = (EscherChildAnchorRecord)ShapeKit.getEscherChild(spContainer,
                EscherChildAnchorRecord.RECORD_ID);
            anchor = new Rectangle2D.Float((float)rec.getDx1() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                (float)rec.getDy1() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI, (float)(rec.getDx2() - rec.getDx1())
                    * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI, (float)(rec.getDy2() - rec.getDy1()) * MainConstant.POINT_DPI
                    / ShapeKit.MASTER_DPI);
        }
        else
        {
            anchor.x = (float)clientAnchor.getCol1() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI;
            anchor.y = (float)clientAnchor.getFlag() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI;
            anchor.width = (float)(clientAnchor.getDx1() - clientAnchor.getCol1()) * MainConstant.POINT_DPI
                / ShapeKit.MASTER_DPI;
            anchor.height = (float)(clientAnchor.getRow1() - clientAnchor.getFlag()) * MainConstant.POINT_DPI
                / ShapeKit.MASTER_DPI;
        }

        return anchor;
    }


    public int getShapeType()
    {
        EscherContainerRecord groupInfoContainer = (EscherContainerRecord)_escherContainer
            .getChild(0);
        EscherSpRecord spRecord = groupInfoContainer.getChildById(EscherSpRecord.RECORD_ID);
        return spRecord.getOptions() >> 4;
    }

    public Hyperlink getHyperlink()
    {
        return null;
    }


    public boolean getFlipHorizontal()
    {
        return ShapeKit.getGroupFlipHorizontal(getSpContainer());
    }


    public boolean getFlipVertical()
    {
        return ShapeKit.getGroupFlipVertical(getSpContainer());
    }
    

    public int getRotation()
    {
        return ShapeKit.getGroupRotation(getSpContainer());
    }
    
    public void dispose()
    {
        super.dispose();
    }
}
