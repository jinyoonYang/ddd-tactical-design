package kitchenpos.products.domain.tobe;

import kitchenpos.products.infra.tobe.Profanities;

import java.util.Arrays;
import java.util.List;

public class FakeProfanities implements Profanities {
    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }


    @Override
    public boolean contains(String text) {
        return profanities.stream()
                .anyMatch(profanity -> text.contains(profanity));
    }
}
