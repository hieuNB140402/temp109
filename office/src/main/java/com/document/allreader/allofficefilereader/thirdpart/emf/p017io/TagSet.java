package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.TagSet */

public class TagSet {
    protected Tag defaultTag = new UndefinedTag();
    protected Map tags = new HashMap();

    public void addTag(Tag tag) {
        System.out.println("addTag==========");
        int tag2 = tag.getTag();
        if (tag2 != -1) {
            this.tags.put(new Integer(tag2), tag);
        } else {
            this.defaultTag = tag;
        }
    }

    public Tag get(int i) {
        Tag tag = (Tag) this.tags.get(new Integer(i));
        return tag == null ? this.defaultTag : tag;
    }

    public boolean exists(int i) {
        return this.tags.get(new Integer(i)) != null;
    }
}
