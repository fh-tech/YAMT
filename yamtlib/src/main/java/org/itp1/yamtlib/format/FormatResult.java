package org.itp1.yamtlib.format;

import lombok.Value;
import org.itp1.yamtlib.music.YamtMusic;

import java.util.List;
import java.util.Map;

@Value
public class FormatResult {
    private List<YamtMusic> failed;
    private Map<YamtMusic, String> success;
}
