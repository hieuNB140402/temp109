package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.ActionSet */

public class ActionSet {
    protected Map actions = new HashMap();
    protected Action defaultAction = new Action.Unknown();

    public void addAction(Action action) {
        this.actions.put(new Integer(action.getCode()), action);
    }

    public Action get(int i) {
        Action action = (Action) this.actions.get(new Integer(i));
        return action == null ? this.defaultAction : action;
    }

    public boolean exists(int i) {
        return this.actions.get(new Integer(i)) != null;
    }
}
