

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.constant.MainConstant;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherBSERecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSimpleProperty;
import com.document.allreader.allofficefilereader.fc.hslf.record.Document;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.PictureData;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.SlideShow;
import com.document.allreader.allofficefilereader.java.awt.Color;


public final class Fill
{

    public Fill(Shape shape)
    {
        this.shape = shape;
    }


    public int getFillType()
    {
        return ShapeKit.getFillType(shape.getSpContainer());
    }


    public int getFillAngle()
    {
    	return ShapeKit.getFillAngle(shape.getSpContainer());
    }
    

    public int getFillFocus()
    {
    	return ShapeKit.getFillFocus(shape.getSpContainer());
    }
    
    public boolean isShaderPreset()
    {
    	return ShapeKit.isShaderPreset(shape.getSpContainer());
    }
    
    public int[] getShaderColors()
    {
    	return ShapeKit.getShaderColors(shape.getSpContainer());
    }    
    
    public float[] getShaderPositions()
    {
    	return ShapeKit.getShaderPositions(shape.getSpContainer());
    }
    

    public int getRadialGradientPositionType()
    {
    	return ShapeKit.getRadialGradientPositionType(shape.getSpContainer());
    }
    

    public Color getForegroundColor()
    {
        return ShapeKit.getForegroundColor(shape.getSpContainer(), shape.getSheet(), MainConstant.APPLICATION_TYPE_PPT);
    }
    

    public Color getFillbackColor()
    {
        return ShapeKit.getFillbackColor(shape.getSpContainer(), shape.getSheet(), MainConstant.APPLICATION_TYPE_PPT);
    }
    

    public PictureData getPictureData()
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(shape.getSpContainer(),
            EscherOptRecord.RECORD_ID);
        
        EscherProperty ep = ShapeKit.getEscherProperty(opt,
            EscherProperties.FILL__PATTERNTEXTURE);
        if (ep == null || !(ep instanceof EscherSimpleProperty))
        {
            return null;
        }
        
        EscherSimpleProperty p = (EscherSimpleProperty)ep;

        SlideShow ppt = shape.getSheet().getSlideShow();
        PictureData[] pict = ppt.getPictureData();
        Document doc = ppt.getDocumentRecord();

        EscherContainerRecord dggContainer = doc.getPPDrawingGroup().getDggContainer();
        EscherContainerRecord bstore = (EscherContainerRecord)ShapeKit.getEscherChild(dggContainer,
            EscherContainerRecord.BSTORE_CONTAINER);

        if (bstore != null)
        {
            java.util.List<EscherRecord> lst = bstore.getChildRecords();
            int idx = (p.getPropertyValue() & 0xFFFF);
            if (idx == 0)
            {
                //logger.log(POILogger.WARN, "no reference to picture data found ");
            }
            else
            {
                EscherBSERecord bse = (EscherBSERecord)lst.get(idx - 1);
                for (int i = 0; i < pict.length; i++)
                {
                    if (pict[i].getOffset() == bse.getOffset())
                    {
                        return pict[i];
                    }
                }
            }
        }
        return null;
    }

    public void setFillType(int type)
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(shape.getSpContainer(),
            EscherOptRecord.RECORD_ID);
        Shape.setEscherProperty(opt, EscherProperties.FILL__FILLTYPE, type);
    }


    public void setForegroundColor(Color color)
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(shape.getSpContainer(),
            EscherOptRecord.RECORD_ID);
        if (color == null)
        {
            Shape.setEscherProperty(opt, EscherProperties.FILL__NOFILLHITTEST, 0x150000);
        }
        else
        {
            int rgb = new Color(color.getBlue(), color.getGreen(), color.getRed(), 0).getRGB();
            Shape.setEscherProperty(opt, EscherProperties.FILL__FILLCOLOR, rgb);
            Shape.setEscherProperty(opt, EscherProperties.FILL__NOFILLHITTEST, 0x150011);
        }
    }

    /**
     * Background color
     */
    public void setBackgroundColor(Color color)
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(shape.getSpContainer(),
            EscherOptRecord.RECORD_ID);
        if (color == null)
        {
            Shape.setEscherProperty(opt, EscherProperties.FILL__FILLBACKCOLOR, -1);
        }
        else
        {
            int rgb = new Color(color.getBlue(), color.getGreen(), color.getRed(), 0).getRGB();
            Shape.setEscherProperty(opt, EscherProperties.FILL__FILLBACKCOLOR, rgb);
        }
    }


    public void setPictureData(int idx)
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(shape.getSpContainer(),
            EscherOptRecord.RECORD_ID);
        Shape.setEscherProperty(opt, (short)(EscherProperties.FILL__PATTERNTEXTURE + 0x4000), idx);
    }
    

    public void dispose()
    {
        shape = null;
    }
    

    protected Shape shape;
}
