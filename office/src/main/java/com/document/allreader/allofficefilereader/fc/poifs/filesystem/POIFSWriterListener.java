

        

package com.document.allreader.allofficefilereader.fc.poifs.filesystem;

/**
 * Interface POIFSWriterListener
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 * @version %I%, %G%
 */

public interface POIFSWriterListener
{

    /**
     * Process a POIFSWriterEvent that this listener had registered
     * for
     *
     * @param event the POIFSWriterEvent
     */

    public void processPOIFSWriterEvent(POIFSWriterEvent event);
}   // end public interface POIFSWriterListener

