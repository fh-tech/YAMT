package org.itp1.yamtlib.metadata.musicBrainz;

import lombok.Getter;

import java.io.File;
import java.io.InputStream;

@Getter
public enum Fpcalc {
    WINDOWS("/fpcalc_windows_x86_64.exe"),
    LINUX("/fpcalc_linux_x86_64"),
    MAC("/fpcalc_macos_x86_64");

    private InputStream programFile;

    Fpcalc(String program) {
        this.programFile = getClass().getResourceAsStream(program);
    }
}


