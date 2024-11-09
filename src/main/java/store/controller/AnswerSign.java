package store.controller;

import static store.controller.AnswerSignErrorMessages.INVALID_ANSWER_SIGN;

import java.util.Arrays;

public enum
AnswerSign {
    YES("Y", true),
    NO("N", false);

    private final String sign;
    private final boolean meaning;

    AnswerSign(String sign, boolean meaning) {
        this.sign = sign;
        this.meaning = meaning;
    }

    public static AnswerSign getBySign(String inputSign) {
        return Arrays.stream(AnswerSign.values())
                .filter(answerSign -> answerSign.sign.equals(inputSign))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ANSWER_SIGN));

    }

    public boolean meansTrue() {
        return meaning;
    }

    public boolean meansFalse() {
        return !meaning;
    }
}
