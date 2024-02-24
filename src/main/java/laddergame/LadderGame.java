package laddergame;

import java.util.function.Supplier;

import laddergame.domain.Height;
import laddergame.domain.Ladder;
import laddergame.domain.Players;
import laddergame.domain.strategy.RandomNoTrueSequenceBuildStrategy;
import laddergame.view.InputView;
import laddergame.view.OutputView;

public class LadderGame {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        Players players = requestUntilValidated(() -> Players.from(inputView.readPlayersName()));
        Height height = requestUntilValidated(() -> new Height(inputView.readLadderHeight()));

        Ladder ladder = Ladder.buildOf(new RandomNoTrueSequenceBuildStrategy(), players, height);
        printLadderResult(players, ladder);
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
            OutputView.writeErrorMessage(e.getMessage());
            return requestUntilValidated(supplier);
        }
    }
}
