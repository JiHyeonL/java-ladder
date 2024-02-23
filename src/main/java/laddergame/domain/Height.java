package laddergame.domain;

import java.util.regex.Pattern;

public class Height {
    private static final Pattern NUMBER_REGEX = Pattern.compile("^[\\d]*$");
    private static final String NATURAL_NUMBER_ERROR = "자연수를 입력해 주세요.";
    private final int height;

    public Height(String height) {
        validate(height);
        this.height = Integer.parseInt(height);
    }

    private void validate(final String height) {
        checkIsNumber(height);
        checkIsZero(height);
    }

    private void checkIsNumber(final String height) {
        if (NUMBER_REGEX.matcher(height).matches()) {
            throw new IllegalArgumentException(NATURAL_NUMBER_ERROR);
        }
    }

    private void checkIsZero(final String height) {
        if (Integer.parseInt(height) == 0) {
            throw new IllegalArgumentException(NATURAL_NUMBER_ERROR);
        }
    }

    public int getHeight() {
        return height;
    }
}
