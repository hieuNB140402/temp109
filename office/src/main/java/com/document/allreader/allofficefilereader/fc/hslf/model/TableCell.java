

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.java.awt.Rectangle;



public final class TableCell extends TextBox
{
    protected static final int DEFAULT_WIDTH = 100;
    protected static final int DEFAULT_HEIGHT = 40;

    private Line borderLeft;
    private Line borderRight;
    private Line borderTop;
    private Line borderBottom;


    protected TableCell(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);
    }


    public TableCell(Shape parent)
    {
        super(parent);

        setShapeType(ShapeTypes.Rectangle);

    }

    protected EscherContainerRecord createSpContainer(boolean isChild)
    {
        _escherContainer = super.createSpContainer(isChild);
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        setEscherProperty(opt, EscherProperties.TEXT__TEXTID, 0);
        setEscherProperty(opt, EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 0x20000);
        setEscherProperty(opt, EscherProperties.FILL__NOFILLHITTEST, 0x150001);
        setEscherProperty(opt, EscherProperties.SHADOWSTYLE__SHADOWOBSURED, 0x20000);
        setEscherProperty(opt, EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 0x40000);

        return _escherContainer;
    }

    protected void anchorBorder(int type, Line line)
    {
        Rectangle cellAnchor = getAnchor();
        Rectangle lineAnchor = new Rectangle();
        switch (type)
        {
            case Table.BORDER_TOP:
                lineAnchor.x = cellAnchor.x;
                lineAnchor.y = cellAnchor.y;
                lineAnchor.width = cellAnchor.width;
                lineAnchor.height = 0;
                break;
            case Table.BORDER_RIGHT:
                lineAnchor.x = cellAnchor.x + cellAnchor.width;
                lineAnchor.y = cellAnchor.y;
                lineAnchor.width = 0;
                lineAnchor.height = cellAnchor.height;
                break;
            case Table.BORDER_BOTTOM:
                lineAnchor.x = cellAnchor.x;
                lineAnchor.y = cellAnchor.y + cellAnchor.height;
                lineAnchor.width = cellAnchor.width;
                lineAnchor.height = 0;
                break;
            case Table.BORDER_LEFT:
                lineAnchor.x = cellAnchor.x;
                lineAnchor.y = cellAnchor.y;
                lineAnchor.width = 0;
                lineAnchor.height = cellAnchor.height;
                break;
            default:
                throw new IllegalArgumentException("Unknown border type: " + type);
        }
        line.setAnchor(lineAnchor);
    }

    public Line getBorderLeft()
    {
        return borderLeft;
    }

    public void setBorderLeft(Line line)
    {
        if (line != null)
            anchorBorder(Table.BORDER_LEFT, line);
        this.borderLeft = line;
    }

    public Line getBorderRight()
    {
        return borderRight;
    }

    public void setBorderRight(Line line)
    {
        if (line != null)
            anchorBorder(Table.BORDER_RIGHT, line);
        this.borderRight = line;
    }

    public Line getBorderTop()
    {
        return borderTop;
    }

    public void setBorderTop(Line line)
    {
        if (line != null)
            anchorBorder(Table.BORDER_TOP, line);
        this.borderTop = line;
    }

    public Line getBorderBottom()
    {
        return borderBottom;
    }

    public void setBorderBottom(Line line)
    {
        if (line != null)
            anchorBorder(Table.BORDER_BOTTOM, line);
        this.borderBottom = line;
    }

    public void setAnchor(Rectangle anchor)
    {
        super.setAnchor(anchor);

        if (borderTop != null)
            anchorBorder(Table.BORDER_TOP, borderTop);
        if (borderRight != null)
            anchorBorder(Table.BORDER_RIGHT, borderRight);
        if (borderBottom != null)
            anchorBorder(Table.BORDER_BOTTOM, borderBottom);
        if (borderLeft != null)
            anchorBorder(Table.BORDER_LEFT, borderLeft);
    }
}
