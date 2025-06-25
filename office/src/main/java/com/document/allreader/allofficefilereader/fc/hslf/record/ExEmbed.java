

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.fc.util.POILogger;



public class ExEmbed extends RecordContainer
{


    protected ExEmbed(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        _children = Record.findChildRecords(source, start + 8, len - 8);
        findInterestingChildren();
    }

    public ExEmbed()
    {
        _header = new byte[8];
        _children = new Record[5];


        _header[0] = 0x0f;
        LittleEndian.putShort(_header, 2, (short)getRecordType());

        CString cs1 = new CString();
        cs1.setOptions(0x1 << 4);
        CString cs2 = new CString();
        cs2.setOptions(0x2 << 4);
        CString cs3 = new CString();
        cs3.setOptions(0x3 << 4);
        _children[0] = new ExEmbedAtom();
        _children[1] = new ExOleObjAtom();
        _children[2] = cs1;
        _children[3] = cs2;
        _children[4] = cs3;
        findInterestingChildren();
    }


    private void findInterestingChildren()
    {


        if (_children[0] instanceof ExEmbedAtom)
        {
            embedAtom = (ExEmbedAtom)_children[0];
        }
        else
        {
            logger.log(POILogger.ERROR, "First child record wasn't a ExEmbedAtom, was of type "
                + _children[0].getRecordType());
        }

        if (_children[1] instanceof ExOleObjAtom)
        {
            oleObjAtom = (ExOleObjAtom)_children[1];
        }
        else
        {
            logger.log(POILogger.ERROR, "Second child record wasn't a ExOleObjAtom, was of type "
                + _children[1].getRecordType());
        }

        for (int i = 2; i < _children.length; i++)
        {
            if (_children[i] instanceof CString)
            {
                CString cs = (CString)_children[i];
                int opts = cs.getOptions() >> 4;
                switch (opts)
                {
                    case 0x1:
                        menuName = cs;
                        break;
                    case 0x2:
                        progId = cs;
                        break;
                    case 0x3:
                        clipboardName = cs;
                        break;
                }
            }
        }
    }


    public ExEmbedAtom getExEmbedAtom()
    {
        return (ExEmbedAtom)embedAtom;
    }

    public ExOleObjAtom getExOleObjAtom()
    {
        return oleObjAtom;
    }


    public String getMenuName()
    {
        return menuName == null ? null : menuName.getText();
    }

    public void setMenuName(String s)
    {
        if (menuName != null)
            menuName.setText(s);
    }


    public String getProgId()
    {
        return progId == null ? null : progId.getText();
    }

    public void setProgId(String s)
    {
        if (progId != null)
            progId.setText(s);
    }

    public String getClipboardName()
    {
        return clipboardName == null ? null : clipboardName.getText();
    }

    public void setClipboardName(String s)
    {
        if (clipboardName != null)
            clipboardName.setText(s);
    }

    public long getRecordType()
    {
        return RecordTypes.ExEmbed.typeID;
    }
    
    
    
    public void dispose()
    {
        super.dispose();
        _header = null;
        if (embedAtom != null)
        {
            embedAtom.dispose();
            embedAtom = null;
        }
        if (oleObjAtom != null)
        {
            oleObjAtom.dispose();
            oleObjAtom = null;
        }
        if (menuName != null)
        {
            menuName.dispose();
            menuName = null;
        }
        if (progId != null)
        {
            progId.dispose();
            progId = null;
        }
        if (clipboardName != null)
        {
            clipboardName.dispose();
            clipboardName = null;
        }
    }
    

    private byte[] _header;

    protected RecordAtom embedAtom;
    private ExOleObjAtom oleObjAtom;
    private CString menuName;
    private CString progId;
    private CString clipboardName;
}
