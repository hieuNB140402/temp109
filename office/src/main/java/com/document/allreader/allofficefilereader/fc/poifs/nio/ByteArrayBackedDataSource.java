

package com.document.allreader.allofficefilereader.fc.poifs.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * A POIFS {@link DataSource} backed by a byte array.
 */
public class ByteArrayBackedDataSource extends DataSource {
   private byte[] buffer;
   private long size;
   
   public ByteArrayBackedDataSource(byte[] data, int size) {
      this.buffer = data;
      this.size = size;
   }
   public ByteArrayBackedDataSource(byte[] data) {
      this(data, data.length);
   }
                
   public ByteBuffer read(int length, long position) {
      if(position >= size) {
         throw new IndexOutOfBoundsException(
               "Unable to read " + length + " bytes from " +
               position + " in stream of length " + size
         );
      }
      
      int toRead = (int)Math.min(length, size - position);
      return ByteBuffer.wrap(buffer, (int)position, toRead);
   }
   
   public void write(ByteBuffer src, long position) {
      // Extend if needed
      long endPosition = position + src.capacity(); 
      if(endPosition > buffer.length) {
         extend(endPosition);
      }
      
      // Now copy
      src.get(buffer, (int)position, src.capacity());
      
      // Update size if needed
      if(endPosition > size) {
         size = endPosition;
      }
   }
   
   private void extend(long length) {
      // Consider extending by a bit more than requested
      long difference = length - buffer.length;
      if(difference < buffer.length*0.25) {
         difference = (long)(buffer.length*0.25);
      }
      if(difference < 4096) {
         difference = 4096;
      }

      byte[] nb = new byte[(int)(difference+buffer.length)];
      System.arraycopy(buffer, 0, nb, 0, (int)size);
      buffer = nb;
   }
   
   public void copyTo(OutputStream stream) throws IOException {
      stream.write(buffer, 0, (int)size);
   }
   
   public long size() {
      return size;
   }
   
   public void close() {
      buffer = null;
      size = -1;
   }
}
