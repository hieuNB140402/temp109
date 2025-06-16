

package com.document.allreader.allofficefilereader.fc.hslf.record;


public final class AnimationInfo extends RecordContainer
{
    private byte[] _header;


    private AnimationInfoAtom animationAtom;

    protected AnimationInfo(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _children = Record.findChildRecords(source, start + 8, len - 8);
        findInterestingChildren();
    }


    private void findInterestingChildren()
    {


        if (_children[0] instanceof AnimationInfoAtom)
        {
            animationAtom = (AnimationInfoAtom)_children[0];
        }

    }



    public long getRecordType()
    {
        return RecordTypes.AnimationInfo.typeID;
    }


    public AnimationInfoAtom getAnimationInfoAtom()
    {
        return animationAtom;
    }
    
    
    public void dispose()
    {
        super.dispose();
        _header = null;
        if (animationAtom != null)
        {
            animationAtom.dispose();
            animationAtom = null;
        }
    }

}
