package org.itp1.yamtlib.metadata.musicBrainz;

import lombok.Getter;
import lombok.Value;

@Value
public class AccousticFingerprint {
    private final String duration;
    private final String fingerprint;
}
