

package com.document.allreader.allofficefilereader.fc.sl.usermodel;

public interface Slide extends Sheet {
	public Notes getNotes();
	public void setNotes(Notes notes);

	public boolean getFollowMasterBackground();
	public void setFollowMasterBackground(boolean follow);

	public boolean getFollowMasterColourScheme();
	public void setFollowMasterColourScheme(boolean follow);

	public boolean getFollowMasterObjects();
	public void setFollowMasterObjects(boolean follow);
}
