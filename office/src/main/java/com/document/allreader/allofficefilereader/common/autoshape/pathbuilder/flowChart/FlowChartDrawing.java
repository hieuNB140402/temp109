
package com.document.allreader.allofficefilereader.common.autoshape.pathbuilder.flowChart;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.document.allreader.allofficefilereader.common.autoshape.AutoShapeKit;
import com.document.allreader.allofficefilereader.common.shape.AutoShape;
import com.document.allreader.allofficefilereader.common.shape.ShapeTypes;
import com.document.allreader.allofficefilereader.system.IControl;

public class FlowChartDrawing
{
    private static Rect flowRect = new Rect();
    
    private static RectF rectF = new RectF();
    
    private static Path path = new Path();
    

    private static final FlowChartDrawing kit = new FlowChartDrawing();


    public static FlowChartDrawing instance()
    {
        return kit;
    }
    

    public void drawFlowChart(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        int type = shape.getShapeType();
        switch(type)
        {
            case ShapeTypes.FlowChartProcess:
                drawFlowChartProcess(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartAlternateProcess:
                drawFlowChartAlternateProcess(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartDecision:
                drawFlowChartDecision(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartInputOutput:
                drawFlowChartInputOutput(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartPredefinedProcess:
                drawFlowChartPredefinedProcess(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartInternalStorage:
                drawFlowChartInternalStorage(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartDocument:
                drawFlowChartDocument(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartMultidocument:
                drawFlowChartMultidocument(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartTerminator:
                drawFlowChartTerminator(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartPreparation:
                drawFlowChartPreparation(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartManualInput:
                drawFlowChartManualInput(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartManualOperation:
                drawFlowChartManualOperation(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartConnector:
                drawFlowChartConnector(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartOffpageConnector:
                drawFlowChartOffpageConnector(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartPunchedCard:
                drawFlowChartPunchedCard(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartPunchedTape:
                drawFlowChartPunchedTape(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartSummingJunction:
                drawFlowChartSummingJunction(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartOr:
                drawFlowChartOr(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartCollate:
                drawFlowChartCollate(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartSort:
                drawFlowChartSort(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartExtract:
                drawFlowChartExtract(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartMerge:
                drawFlowChartMerge(canvas, control, viewIndex, shape, rect, zoom);
                break;

            case ShapeTypes.FlowChartOnlineStorage:
                drawFlowChartOnlineStorage(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartDelay:
                drawFlowChartDelay(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartMagneticTape:
                drawFlowChartMagneticTape(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartMagneticDisk:
                drawFlowChartMagneticDisk(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartMagneticDrum:
                drawFlowChartMagneticDrum(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            case ShapeTypes.FlowChartDisplay:
                drawFlowChartDisplay(canvas, control, viewIndex, shape, rect, zoom);
                break;
                
            default:
                break;
        }
    }
    

    private void drawFlowChartProcess(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        path.reset();
        path.addRect(rect.left, rect.top, rect.right, rect.bottom, Path.Direction.CW);
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartAlternateProcess(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = Math.min(rect.width(), rect.height()) * 0.18f;
        
        path.reset();
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        path.addRoundRect(rectF, new float[]{x, x, x, x, x, x, x, x}, Path.Direction.CW);
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartDecision(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {   
        path.reset();
        path.moveTo(rect.exactCenterX(), rect.top);
        path.lineTo(rect.right, rect.exactCenterY());
        path.lineTo(rect.exactCenterX(), rect.bottom);
        path.lineTo(rect.left, rect.exactCenterY());
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }

    

    private void drawFlowChartInputOutput(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.2f;
        
        path.reset();
        path.moveTo(rect.left + x, rect.top);
        path.lineTo(rect.right, rect.top);
        path.lineTo(rect.right - x, rect.bottom);
        path.lineTo(rect.left, rect.bottom);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartPredefinedProcess(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.125f;
        
        path.reset();
        path.addRect(rect.left, rect.top, rect.right, rect.bottom, Path.Direction.CW);
        
        path.moveTo(rect.left + x, rect.top);
        path.lineTo(rect.left + x, rect.bottom);
        path.moveTo(rect.right - x, rect.top);
        path.lineTo(rect.right - x, rect.bottom);
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartInternalStorage(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.125f;
        float y = rect.height() * 0.125f;
        
        path.reset();
        path.addRect(rect.left, rect.top, rect.right, rect.bottom, Path.Direction.CW);
        
        path.moveTo(rect.left + x, rect.top);
        path.lineTo(rect.left + x, rect.bottom);
        path.moveTo(rect.left, rect.top + y);
        path.lineTo(rect.right, rect.top + y);
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartDocument(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.height() * 0.2f;
        float y = rect.height() * 0.07f;
        
        path.reset();
        path.moveTo(rect.left, rect.top);
        path.lineTo(rect.right, rect.top);
        rectF.set(rect.exactCenterX(), rect.bottom - x, 
            rect.right + (float)rect.width() / 2, rect.bottom + x - y * 2);
        path.arcTo(rectF, 270, -90);
        rectF.set(rect.left, rect.bottom - y * 2, rect.exactCenterX(), rect.bottom);
        path.arcTo(rectF, 0, 180);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }

    private void drawFlowChartMultidocument(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        int x = (int)(rect.width() * 0.137);
        int y = (int)(rect.height() * 0.167);
        
        flowRect.set(rect.left + x, rect.top, rect.right, rect.bottom - y);
        drawFlowChartDocument(canvas, control, viewIndex, shape, flowRect, zoom);
        
        flowRect.set(rect.left + x / 2, rect.top + y / 2, rect.right - x / 2, rect.bottom - y / 2);
        drawFlowChartDocument(canvas, control, viewIndex, shape, flowRect, zoom);
        
        flowRect.set(rect.left, rect.top + y, rect.right - x, rect.bottom);
        drawFlowChartDocument(canvas, control, viewIndex, shape, flowRect, zoom);
    }
    

    private void drawFlowChartTerminator(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.16f;
        float y = rect.height() * 0.5f;
        
        path.reset();
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        path.addRoundRect(rectF, new float[]{x, y, x, y, x, y, x, y}, Path.Direction.CW);
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartPreparation(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.2f;
        
        path.reset();
        path.moveTo(rect.left + x, rect.top);
        path.lineTo(rect.right - x, rect.top);
        path.lineTo(rect.right, rect.exactCenterY());
        path.lineTo(rect.right - x, rect.bottom);
        path.lineTo(rect.left + x, rect.bottom);
        path.lineTo(rect.left, rect.exactCenterY());
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartManualInput(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.height() * 0.2f;
        
        path.reset();
        path.moveTo(rect.left, rect.top + x);
        path.lineTo(rect.right, rect.top);
        path.lineTo(rect.right, rect.bottom);
        path.lineTo(rect.left, rect.bottom);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartManualOperation(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.2f;
        
        path.reset();
        path.moveTo(rect.left, rect.top);
        path.lineTo(rect.right, rect.top);
        path.lineTo(rect.right - x, rect.bottom);
        path.lineTo(rect.left + x, rect.bottom);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartConnector(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        path.reset();
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }

    private void drawFlowChartOffpageConnector(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.height() * 0.2f;
        
        path.reset();
        path.moveTo(rect.left, rect.top);
        path.lineTo(rect.right, rect.top);
        path.lineTo(rect.right, rect.bottom - x);
        path.lineTo(rect.exactCenterX(), rect.bottom);
        path.lineTo(rect.left, rect.bottom - x);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartPunchedCard(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.2f;
        float y = rect.height() * 0.2f;
        
        path.reset();
        path.moveTo(rect.left + x, rect.top);
        path.lineTo(rect.right, rect.top);
        path.lineTo(rect.right, rect.bottom);
        path.lineTo(rect.left, rect.bottom);
        path.lineTo(rect.left, rect.top + y);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartPunchedTape(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.height() * 0.1f;
        
        path.reset();
        rectF.set(rect.left, rect.top, rect.exactCenterX(), rect.top + x * 2);
        path.arcTo(rectF, 180, -180);
        rectF.set(rect.exactCenterX(), rect.top, rect.right, rect.top + x * 2);
        path.arcTo(rectF, 180, 180);
        rectF.set(rect.exactCenterX(), rect.bottom - x * 2, rect.right, rect.bottom);
        path.arcTo(rectF, 0, -180);
        rectF.set(rect.left, rect.bottom - x * 2, rect.exactCenterX(), rect.bottom);
        path.arcTo(rectF, 0, 180);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartSummingJunction(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = (float)Math.sqrt(2) * rect.width() / 4;
        float y = (float)Math.sqrt(2) * rect.height() / 4;
        float x0 = rect.exactCenterX();
        float y0 = rect.exactCenterY();
        
        path.reset();
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        
        path.moveTo(x0 - x, y0 - y);
        path.lineTo(x0 + x, y0 + y);
        
        path.moveTo(x0 + x, y0 - y);
        path.lineTo(x0 - x, y0 + y);
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartOr(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        path.reset();
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        
        path.moveTo(rect.exactCenterX(), rect.top);
        path.lineTo(rect.exactCenterX(), rect.bottom);
        
        path.moveTo(rect.left, rect.exactCenterY());
        path.lineTo(rect.right, rect.exactCenterY());
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartCollate(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        path.reset();   
        path.moveTo(rect.left, rect.top);
        path.lineTo(rect.right, rect.top);
        path.lineTo(rect.exactCenterX(), rect.exactCenterY());
        path.lineTo(rect.right, rect.bottom);
        path.lineTo(rect.left, rect.bottom);
        path.lineTo(rect.exactCenterX(), rect.exactCenterY());
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartSort(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        path.reset();  
        path.moveTo(rect.exactCenterX(), rect.top);
        path.lineTo(rect.right, rect.exactCenterY());
        path.lineTo(rect.exactCenterX(), rect.bottom);       
        path.lineTo(rect.left, rect.exactCenterY());
        path.close();
        
        path.moveTo(rect.left, rect.exactCenterY());
        path.lineTo(rect.right, rect.exactCenterY());
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartExtract(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        path.reset();   
        path.moveTo(rect.exactCenterX(), rect.top);
        path.lineTo(rect.right, rect.bottom);
        path.lineTo(rect.left, rect.bottom);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartMerge(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        path.reset();       
        path.moveTo(rect.left, rect.top);
        path.lineTo(rect.right, rect.top);
        path.lineTo(rect.exactCenterX(), rect.bottom);       
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartOnlineStorage(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.16f;
        
        path.reset();
        rectF.set(rect.right - x, rect.top, rect.right + x, rect.bottom);
        path.arcTo(rectF, 90, 180);
        rectF.set(rect.left, rect.top, rect.left + x * 2, rect.bottom);
        path.arcTo(rectF, 270, -180);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartDelay(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        path.reset();
        path.moveTo(rect.left, rect.top);
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        path.arcTo(rectF, 270, 180);
        path.lineTo(rect.left, rect.bottom);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }

    private void drawFlowChartMagneticTape(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.15f;
        float y = rect.height() * 0.15f;
        
        path.reset();
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
        
        boolean border = shape.hasLine();
        shape.setLine(false);
        
        path.reset();
        path.moveTo(rect.exactCenterX(), rect.bottom - y);
        path.lineTo(rect.right, rect.bottom - y);
        path.lineTo(rect.right, rect.bottom);
        path.moveTo(rect.exactCenterX(), rect.bottom);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
        shape.setLine(border);
        
        path.reset();
        path.moveTo(rect.right - x, rect.bottom - y);
        path.lineTo(rect.right, rect.bottom - y);
        path.lineTo(rect.right, rect.bottom);
        path.lineTo(rect.exactCenterX(), rect.bottom);
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartMagneticDisk(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.height() * 0.32f;
        
        path.reset();
        rectF.set(rect.left, rect.top, rect.right, rect.top + x);
        path.addOval(rectF, Path.Direction.CW);
        rectF.set(rect.left, rect.bottom - x, rect.right, rect.bottom);
        path.arcTo(rectF, 0, 180);
        rectF.set(rect.left, rect.top, rect.right, rect.top + x);
        path.arcTo(rectF, 180, -180);       
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
    

    private void drawFlowChartMagneticDrum(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.34f;
        
        path.reset();
        rectF.set(rect.right - x, rect.top, rect.right, rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        path.moveTo(rect.right - x / 2, rect.bottom);
        rectF.set(rect.right - x, rect.top, rect.right, rect.bottom);
        path.arcTo(rectF, 90, 180);
        rectF.set(rect.left, rect.top, rect.left + x, rect.bottom);
        path.arcTo(rectF, 270, -180);       
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }

    private void drawFlowChartDisplay(Canvas canvas, IControl control, int viewIndex, AutoShape shape, Rect rect, float zoom)
    {
        float x = rect.width() * 0.16f;
        
        path.reset();
        path.moveTo(rect.left, rect.exactCenterY());
        path.lineTo(rect.left + x, rect.top);
        rectF.set(rect.right - x * 2, rect.top, rect.right, rect.bottom);
        path.arcTo(rectF, 270, 180);
        path.lineTo(rect.left + x, rect.bottom);
        path.close();
        
        AutoShapeKit.instance().drawShape(canvas, control, viewIndex, shape, path, rect, zoom);
    }
}
