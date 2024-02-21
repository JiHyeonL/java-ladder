package laddergame.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("플레이어들")
public class PlayersTest {
    @Test
    @DisplayName("플레이어 이름에 중복이 있을 경우 예외가 발생한다.")
    public void nameDuplicateException() {
        //given
        final String name = "name";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> Players.from(List.of(name, name)));

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  "})
    @DisplayName("입력이 없을 경우 예외가 발생한다.")
    public void nameIsNullException(final String name) {
        //given & when & then
        assertThrows(IllegalArgumentException.class, () -> Players.from(List.of(name)));
    }
}
