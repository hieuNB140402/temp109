
package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import java.util.List;
import java.util.Map;

public interface Bookmarks
{

    POIBookmark getBookmark( int index ) throws IndexOutOfBoundsException;


    int getBookmarksCount();


    Map<Integer, List<POIBookmark>> getBookmarksStartedBetween(
            int startInclusive, int endExclusive );
}
