package org.itp1.yamtlib.metadata.musicBrainz;

import lombok.Value;

@Value
public class AccousticFingerprint {
    private final float duration;
    private final String fingerprint;
}
