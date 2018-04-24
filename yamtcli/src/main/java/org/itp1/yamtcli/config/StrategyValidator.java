package org.itp1.yamtcli.config;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;

public class StrategyValidator implements IValueValidator<String> {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (name.equals("--meta")) {
            if (!Arrays.asList("never", "missing", "always").contains(value)) {
                throw new ParameterException("Parameter --meta can only have 3 values: never, missing, always");
            }
        }
    }
}