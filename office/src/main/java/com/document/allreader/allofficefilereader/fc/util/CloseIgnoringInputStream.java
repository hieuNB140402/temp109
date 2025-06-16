

package com.document.allreader.allofficefilereader.fc.util;

import java.io.FilterInputStream;
import java.io.InputStream;

import com.document.allreader.allofficefilereader.fc.poifs.filesystem.POIFSFileSystem;


/**
 * A wrapper around an {@link InputStream}, which 
 *  ignores close requests made to it.
 *
 * Useful with {@link POIFSFileSystem}, where you want
 *  to control the close yourself.
 */
public class CloseIgnoringInputStream extends FilterInputStream {
   public CloseIgnoringInputStream(InputStream in) {
      super(in);
   }

   public void close() {
      // Does nothing and ignores you
      return;
   }
}
