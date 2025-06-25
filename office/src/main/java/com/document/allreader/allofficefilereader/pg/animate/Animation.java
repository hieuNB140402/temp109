/*
 * 文件名称:          AnimationManager.java
 *  
 * 编译器:            android2.2
 * 时间:              下午1:33:01
 */
package com.document.allreader.allofficefilereader.pg.animate;




public class Animation implements IAnimation
{

    public static final byte AnimStatus_NotStarted = 0;
    public static final byte AnimStatus_Animating = 1;
    public static final byte AnimStatus_End = 2;
    

    public static final byte FadeIn = 0;
    public static final byte FadeOut = 1;
    

    public static final int Duration = 1200;
    //frame per second
    private static final int FPS = 15;
    
    
    public Animation(ShapeAnimation shapeAnim)
    {
        this(shapeAnim, Duration, FPS);
    }
    
    public Animation(ShapeAnimation shapeAnim, int duration)
    {
        this(shapeAnim, duration, FPS);
    }
    
    public Animation(ShapeAnimation shapeAnim, int duration, int fps)
    {
        this.shapeAnim = shapeAnim;
        
        this.duration = duration;        
        this.fps = fps;
        delay = 1000 / fps;
        
        status = AnimStatus_NotStarted;        
    }
    

    public void start()
    {
        this.status = AnimStatus_Animating;
    }
    
    /**
     * stop animation 
     */
    public void stop()
    {
        this.status = AnimStatus_End;        
    }
    
    /**
     * get current animation information of frame when animating
     * @return
     */
    public AnimationInformation getCurrentAnimationInfor()
    {
        return  current;
    }
    
    /**
     * 
     * @return
     */
    public ShapeAnimation getShapeAnimation()
    {
        return shapeAnim;
    }
    
    /**
     * set animation duration(ms)
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }
    
    /**
     * get animation duration(ms)
     * @return
     */
    public int getDuration()
    {
        return (int)duration;
    }
    
    /**
     * 
     * @return
     */
    public int getFPS()
    {
        return fps;
    }
    
    /**
     * 定时器
     */
    public void animation(int frameIndex)
    { 
        
    }

    /**
     * 
     * @return
     */
    public byte getAnimationStatus()
    {
        return status;
    }
    
    public void dispose()
    {
        shapeAnim = null;
        
        if(begin != null)
        {
            begin.dispose();
            begin = null;
        }
        
        if(end != null)
        {
            end.dispose();
            end = null;
        }
        
        if(current != null)
        {
            current.dispose();
            current = null;
        }
    }
    
    protected ShapeAnimation shapeAnim;
    
    //duration: 2 sec
    protected float duration;    
    protected int fps;
    protected int delay;
    
    protected byte status;
    //
    protected AnimationInformation begin;
    protected AnimationInformation end;
    protected AnimationInformation current;
}
