

package com.document.allreader.allofficefilereader.fc.util;
/**
 * Implementors of this interface allow client code to 'delay' writing to a certain section of a
 * data output stream.<br/>
 * A typical application is for writing BIFF records when the size is not known until well after
 * the header has been written.  The client code can call {@link #createDelayedOutput(int)}
 * to reserve two bytes of the output for the 'ushort size' header field.  The delayed output can
 * be written at any stage.
 *
 * @author Josh Micich
 */
public interface DelayableLittleEndianOutput extends LittleEndianOutput {
	/**
	 * Creates an output stream intended for outputting a sequence of <tt>size</tt> bytes.
	 */
	LittleEndianOutput createDelayedOutput(int size);
}
