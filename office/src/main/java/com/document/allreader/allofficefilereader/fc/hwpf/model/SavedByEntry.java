

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.util.Internal;


@ Internal
public final class SavedByEntry
{
    private String userName;
    private String saveLocation;

    public SavedByEntry(String userName, String saveLocation)
    {
        this.userName = userName;
        this.saveLocation = saveLocation;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getSaveLocation()
    {
        return saveLocation;
    }


    public boolean equals(Object other)
    {
        if (other == this)
            return true;
        if (!(other instanceof SavedByEntry))
            return false;
        SavedByEntry that = (SavedByEntry)other;
        return that.userName.equals(userName) && that.saveLocation.equals(saveLocation);
    }


    public int hashCode()
    {
        int hash = 29;
        hash = hash * 13 + userName.hashCode();
        hash = hash * 13 + saveLocation.hashCode();
        return hash;
    }


    public String toString()
    {
        return "SavedByEntry[userName=" + getUserName() + ",saveLocation=" + getSaveLocation()
            + "]";
    }
}
