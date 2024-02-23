package laddergame;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import laddergame.domain.Height;
import laddergame.domain.Ladder;
import laddergame.domain.Players;
import laddergame.domain.strategy.LineBuildStrategy;
import laddergame.domain.strategy.RandomBuildStrategy;
import laddergame.dto.LineBuildResult;
import laddergame.view.InputView;
import laddergame.view.OutputView;

public class LadderGame {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        Players players = requestUntilValidated(() -> Players.from(inputView.readPlayersName()));
        Height height = requestUntilValidated(() -> new Height(inputView.readLadderHeight()));

        Ladder ladder = new Ladder(players.getPlayers().size(), height);
        ladder.build(getLineBuildResults(players, height));
        printLadderResult(players, ladder);
    }

    private static List<LineBuildResult> getLineBuildResults(final Players players, final Height height) {
        LineBuildStrategy randomBuildStrategy = new RandomBuildStrategy();
        return IntStream.range(0, height.getHeight())
                .mapToObj(i -> randomBuildStrategy.apply(players.getPlayers().size() - 1))
                .toList();
    }

    private void printLadderResult(final Players players, final Ladder ladder) {
        outputView.writeResultTitle();
        outputView.writePlayersName(players);
        outputView.writeLadder(ladder);
    }

    private <T> T requestUntilValidated(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            OutputView.writeErrorMessage("입력이 잘못되었습니다. 다시 입력해주세요.");
            return requestUntilValidated(supplier);
        }
    }
}
