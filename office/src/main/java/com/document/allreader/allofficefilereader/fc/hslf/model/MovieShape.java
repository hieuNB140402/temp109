

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.hslf.record.*;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.SlideShow;



public final class MovieShape extends Picture
{
    public static final int DEFAULT_MOVIE_THUMBNAIL = -1;

    public static final int MOVIE_MPEG = 1;
    public static final int MOVIE_AVI = 2;


    public MovieShape(int movieIdx, int pictureIdx)
    {
        super(pictureIdx, null);
        setMovieIndex(movieIdx);
        setAutoPlay(true);
    }


    public MovieShape(int movieIdx, int idx, Shape parent)
    {
        super(idx, parent);
        setMovieIndex(movieIdx);
    }


    protected MovieShape(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);
    }

    protected EscherContainerRecord createSpContainer(int idx, boolean isChild)
    {
        _escherContainer = super.createSpContainer(idx, isChild);


        return _escherContainer;
    }


    public void setMovieIndex(int idx)
    {
        OEShapeAtom oe = (OEShapeAtom)getClientDataRecord(RecordTypes.OEShapeAtom.typeID);
        oe.setOptions(idx);

        AnimationInfo an = (AnimationInfo)getClientDataRecord(RecordTypes.AnimationInfo.typeID);
        if (an != null)
        {
            AnimationInfoAtom ai = an.getAnimationInfoAtom();
            ai.setDimColor(0x07000000);
            ai.setFlag(AnimationInfoAtom.Automatic, true);
            ai.setFlag(AnimationInfoAtom.Play, true);
            ai.setFlag(AnimationInfoAtom.Synchronous, true);
            ai.setOrderID(idx + 1);
        }
    }

    public void setAutoPlay(boolean flag)
    {
        AnimationInfo an = (AnimationInfo)getClientDataRecord(RecordTypes.AnimationInfo.typeID);
        if (an != null)
        {
            an.getAnimationInfoAtom().setFlag(AnimationInfoAtom.Automatic, flag);
            updateClientData();
        }
    }

    public boolean isAutoPlay()
    {
        AnimationInfo an = (AnimationInfo)getClientDataRecord(RecordTypes.AnimationInfo.typeID);
        if (an != null)
        {
            return an.getAnimationInfoAtom().getFlag(AnimationInfoAtom.Automatic);
        }
        return false;
    }

    public String getPath()
    {
        OEShapeAtom oe = (OEShapeAtom)getClientDataRecord(RecordTypes.OEShapeAtom.typeID);
        int idx = oe.getOptions();

        SlideShow ppt = getSheet().getSlideShow();
        ExObjList lst = (ExObjList)ppt.getDocumentRecord().findFirstOfType(
            RecordTypes.ExObjList.typeID);
        if (lst == null)
            return null;

        Record[] r = lst.getChildRecords();
        for (int i = 0; i < r.length; i++)
        {
            if (r[i] instanceof ExMCIMovie)
            {
                ExMCIMovie mci = (ExMCIMovie)r[i];
                ExVideoContainer exVideo = mci.getExVideo();
                int objectId = exVideo.getExMediaAtom().getObjectId();
                if (objectId == idx)
                {
                    return exVideo.getPathAtom().getText();
                }
            }

        }
        return null;
    }
}
