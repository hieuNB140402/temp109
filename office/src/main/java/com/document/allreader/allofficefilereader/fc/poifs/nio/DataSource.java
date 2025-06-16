

package com.document.allreader.allofficefilereader.fc.poifs.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Common definition of how we read and write bytes
 */
public abstract class DataSource {
   public abstract ByteBuffer read(int length, long position) throws IOException;
   public abstract void write(ByteBuffer src, long position) throws IOException;
   public abstract long size() throws IOException;
   /** Close the underlying stream */
   public abstract void close() throws IOException;
   /** Copies the contents to the specified OutputStream */
   public abstract void copyTo(OutputStream stream) throws IOException;
}
