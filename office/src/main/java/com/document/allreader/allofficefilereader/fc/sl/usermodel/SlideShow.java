

package com.document.allreader.allofficefilereader.fc.sl.usermodel;

import java.io.IOException;

public interface SlideShow {
	public Slide createSlide() throws IOException;
	public MasterSheet createMasterSheet() throws IOException;

	public Slide[] getSlides();
	public MasterSheet[] getMasterSheet();

	public Resources getResources();
}
