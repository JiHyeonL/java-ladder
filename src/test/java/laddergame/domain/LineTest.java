package laddergame.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import laddergame.domain.strategy.CanBuildStrategy;
import laddergame.dto.LineBuildResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("라인")
public class LineTest {
    @Test
    @DisplayName("라인을 생성하는지 테스트한다.")
    public void createLineTest() {
        //given
        final int personCount = 4;
        final int expectedSize = personCount - 1;

        //when
        Line line = new Line(personCount);

        //then
        assertEquals(line.getPoints().size(), expectedSize);
    }

    @Test
    @DisplayName("랜덤값이 true일 경우 특정 위치에서 다리를 건설한다.")
    public void buildBridgeTest() {
        //given
        final int personCount = 4;
        final int position = 1;
        Line line = new Line(personCount);

        CanBuildStrategy canBuildStrategy = new CanBuildStrategy() {
            @Override
            public LineBuildResult canBuildBridges(int count) {
                return new LineBuildResult(List.of(true, false, true));
            }
        };

        LineBuildResult isBridgeBuilt = canBuildStrategy.canBuildBridges(personCount);

        //when
        line.buildBridge(isBridgeBuilt);

        //then
        assertFalse(line.isBuilt(position));
    }

    @Test
    @DisplayName("연속으로 랜덤 결과가 true면 에러를 발생한다.")
    public void checkSequenceBuildBridge() {
        //given
        final int playerCount = 5;
        final LineBuildResult canBuild1 = new LineBuildResult(List.of(true, true, false, false));
        final LineBuildResult canBuild2 = new LineBuildResult(List.of(true, true, true, true));

        //when & then
        assertThrows(IllegalStateException.class, () -> new Line(playerCount).buildBridge(canBuild1));
        assertThrows(IllegalStateException.class, () -> new Line(playerCount).buildBridge(canBuild2));
    }

}
