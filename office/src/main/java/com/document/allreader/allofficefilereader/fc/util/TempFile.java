

package com.document.allreader.allofficefilereader.fc.util;

import java.io.File;
import java.util.Random;

/**
 * Interface for creating temporary files.  Collects them all into one directory.
 *
 * @author Glen Stampoultzis
 */
public final class TempFile {
    private static File dir;
    private static final Random rnd = new Random();

    /**
     * Creates a temporary file.  Files are collected into one directory and by default are
     * deleted on exit from the VM.  Files can be kept by defining the system property
     * <code>poi.keep.tmp.files</code>.
     * <p>
     * Don't forget to close all files or it might not be possible to delete them.
     */
    public static File createTempFile(String prefix, String suffix) {
        if (dir == null)
        {
            dir = new File(System.getProperty("java.io.tmpdir"), "poifiles");
            dir.mkdir();
            if (System.getProperty("poi.keep.tmp.files") == null)
                dir.deleteOnExit();
        }

        File newFile = new File(dir, prefix + rnd.nextInt() + suffix);
        if (System.getProperty("poi.keep.tmp.files") == null)
            newFile.deleteOnExit();
        return newFile;
    }
}
