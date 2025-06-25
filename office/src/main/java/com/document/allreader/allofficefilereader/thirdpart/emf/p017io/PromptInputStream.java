package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.PromptInputStream */

public class PromptInputStream extends RoutedInputStream {
    public PromptInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public void addPromptListener(String str, final PromptListener promptListener) {
        addRoute(str, str, new RouteListener() { // from class: com.allreader.office.allofficefilereader.thirdpart.emf.io.PromptInputStream.1
            @Override // com.allreader.office.allofficefilereader.thirdpart.emf.p017io.RouteListener
            public void routeFound(RoutedInputStream.Route route) throws IOException {
                promptListener.promptFound(route);
            }
        });
    }
}
