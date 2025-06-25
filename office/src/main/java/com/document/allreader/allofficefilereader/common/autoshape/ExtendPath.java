
package com.document.allreader.allofficefilereader.common.autoshape;

import android.graphics.Path;

import com.document.allreader.allofficefilereader.common.bg.BackgroundAndFill;
import com.document.allreader.allofficefilereader.common.borders.Line;


public class ExtendPath
{
    public ExtendPath()
    {
        path = new Path();
        fill = null;
    }
    
    public ExtendPath(ExtendPath extendPath)
    {
        path = new Path(extendPath.getPath());
        fill = extendPath.getBackgroundAndFill();
        hasLine = extendPath.hasLine();
        line = extendPath.getLine();
        isArrow = extendPath.isArrowPath();
    }
    
    public void setPath(Path path)
    {
        this.path = path;
    }
    
    public Path getPath()
    {
        return path;
    }
    
    
    public void setBackgroundAndFill(BackgroundAndFill fill)
    {
        this.fill = fill;
    }
    
    public BackgroundAndFill getBackgroundAndFill()
    {
        return fill;
    }
    

    public boolean hasLine()
    {
        return hasLine;
    }
    

    public void setLine(boolean hasLine)
    {
        this.hasLine = hasLine;
        if(hasLine && line == null)
        {
        	line = new Line();
        }
    }

    public Line getLine()
    {
        return line;
    }
    

    public void setLine(Line line)
    {
        this.line = line;
        if(line != null)
        {
        	hasLine = true;
        }
        else
        {
        	hasLine = false;
        }
    }
    
    public void setArrowFlag(boolean isArrow)
    {
    	this.isArrow = isArrow;
    }
    
    public boolean isArrowPath()
    {
    	return isArrow;
    }
    
    public void dispose()
    {
        path = null;
        if(fill != null)
        {
            fill.dispose();
        }
    }
    
    
    private Path path;
    private BackgroundAndFill fill;
    private boolean hasLine;
    private Line  line;
    private boolean isArrow;
}
