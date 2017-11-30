package org.itp1.yamtlib.config;


import org.itp1.yamtlib.errors.YamtException;

import java.util.Arrays;

public class ConfigFactory {

    private static IncompleteYamtConfig safeGenerate(ConfigGenerator cg) {
        try {
            return cg.generate();
        } catch (YamtException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public YamtConfig generate(ConfigGenerator... generators) throws YamtException {
        try {
            return Arrays.stream(generators)
                    .map(ConfigFactory::safeGenerate)
                    .reduce(new IncompleteYamtConfig(), IncompleteYamtConfig::merge)
                    .verify();
        } catch (RuntimeException e) {
            throw new YamtException(e);
        }
    }
}

