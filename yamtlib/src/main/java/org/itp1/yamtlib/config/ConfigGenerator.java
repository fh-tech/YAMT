package org.itp1.yamtlib.config;


import org.itp1.yamtlib.errors.YamtException;


/**
 * An interface to abstract over Config generation
 * Each implementor can generate a org.itp1.config using varying sources and methods.
 * Returns an incomplete Config which can be merged with others to make a complete Config.
 */

public interface ConfigGenerator{

    /**
     * @return An incomplete Configuration from some Source
     * @throws YamtException.ConfigException when some error is encountered during Config generation.
     *                                       Should this happen the Source of the org.itp1.config is probably corrupted and consecutive calls should also error.
     */

    IncompleteYamtConfig generate() throws YamtException.ConfigException;
}
