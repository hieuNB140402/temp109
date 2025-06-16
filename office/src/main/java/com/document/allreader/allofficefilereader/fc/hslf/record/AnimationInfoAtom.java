

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class AnimationInfoAtom extends RecordAtom {

    public static final int Reverse = 1;

    public static final int Automatic = 4;

    public static final int Sound = 16;

    public static final int StopSound = 64;

    public static final int Play = 256;

    public static final int Synchronous = 1024;

    public static final int Hide = 4096;

    public static final int AnimateBg = 16384;


    private byte[] _header;


    private byte[] _recdata;



    public long getRecordType() {
        return RecordTypes.AnimationInfoAtom.typeID;
    }


    public void writeOut(OutputStream out) throws IOException {
        out.write(_header);
        out.write(_recdata);
    }


    public int getDimColor(){
        return LittleEndian.getInt(_recdata, 0);
    }


    public void setDimColor(int rgb){
         LittleEndian.putInt(_recdata, 0, rgb);
    }


    public int getMask(){
        return LittleEndian.getInt(_recdata, 4);
    }



    public void setMask(int mask){
        LittleEndian.putInt(_recdata, 4, mask);
    }


    public boolean getFlag(int bit){
        return (getMask() & bit) != 0;
    }



    public void setFlag(int bit, boolean value){
        int mask = getMask();
        if(value) mask |= bit;
        else mask &= ~bit;
        setMask(mask);
    }

    public int getSoundIdRef(){
        return LittleEndian.getInt(_recdata, 8);
    }



    public void setSoundIdRef(int id){
         LittleEndian.putInt(_recdata, 8, id);
    }


    public int getDelayTime(){
        return LittleEndian.getInt(_recdata, 12);
    }


    public void setDelayTime(int id){
         LittleEndian.putInt(_recdata, 12, id);
    }


    public int getOrderID(){
        return LittleEndian.getInt(_recdata, 16);
    }

    public void setOrderID(int id){
         LittleEndian.putInt(_recdata, 16, id);
    }


    public int getSlideCount(){
        return LittleEndian.getInt(_recdata, 18);
    }


    public void setSlideCount(int id){
         LittleEndian.putInt(_recdata, 18, id);
    }

    public String toString(){
        StringBuffer buf = new StringBuffer();
        buf.append("AnimationInfoAtom\n");
        buf.append("\tDimColor: " + getDimColor() + "\n");
        int mask = getMask();
        buf.append("\tMask: " + mask + ", 0x"+Integer.toHexString(mask)+"\n");
        buf.append("\t  Reverse: " + getFlag(Reverse)+"\n");
        buf.append("\t  Automatic: " + getFlag(Automatic)+"\n");
        buf.append("\t  Sound: " + getFlag(Sound)+"\n");
        buf.append("\t  StopSound: " + getFlag(StopSound)+"\n");
        buf.append("\t  Play: " + getFlag(Play)+"\n");
        buf.append("\t  Synchronous: " + getFlag(Synchronous)+"\n");
        buf.append("\t  Hide: " + getFlag(Hide)+"\n");
        buf.append("\t  AnimateBg: " + getFlag(AnimateBg)+"\n");
        buf.append("\tSoundIdRef: " + getSoundIdRef() + "\n");
        buf.append("\tDelayTime: " + getDelayTime() + "\n");
        buf.append("\tOrderID: " + getOrderID() + "\n");
        buf.append("\tSlideCount: " + getSlideCount() + "\n");
        return buf.toString();
    }
    
    
    public void dispose()
    {
        _header = null;
        _recdata = null;
    }

}
