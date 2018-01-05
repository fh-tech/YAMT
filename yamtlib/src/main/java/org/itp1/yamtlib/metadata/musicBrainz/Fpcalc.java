package org.itp1.yamtlib.metadata.musicBrainz;

import lombok.Getter;

import java.io.File;

@Getter
public enum Fpcalc {
    WINDOWS("/fpcalc_windows_x86_64.exe"),
    LINUX("/fpcalc_linux_x86_64"),
    MAC("/fpcalc_macos_x86_64");

    private File programFile;

    Fpcalc(String program) {
        this.programFile = new File(getClass().getResource(program).getFile());
    }
}


