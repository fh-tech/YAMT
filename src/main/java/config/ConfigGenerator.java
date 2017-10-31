package config;


import errors.YamtException;

import java.util.function.Supplier;


/**
 * An interface to abstract over Config generation
 * Each implementor can generate a config using varying sources and methods.
 * Returns an incomplete Config which can be merged with others to make a complete Config.
 */

public interface ConfigGenerator extends Supplier<IncompleteYamtConfig> {
    /**
     * @return An incomplete Configuration from some Source
     * @throws YamtException.ConfigException when some error is encountered during Config generation.
     *                                       Should this happen the Source of the config is probably corrupted and consecutive calls should also error.
     */
    IncompleteYamtConfig generate() throws YamtException.ConfigException;

    /**
     * Stub implementation to make this interface compatible with the <code>Supplier</code> interface
     */
    @Override
    default IncompleteYamtConfig get() {
        try {

            return generate();
        } catch (YamtException.ConfigException e) {
            throw new RuntimeException(e);
        }
    }
}
