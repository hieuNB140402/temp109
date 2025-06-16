

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.java.awt.geom.Rectangle2D;


public class AutoShape extends TextShape
{
    protected AutoShape(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);
    }
    
    protected void setDefaultTextProperties(TextRun _txtrun)
    {
        setVerticalAlignment(TextBox.AnchorMiddle);
        setHorizontalAlignment(TextBox.AlignCenter);
        setWordWrap(TextBox.WrapNone);
    }


    public AutoShape(int type, Shape parent)
    {
        super(null, parent);
        _escherContainer = createSpContainer(type, parent instanceof ShapeGroup);
    }

    public AutoShape(int type)
    {
        this(type, null);
    }
    
    protected EscherContainerRecord createSpContainer(int shapeType, boolean isChild)
    {
        _escherContainer = super.createSpContainer(isChild);

        setShapeType(shapeType);

        setEscherProperty(EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 0x40000);
        setEscherProperty(EscherProperties.FILL__FILLCOLOR, 0x8000004);
        setEscherProperty(EscherProperties.FILL__FILLCOLOR, 0x8000004);
        setEscherProperty(EscherProperties.FILL__FILLBACKCOLOR, 0x8000000);
        setEscherProperty(EscherProperties.FILL__NOFILLHITTEST, 0x100010);
        setEscherProperty(EscherProperties.LINESTYLE__COLOR, 0x8000001);
        setEscherProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 0x80008);
        setEscherProperty(EscherProperties.SHADOWSTYLE__COLOR, 0x8000002);

        return _escherContainer;
    }

    public int getAdjustmentValue(int idx)
    {
        if (idx < 0 || idx > 9)
        {
            throw new IllegalArgumentException(
                "The index of an adjustment value must be in the [0, 9] range");
        }
        return ShapeKit.getEscherProperty(_escherContainer, (short)(EscherProperties.GEOMETRY__ADJUSTVALUE + idx));
    }


    public void setAdjustmentValue(int idx, int val)
    {
        if (idx < 0 || idx > 9)
        {
            throw new IllegalArgumentException(
                "The index of an adjustment value must be in the [0, 9] range");
        }
        setEscherProperty((short)(EscherProperties.GEOMETRY__ADJUSTVALUE + idx), val);
    }

    public com.document.allreader.allofficefilereader.java.awt.Shape getOutline()
    {
        ShapeOutline outline = AutoShapes.getShapeOutline(getShapeType());
        Rectangle2D anchor = getLogicalAnchor2D();
        if (outline == null)
        {
            return anchor;
        }
        com.document.allreader.allofficefilereader.java.awt.Shape shape = outline.getOutline(this);
        return AutoShapes.transform(shape, anchor);
    }
    
    public void dispose()
    {
        super.dispose();
    }
}
