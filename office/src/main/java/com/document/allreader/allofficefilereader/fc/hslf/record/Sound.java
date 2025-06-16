

package com.document.allreader.allofficefilereader.fc.hslf.record;



public final class Sound extends RecordContainer
{


    protected Sound(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _children = Record.findChildRecords(source, start + 8, len - 8);
        findInterestingChildren();
    }

    private void findInterestingChildren()
    {

        if (_children[0] instanceof CString)
        {
            _name = (CString)_children[0];
        }


        if (_children[1] instanceof CString)
        {
            _type = (CString)_children[1];
        }


        for (int i = 2; i < _children.length; i++)
        {
            if (_children[i] instanceof SoundData)
            {
                _data = (SoundData)_children[i];
                break;
            }
        }

    }


    public long getRecordType()
    {
        return RecordTypes.Sound.typeID;
    }



    public String getSoundName()
    {
        return _name.getText();
    }


    public String getSoundType()
    {
        return _type.getText();
    }


    public byte[] getSoundData()
    {
        return _data == null ? null : _data.getData();
    }
    
    
    public void dispose()
    {
       super.dispose();
       _header = null;
       if (_name != null)
       {
           _name.dispose();
           _name = null;
       }
       if (_type != null)
       {
           _type.dispose();
           _type = null;
       }
       if (_data != null)
       {
           _data.dispose();
           _data = null;
       }
    }

    private byte[] _header;

    private CString _name;
    private CString _type;
    private SoundData _data;

}
