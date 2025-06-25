

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.hslf.record.Comment2000;


public final class Comment
{
    private Comment2000 _comment2000;

    public Comment(Comment2000 comment2000)
    {
        _comment2000 = comment2000;
    }

    protected Comment2000 getComment2000()
    {
        return _comment2000;
    }


    public String getAuthor()
    {
        return _comment2000.getAuthor();
    }


    public void setAuthor(String author)
    {
        _comment2000.setAuthor(author);
    }

        public String getAuthorInitials()
    {
        return _comment2000.getAuthorInitials();
    }

    public void setAuthorInitials(String initials)
    {
        _comment2000.setAuthorInitials(initials);
    }

    public String getText()
    {
        return _comment2000.getText();
    }


    public void setText(String text)
    {
        _comment2000.setText(text);
    }
}
