
package com.document.allreader.allofficefilereader.fc.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.poifs.filesystem.DirectoryEntry;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.DocumentEntry;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.DocumentInputStream;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.Entry;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.POIFSFileSystem;


@Internal
public class POIUtils
{

    /**
     * Copies an Entry into a target POIFS directory, recursively
     */
    @Internal
    public static void copyNodeRecursively( Entry entry, DirectoryEntry target )
            throws IOException
    {
        // System.err.println("copyNodeRecursively called with "+entry.getName()+
        // ","+target.getName());
        DirectoryEntry newTarget = null;
        if ( entry.isDirectoryEntry() )
        {
            newTarget = target.createDirectory( entry.getName() );
            Iterator<Entry> entries = ( (DirectoryEntry) entry ).getEntries();

            while ( entries.hasNext() )
            {
                copyNodeRecursively( entries.next(), newTarget );
            }
        }
        else
        {
            DocumentEntry dentry = (DocumentEntry) entry;
            DocumentInputStream dstream = new DocumentInputStream( dentry );
            target.createDocument( dentry.getName(), dstream );
            dstream.close();
        }
    }

    /**
     * Copies nodes from one POIFS to the other minus the excepts
     * 
     * @param source
     *            is the source POIFS to copy from
     * @param target
     *            is the target POIFS to copy to
     * @param excepts
     *            is a list of Strings specifying what nodes NOT to copy
     */
    public static void copyNodes( DirectoryEntry sourceRoot,
            DirectoryEntry targetRoot, List<String> excepts )
            throws IOException
    {
        Iterator<Entry> entries = sourceRoot.getEntries();
        while ( entries.hasNext() )
        {
            Entry entry = entries.next();
            if ( !excepts.contains( entry.getName() ) )
            {
                copyNodeRecursively( entry, targetRoot );
            }
        }
    }

    /**
     * Copies nodes from one POIFS to the other minus the excepts
     * 
     * @param source
     *            is the source POIFS to copy from
     * @param target
     *            is the target POIFS to copy to
     * @param excepts
     *            is a list of Strings specifying what nodes NOT to copy
     */
    public static void copyNodes( POIFSFileSystem source,
            POIFSFileSystem target, List<String> excepts ) throws IOException
    {
        // System.err.println("CopyNodes called");
        copyNodes( source.getRoot(), target.getRoot(), excepts );
    }
}
