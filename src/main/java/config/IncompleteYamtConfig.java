package config;

import com.sun.istack.internal.Nullable;
import lombok.*;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Specialized Builder for <code>{@link YamtConfig}</code>.
 * Has all optional fields to avoid null values during creation of a Config.
 * Using <code>merge</code> two {@link IncompleteYamtConfig} are merge into the caller. Fields in the caller are only overwritten if they are <code>Optional.empyty()</code>
 */
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class IncompleteYamtConfig {

    @NonNull
    private Optional<Path> musicDir = Optional.empty();

    @NonNull
    private Optional<Path> outDir = Optional.empty();

    @NonNull
    private Optional<String> format = Optional.empty();

    /**
     * Merges this instance of {@link IncompleteYamtConfig} with another one, prioritizing the caller.
     * Fields of the caller are replaced with the ones from <code>other</code> only if they are <code>Optional.emtpy()</code>
     *
     * @param other The other {@link IncompleteYamtConfig} with which this on is merged. Has less priority
     */
    @NonNull
    IncompleteYamtConfig merge(@NonNull IncompleteYamtConfig other) {
        return new IncompleteYamtConfig(
                Optional.ofNullable(musicDir.orElseGet(() -> other.musicDir.orElse(null))),
                Optional.ofNullable(outDir.orElseGet(() -> other.outDir.orElse(null))),
                Optional.ofNullable(format.orElseGet(() -> other.format.orElse(null)))
        );
    }

    /**
     * Sets musicDir to Optional.of(musicDir) if it is currently Empty
     *
     * @param musicDir Parameter to merge
     */
    void mergeMusicDir(@Nullable Path musicDir) {
        this.musicDir = Optional.ofNullable(this.musicDir.orElse(musicDir));
    }

    /**
     * Sets outDir to Optional.of(outDir) if it is currently Empty
     *
     * @param outDir Parameter to merge
     */
    void mergeOutDir(@Nullable Path outDir) {
        this.outDir = Optional.ofNullable(this.outDir.orElse(outDir));
    }

    /**
     * Sets format to Optional.of(format) if it is currently Empty
     *
     * @param format Parameter to merge
     */
    void mergeFormat(@Nullable String format) {
        this.format = Optional.ofNullable(this.format.orElse(format));
    }

    /**
     * Verifies if the current State of this Object is a valid Configuration and if it is returns a {@link YamtConfig}.
     * Usually this Object is merged with other {@link IncompleteYamtConfig} and then verified.
     *
     * @return a valid YamtConfig
     */
    @NonNull
    public YamtConfig verify() {
        if (musicDir.isPresent()
                && outDir.isPresent()
                && format.isPresent()) {

            return YamtConfig.builder()
                    .musicDir(musicDir.orElse(null))
                    .outDir(outDir.orElse(null))
                    .format(format.orElse(null))
                    .build();
            //TODO: Throw a checked Exception instead
        } else throw new NullPointerException("Unable to create complete config");
    }
}
