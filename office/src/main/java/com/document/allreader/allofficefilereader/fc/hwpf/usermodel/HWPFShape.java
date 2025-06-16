
package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.document.allreader.allofficefilereader.common.bg.BackgroundAndFill;
import com.document.allreader.allofficefilereader.common.borders.Line;
import com.document.allreader.allofficefilereader.constant.AutoShapeConstant;
import com.document.allreader.allofficefilereader.constant.MainConstant;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherChildAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherClientAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSimpleProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;
import com.document.allreader.allofficefilereader.java.awt.Color;
import com.document.allreader.allofficefilereader.java.awt.Rectangle;

import android.graphics.Path;
import android.graphics.PointF;


public abstract class HWPFShape
{

	public static final byte POSH_ABS = 0;
	public static final byte POSH_LEFT = 1;
    public static final byte POSH_CENTER = 2;
    public static final byte POSH_RIGHT = 3;

    public static final byte POSH_INSIDE = 4;

    public static final byte POSH_OUTSIDE = 5;
    

    
    public static final byte POSRELH_MARGIN = 0;
    public static final byte POSRELH_PAGE = 1;
    public static final byte POSRELH_COLUMN = 2;
    public static final byte POSRELH_CHAR = 3;

    public static final byte POSV_ABS = 0;
	public static final byte POSV_TOP = 1;
    public static final byte POSV_CENTER = 2;
    public static final byte POSV_BOTTOM = 3;

    public static final byte POSV_INSIDE = 4;

    public static final byte POSV_OUTSIDE = 5;
    

    public static final byte POSRELV_MARGIN = 0;
    public static final byte POSRELV_PAGE = 1;
    public static final byte POSRELV_TEXT = 2;
    public static final byte POSRELV_LINE = 3;
    

    public HWPFShape(EscherContainerRecord escherRecord, HWPFShape parent)
    {
        this.escherContainer = escherRecord;
        this.parent = parent;
    }
    
    public EscherContainerRecord getSpContainer()
    {
        return this.escherContainer;
    }
    
    public HWPFShape getParent()
    {
        return this.parent;
    }
    
    public int getShapeType()
    {
        return ShapeKit.getShapeType(escherContainer);
    }
    
    public Rectangle getAnchor(Rectangle parent, float zoomX, float zoomY)
    {
        Rectangle anchor = null;
        EscherSpRecord spRecord = escherContainer.getChildById(EscherSpRecord.RECORD_ID);
        if (spRecord != null)
        {
            int flags = spRecord.getFlags();
            if ((flags & EscherSpRecord.FLAG_CHILD) != 0)
            {
                EscherChildAnchorRecord rec = (EscherChildAnchorRecord)ShapeKit.getEscherChild(escherContainer,
                    EscherChildAnchorRecord.RECORD_ID);
                if (rec == null)
                {
                    EscherClientAnchorRecord clrec = (EscherClientAnchorRecord)ShapeKit.getEscherChild(
                        escherContainer, EscherClientAnchorRecord.RECORD_ID);
                    if (clrec != null)
                    {
                        anchor = new Rectangle();
                        anchor.x = (int)(clrec.getCol1() * zoomX * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                        anchor.y = (int)(clrec.getFlag() * zoomY * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                        anchor.width = (int)((clrec.getDx1() - clrec.getCol1()) * zoomX * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                        anchor.height = (int)((clrec.getRow1() - clrec.getFlag()) * zoomY * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                    }
                }
                else
                {
                    anchor = new Rectangle();
                    anchor.x = (int)(rec.getDx1() * zoomX * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                    anchor.y = (int)(rec.getDy1() * zoomY * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                    anchor.width = (int)((rec.getDx2() - rec.getDx1()) * zoomX * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                    anchor.height = (int)((rec.getDy2() - rec.getDy1()) * zoomY * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                }
            }
            else
            {
                EscherClientAnchorRecord clrec = (EscherClientAnchorRecord)ShapeKit.getEscherChild(
                    escherContainer, EscherClientAnchorRecord.RECORD_ID);
                if (clrec != null)
                {
                    anchor = new Rectangle();
                    anchor.x = (int)(clrec.getCol1() * zoomX * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                    anchor.y = (int)(clrec.getFlag() * zoomY * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                    anchor.width = (int)((clrec.getDx1() - clrec.getCol1()) * zoomX * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                    anchor.height = (int)((clrec.getRow1() - clrec.getFlag()) * zoomY * MainConstant.PIXEL_DPI / ShapeKit.EMU_PER_INCH);
                }
            }
        }
        
        if(parent != null)
        {
        	anchor.x = anchor.x - parent.x;
        	anchor.y = anchor.y - parent.y;
        }
        return anchor;
    }
    

    public boolean isHidden()
    {
        return ShapeKit.isHidden(getSpContainer());
    }
    

    public boolean hasLine()
    {
    	return ShapeKit.hasLine(getSpContainer());
    }
    

    public double getLineWidth()
    {
        return ShapeKit.getLineWidth(getSpContainer());
    }

    public Color getLineColor() {
        return ShapeKit.getLineColor(getSpContainer(), null, MainConstant.APPLICATION_TYPE_WP);
    }
    public int getLineDashing()
    {
    	return ShapeKit.getLineDashing(getSpContainer());
    }
    

    public int getFillType()
    {
        return ShapeKit.getFillType(getSpContainer());
    }
    

    public Color getForegroundColor()
    {
        return ShapeKit.getForegroundColor(getSpContainer(), null, MainConstant.APPLICATION_TYPE_WP);
    }
    

    public Color getFillbackColor()
    {
        return ShapeKit.getFillbackColor(getSpContainer(), null, MainConstant.APPLICATION_TYPE_WP);
    }
    
    public int getBackgroundPictureIdx()
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(getSpContainer(),
            EscherOptRecord.RECORD_ID);
        
        int type = ShapeKit.getFillType(escherContainer);
        if (type == BackgroundAndFill.FILL_PICTURE 
        		|| type == BackgroundAndFill.FILL_SHADE_TILE
        		|| type == BackgroundAndFill.FILL_PATTERN)
        {
            EscherSimpleProperty property = (EscherSimpleProperty)ShapeKit.getEscherProperty(opt,
                EscherProperties.FILL__PATTERNTEXTURE);
            if (property != null)
            {
                return property.getPropertyValue();
            }
        }
        return -1;
    }
    

    public int getRotation()
    {
        return ShapeKit.getRotation(getSpContainer());
    }
    

    public boolean getFlipHorizontal()
    {
        return ShapeKit.getFlipHorizontal(getSpContainer());
    }


    public boolean getFlipVertical()
    {
        return ShapeKit.getFlipVertical(getSpContainer());
    }
    
    public Float[] getAdjustmentValue()
    {
        return ShapeKit.getAdjustmentValue(getSpContainer());
    }
    
    public int getStartArrowType()
    {
        return ShapeKit.getStartArrowType(getSpContainer());
    }
    
    public int getStartArrowWidth()
    {
        return ShapeKit.getStartArrowWidth(getSpContainer());
    }
    
    public int getStartArrowLength()
    {
        return ShapeKit.getStartArrowLength(getSpContainer());
    }    
    
    public int getEndArrowType()
    {
        return ShapeKit.getEndArrowType(getSpContainer());
    }
    
    public int getEndArrowWidth()
    {
        return ShapeKit.getEndArrowWidth(getSpContainer());
    }
    
    public int getEndArrowLength()
    {
        return ShapeKit.getEndArrowLength(getSpContainer());
    }
    

    public Path[] getFreeformPath(Rectangle rect, PointF startArrowTailCenter, byte startArrowType,  PointF endArrowTailCenter, byte endArrowType)
    {
        return ShapeKit.getFreeformPath(getSpContainer(), rect, startArrowTailCenter, startArrowType, endArrowTailCenter, endArrowType);
    }
    
    public ArrowPathAndTail getStartArrowPath(Rectangle rect)
    {
        return ShapeKit.getStartArrowPathAndTail(getSpContainer(), rect);
    }
    
    public ArrowPathAndTail getEndArrowPath(Rectangle rect)
    {
        return ShapeKit.getEndArrowPathAndTail(getSpContainer(), rect);
    }
    

    public int getFillAngle()
    {
    	return ShapeKit.getFillAngle(getSpContainer());
    }

    public int getFillFocus()
    {
    	return ShapeKit.getFillFocus(getSpContainer());
    }
    
    public boolean isShaderPreset()
    {
    	return ShapeKit.isShaderPreset(getSpContainer());
    }
    
    public int[] getShaderColors()
    {
    	return ShapeKit.getShaderColors(getSpContainer());
    }    
    
    public float[] getShaderPositions()
    {
    	return ShapeKit.getShaderPositions(getSpContainer());
    }
    
    /**
     * 
     * @return
     */
    public int getRadialGradientPositionType()
    {
    	return ShapeKit.getRadialGradientPositionType(getSpContainer());
    }
    
    public Line getLine(boolean isLineShape)
    {
    	if(isLineShape || hasLine())
    	{
    		int lineWidth = (int)Math.round(getLineWidth() * MainConstant.POINT_TO_PIXEL);
            boolean dash = getLineDashing() > AutoShapeConstant.LINESTYLE_SOLID;
            com.document.allreader.allofficefilereader.java.awt.Color lineColor = getLineColor();
            if(lineColor != null)
            {            	
            	BackgroundAndFill lineFill = new BackgroundAndFill();
            	lineFill.setForegroundColor(lineColor.getRGB());
            	Line line = new Line();
                line.setBackgroundAndFill(lineFill);
                line.setLineWidth(lineWidth);
                line.setDash(dash);
                return line;
            }
    	}    	
        
        return null;
    }
    
    public int getPosition_H()
    {
    	return ShapeKit.getPosition_H(getSpContainer());
    }
    
    public int getPositionRelTo_H()
    {
    	return ShapeKit.getPositionRelTo_H(getSpContainer());
    }
    
    public int getPosition_V()
    {
    	return ShapeKit.getPosition_V(getSpContainer());
    }
    
    public int getPositionRelTo_V()
    {
    	return ShapeKit.getPositionRelTo_V(getSpContainer());
    }
    
    
    public void dispose()
    {
        parent = null;
        if (escherContainer != null)
        {
            escherContainer.dispose();
            escherContainer = null;
        }
    }
    

    protected EscherContainerRecord escherContainer;

    protected HWPFShape parent;
}
