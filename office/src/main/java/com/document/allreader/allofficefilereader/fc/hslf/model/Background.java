

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;


public final class Background extends Shape
{

    protected Background(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);
    }

    protected EscherContainerRecord createSpContainer(boolean isChild)
    {
        return null;
    }


}
