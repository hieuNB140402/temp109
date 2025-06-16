

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.common.shape.ShapeTypes;
import com.document.allreader.allofficefilereader.constant.MainConstant;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherArrayProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSimpleProperty;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.java.awt.geom.Point2D;



public final class Polygon extends AutoShape
{

    protected Polygon(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);

    }


    public Polygon(Shape parent)
    {
        super(null, parent);
        _escherContainer = createSpContainer(ShapeTypes.NotPrimitive, parent instanceof ShapeGroup);
    }

    public Polygon()
    {
        this(null);
    }


    public void setPoints(float[] xPoints, float[] yPoints)
    {
        float right = findBiggest(xPoints);
        float bottom = findBiggest(yPoints);
        float left = findSmallest(xPoints);
        float top = findSmallest(yPoints);

        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__RIGHT,
            (int)((right - left) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI)));
        opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__BOTTOM,
            (int)((bottom - top) * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI)));

        for (int i = 0; i < xPoints.length; i++)
        {
            xPoints[i] += -left;
            yPoints[i] += -top;
        }

        int numpoints = xPoints.length;

        EscherArrayProperty verticesProp = new EscherArrayProperty(
            EscherProperties.GEOMETRY__VERTICES, false, new byte[0]);
        verticesProp.setNumberOfElementsInArray(numpoints + 1);
        verticesProp.setNumberOfElementsInMemory(numpoints + 1);
        verticesProp.setSizeOfElements(0xFFF0);
        for (int i = 0; i < numpoints; i++)
        {
            byte[] data = new byte[4];
            LittleEndian.putShort(data, 0, (short)(xPoints[i] * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI));
            LittleEndian.putShort(data, 2, (short)(yPoints[i] * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI));
            verticesProp.setElement(i, data);
        }
        byte[] data = new byte[4];
        LittleEndian.putShort(data, 0, (short)(xPoints[0] * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI));
        LittleEndian.putShort(data, 2, (short)(yPoints[0] * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI));
        verticesProp.setElement(numpoints, data);
        opt.addEscherProperty(verticesProp);

        EscherArrayProperty segmentsProp = new EscherArrayProperty(
            EscherProperties.GEOMETRY__SEGMENTINFO, false, null);
        segmentsProp.setSizeOfElements(0x0002);
        segmentsProp.setNumberOfElementsInArray(numpoints * 2 + 4);
        segmentsProp.setNumberOfElementsInMemory(numpoints * 2 + 4);
        segmentsProp.setElement(0, new byte[]{(byte)0x00, (byte)0x40});
        segmentsProp.setElement(1, new byte[]{(byte)0x00, (byte)0xAC});
        for (int i = 0; i < numpoints; i++)
        {
            segmentsProp.setElement(2 + i * 2, new byte[]{(byte)0x01, (byte)0x00});
            segmentsProp.setElement(3 + i * 2, new byte[]{(byte)0x00, (byte)0xAC});
        }
        segmentsProp.setElement(segmentsProp.getNumberOfElementsInArray() - 2, new byte[]{
            (byte)0x01, (byte)0x60});
        segmentsProp.setElement(segmentsProp.getNumberOfElementsInArray() - 1, new byte[]{
            (byte)0x00, (byte)0x80});
        opt.addEscherProperty(segmentsProp);

        opt.sortProperties();
    }


    public void setPoints(Point2D[] points)
    {
        float[] xpoints = new float[points.length];
        float[] ypoints = new float[points.length];
        for (int i = 0; i < points.length; i++)
        {
            xpoints[i] = (float)points[i].getX();
            ypoints[i] = (float)points[i].getY();

        }

        setPoints(xpoints, ypoints);
    }

    private float findBiggest(float[] values)
    {
        float result = Float.MIN_VALUE;
        for (int i = 0; i < values.length; i++)
        {
            if (values[i] > result)
                result = values[i];
        }
        return result;
    }

    private float findSmallest(float[] values)
    {
        float result = Float.MAX_VALUE;
        for (int i = 0; i < values.length; i++)
        {
            if (values[i] < result)
                result = values[i];
        }
        return result;
    }

}
