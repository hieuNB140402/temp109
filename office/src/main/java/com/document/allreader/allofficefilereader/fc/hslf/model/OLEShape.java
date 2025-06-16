

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExEmbed;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExObjList;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.ObjectData;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.SlideShow;


public final class OLEShape extends Picture
{
    protected ExEmbed _exEmbed;


    public OLEShape(int idx)
    {
        super(idx);
    }

    public OLEShape(int idx, Shape parent)
    {
        super(idx, parent);
    }

    protected OLEShape(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);
    }


    public int getObjectID()
    {
        return ShapeKit.getEscherProperty(getSpContainer(), EscherProperties.BLIP__PICTUREID);
    }


    public ObjectData getObjectData()
    {
        SlideShow ppt = getSheet().getSlideShow();
        ObjectData[] ole = ppt.getEmbeddedObjects();

        //persist reference
        int ref = getExEmbed().getExOleObjAtom().getObjStgDataRef();

        ObjectData data = null;

        for (int i = 0; i < ole.length; i++)
        {
            if (ole[i].getExOleObjStg().getPersistId() == ref)
            {
                data = ole[i];
            }
        }

        if (data == null)
        {
        }

        return data;
    }


    public ExEmbed getExEmbed()
    {
        if (_exEmbed == null)
        {
            SlideShow ppt = getSheet().getSlideShow();

            ExObjList lst = ppt.getDocumentRecord().getExObjList();
            if (lst == null)
            {
                return null;
            }

            int id = getObjectID();
            Record[] ch = lst.getChildRecords();
            for (int i = 0; i < ch.length; i++)
            {
                if (ch[i] instanceof ExEmbed)
                {
                    ExEmbed embd = (ExEmbed)ch[i];
                    if (embd.getExOleObjAtom().getObjID() == id)
                        _exEmbed = embd;
                }
            }
        }
        return _exEmbed;
    }


    public String getInstanceName()
    {
        return getExEmbed().getMenuName();
    }


    public String getFullName()
    {
        return getExEmbed().getClipboardName();
    }


    public String getProgID()
    {
        return getExEmbed().getProgId();
    }
    

    public void dispose()
    {
        super.dispose();
        if (_exEmbed != null)
        {
            _exEmbed.dispose();
            _exEmbed = null;
        }
    }
}
