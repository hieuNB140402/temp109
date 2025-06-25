
package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;


public interface Notes
{

    int getNoteAnchorPosition( int index );



    int getNotesCount();



    int getNoteIndexByAnchorPosition( int anchorPosition );



    int getNoteTextEndOffset( int index );



    int getNoteTextStartOffset( int index );
}
