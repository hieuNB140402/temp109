package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.TaggedInputStream */

public abstract class TaggedInputStream extends ByteCountInputStream {
    protected ActionSet actionSet;
    private TagHeader tagHeader;
    protected TagSet tagSet;

    protected abstract ActionHeader readActionHeader() throws IOException;

    protected abstract TagHeader readTagHeader() throws IOException;

    public TaggedInputStream(InputStream inputStream, TagSet tagSet, ActionSet actionSet) {
        this(inputStream, tagSet, actionSet, false);
    }

    public TaggedInputStream(InputStream inputStream, TagSet tagSet, ActionSet actionSet, boolean z) {
        super(inputStream, z, 8);
        this.tagSet = tagSet;
        this.actionSet = actionSet;
    }

    public void addTag(Tag tag) {
        this.tagSet.addTag(tag);
    }

    public Tag readTag() throws IOException {
        TagHeader readTagHeader = readTagHeader();
        this.tagHeader = readTagHeader;
        if (readTagHeader == null) {
            return null;
        }
        int length = (int) readTagHeader.getLength();
        Tag tag = this.tagSet.get(this.tagHeader.getTag());
        pushBuffer(length);
        Tag read = tag.read(this.tagHeader.getTag(), this, length);
        byte[] popBuffer = popBuffer();
        if (popBuffer == null) {
            return read;
        }
        throw new IncompleteTagException(read, popBuffer);
    }

    public TagHeader getTagHeader() {
        return this.tagHeader;
    }

    public void addAction(Action action) {
        this.actionSet.addAction(action);
    }

    public Action readAction() throws IOException {
        ActionHeader readActionHeader = readActionHeader();
        if (readActionHeader == null) {
            return null;
        }
        int length = (int) readActionHeader.getLength();
        Action action = this.actionSet.get(readActionHeader.getAction());
        pushBuffer(length);
        Action read = action.read(readActionHeader.getAction(), this, length);
        byte[] popBuffer = popBuffer();
        if (popBuffer == null) {
            return read;
        }
        throw new IncompleteActionException(read, popBuffer);
    }
}
