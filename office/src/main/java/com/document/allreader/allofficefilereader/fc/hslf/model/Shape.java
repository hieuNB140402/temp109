

package com.document.allreader.allofficefilereader.fc.hslf.model;

import java.util.Iterator;

import com.document.allreader.allofficefilereader.constant.MainConstant;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherChildAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherClientAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSimpleProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;
import com.document.allreader.allofficefilereader.fc.hslf.record.ColorSchemeAtom;
import com.document.allreader.allofficefilereader.java.awt.Color;
import com.document.allreader.allofficefilereader.java.awt.Rectangle;
import com.document.allreader.allofficefilereader.java.awt.geom.Rectangle2D;

public abstract class Shape
{

    protected Shape(EscherContainerRecord escherRecord, Shape parent)
    {
        _escherContainer = escherRecord;
        _parent = parent;
    }
    

    public Shape getParent()
    {
        return _parent;
    }
    

    public int getShapeType()
    {
        return ShapeKit.getShapeType(_escherContainer);
    }
    

    public int getShapeId()
    {
        return ShapeKit.getShapeId(_escherContainer);
    }
    

    public boolean isHidden()
    {
        return ShapeKit.isHidden(_escherContainer);
    }
    

    public int getMasterShapeID()
    {
    	return ShapeKit.getMasterShapeID(_escherContainer);
    }
    

    public Rectangle2D getAnchor2D()
    {
        Rectangle2D anchor = null;
        EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
        int flags = spRecord.getFlags();
        if ((flags & EscherSpRecord.FLAG_CHILD) != 0)
        {
            EscherChildAnchorRecord rec = (EscherChildAnchorRecord)ShapeKit.getEscherChild(_escherContainer,
                EscherChildAnchorRecord.RECORD_ID);
            if (rec == null)
            {
                EscherClientAnchorRecord clrec = (EscherClientAnchorRecord)ShapeKit.getEscherChild(
                    _escherContainer, EscherClientAnchorRecord.RECORD_ID);
                anchor = new Rectangle2D.Float((float)clrec.getCol1() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                    (float)clrec.getFlag() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                    (float)(clrec.getDx1() - clrec.getCol1()) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                    (float)(clrec.getRow1() - clrec.getFlag()) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI);
            }
            else
            {
                anchor = new Rectangle2D.Float((float)rec.getDx1() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                    (float)rec.getDy1() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                    (float)(rec.getDx2() - rec.getDx1()) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                    (float)(rec.getDy2() - rec.getDy1()) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI);
            }
        }
        else
        {
            EscherClientAnchorRecord rec = (EscherClientAnchorRecord)ShapeKit.getEscherChild(
                _escherContainer, EscherClientAnchorRecord.RECORD_ID);
            anchor = new Rectangle2D.Float((float)rec.getCol1() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                (float)rec.getFlag() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                (float)(rec.getDx1() - rec.getCol1()) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI,
                (float)(rec.getRow1() - rec.getFlag()) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI);
        }
        return anchor;
    }
    

    public Rectangle getAnchor()
    {
        Rectangle2D anchor2d = getAnchor2D();
        return anchor2d.getBounds();
    }


    public EscherContainerRecord getSpContainer()
    {
        return _escherContainer;
    }
    

    public Sheet getSheet()
    {
        return _sheet;
    }


    public void setSheet(Sheet sheet)
    {
        _sheet = sheet;
    }
    
    protected Color getColor(int rgb, int alpha)
    {
        if (rgb >= 0x8000000)
        {
            int idx = rgb - 0x8000000;
            ColorSchemeAtom ca = getSheet().getColorScheme();
            if (idx >= 0 && idx <= 7)
            {
                rgb = ca.getColor(idx);
            }
        }
        Color tmp = new Color(rgb, true);
        return new Color(tmp.getBlue(), tmp.getGreen(), tmp.getRed(), alpha);
    }
    

    public Fill getFill()
    {
        if (_fill == null)
        {
            _fill = new Fill(this);
        }
        return _fill;
    }


    public Hyperlink getHyperlink()
    {
        return Hyperlink.find(this);
    }
    

    public static EscherRecord getEscherChild(EscherContainerRecord owner, int recordId)
    {
        for (Iterator<EscherRecord> iterator = owner.getChildIterator(); iterator.hasNext();)
        {
            EscherRecord escherRecord = iterator.next();
            if (escherRecord.getRecordId() == recordId)
                return escherRecord;
        }
        return null;
    }

    public static EscherProperty getEscherProperty(EscherOptRecord opt, int propId)
    {
        if (opt != null)
            for (Iterator iterator = opt.getEscherProperties().iterator(); iterator.hasNext();)
            {
                EscherProperty prop = (EscherProperty)iterator.next();
                if (prop.getPropertyNumber() == propId)
                    return prop;
            }
        return null;
    }


    public static void setEscherProperty(EscherOptRecord opt, short propId, int value)
    {
        java.util.List props = opt.getEscherProperties();
        for (Iterator iterator = props.iterator(); iterator.hasNext();)
        {
            EscherProperty prop = (EscherProperty)iterator.next();
            if (prop.getId() == propId)
            {
                iterator.remove();
            }
        }
        if (value != -1)
        {
            opt.addEscherProperty(new EscherSimpleProperty(propId, value));
            opt.sortProperties();
        }
    }
    

    public void setEscherProperty(short propId, int value)
    {
        EscherOptRecord opt = (EscherOptRecord)getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        setEscherProperty(opt, propId, value);
    }


    public int getEscherProperty(short propId)
    {
        EscherOptRecord opt = (EscherOptRecord)getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, propId);
        return prop == null ? 0 : prop.getPropertyValue();
    }


    public int getEscherProperty(short propId, int defaultValue)
    {
        EscherOptRecord opt = (EscherOptRecord)getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, propId);
        return prop == null ? defaultValue : prop.getPropertyValue();
    }

    
    public Float[] getAdjustmentValue()
    {
        return ShapeKit.getAdjustmentValue(_escherContainer);
    }
    
    public int getStartArrowType()
    {
        EscherOptRecord opt = (EscherOptRecord)getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        if (opt != null)
        {
            EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt,
                EscherProperties.LINESTYLE__LINESTARTARROWHEAD);
            if (prop != null && prop.getPropertyValue() > 0)
            {
                return prop.getPropertyValue();
            }
        }
        return 0;
    }
    
    public int getStartArrowWidth()
    {
        return ShapeKit.getStartArrowWidth(_escherContainer);
    }
    
    public int getStartArrowLength()
    {
        return ShapeKit.getStartArrowLength(_escherContainer);
    }    
    
    public int getEndArrowType()
    {
        EscherOptRecord opt = (EscherOptRecord)getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        if (opt != null)
        {
            EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt,
                EscherProperties.LINESTYLE__LINEENDARROWHEAD);
            if (prop != null && prop.getPropertyValue() > 0)
            {
                return prop.getPropertyValue();
            }
        }
        return 0;
    }
    
    public int getEndArrowWidth()
    {
        return ShapeKit.getEndArrowWidth(_escherContainer);
    }
    
    public int getEndArrowLength()
    {
        return ShapeKit.getEndArrowLength(_escherContainer);
    }
    

    public boolean hasLine()
    {
    	return ShapeKit.hasLine(getSpContainer());
    }
    

    public double getLineWidth()
    {
        return ShapeKit.getLineWidth(getSpContainer());
    }
    

    public Color getLineColor()
    {
        return ShapeKit.getLineColor(getSpContainer(), getSheet(), MainConstant.APPLICATION_TYPE_PPT);
    }
    

    public Color getFillColor()
    {
        return getFill().getForegroundColor();
    }
    

    public boolean getFlipHorizontal()
    {
        return ShapeKit.getFlipHorizontal(getSpContainer());
    }

    public boolean getFlipVertical()
    {
        return ShapeKit.getFlipVertical(getSpContainer());
    }

    public int getRotation()
    {
        return ShapeKit.getRotation(getSpContainer());
    }
    

    public void dispose()
    {
        _parent = null;
        _sheet = null;
        if (_escherContainer != null)
        {
            _escherContainer.dispose();
            _escherContainer = null;
        }
        if (_fill != null)
        {
            _fill.dispose();
            _fill = null;
        }
    }

    

    protected abstract EscherContainerRecord createSpContainer(boolean isChild);


    public String getShapeName()
    {
        return ShapeTypes.typeName(getShapeType());
    }
    

    public void setShapeType(int type)
    {
        EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
        spRecord.setOptions((short)(type << 4 | 0x2));
    }

    public Rectangle2D getLogicalAnchor2D()
    {
        return getAnchor2D();
    }

    public void setAnchor(Rectangle2D anchor)
    {
        EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
        int flags = spRecord.getFlags();
        if ((flags & EscherSpRecord.FLAG_CHILD) != 0)
        {
            EscherChildAnchorRecord rec = (EscherChildAnchorRecord)ShapeKit.getEscherChild(_escherContainer,
                EscherChildAnchorRecord.RECORD_ID);
            rec.setDx1((int)(anchor.getX() * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
            rec.setDy1((int)(anchor.getY() * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
            rec.setDx2((int)((anchor.getWidth() + anchor.getX()) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
            rec.setDy2((int)((anchor.getHeight() + anchor.getY()) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
        }
        else
        {
            EscherClientAnchorRecord rec = (EscherClientAnchorRecord)ShapeKit.getEscherChild(
                _escherContainer, EscherClientAnchorRecord.RECORD_ID);
            rec.setFlag((short)(anchor.getY() * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
            rec.setCol1((short)(anchor.getX() * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
            rec.setDx1((short)(((anchor.getWidth() + anchor.getX()) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI)));
            rec.setRow1((short)(((anchor.getHeight() + anchor.getY()) * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI)));
        }

    }

    public void moveTo(float x, float y)
    {
        Rectangle2D anchor = getAnchor2D();
        anchor.setRect(x, y, anchor.getWidth(), anchor.getHeight());
        setAnchor(anchor);
    }

    protected void afterInsert(Sheet sh)
    {

    }


    public void setShapeId(int id)
    {
        EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
        if (spRecord != null)
        {
            spRecord.setShapeId(id);
        }
    }

    public com.document.allreader.allofficefilereader.java.awt.Shape getOutline()
    {
        return getLogicalAnchor2D();
    }

    protected EscherContainerRecord _escherContainer;

    protected Shape _parent;


    protected Sheet _sheet;

    protected Fill _fill;
}
