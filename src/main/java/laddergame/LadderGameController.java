package laddergame;

import laddergame.domain.*;
import laddergame.domain.PlayersResult;
import laddergame.util.RandomRungGenerator;
import laddergame.view.InputView;
import laddergame.view.OutputView;

import java.util.function.Supplier;

public class LadderGameController {
    private static final String WANT_TO_SEE_ALL_RESULT = "all";

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        Players players = retryUntilValidated(() -> new Players(inputView.readPlayersName()));
        Height height = retryUntilValidated(() -> new Height(inputView.readLadderHeight()));

        LineBuilder lineBuilder = new LineBuilder(new RandomRungGenerator(), players.getPlayersCount() - 1);
        Ladder ladder = new Ladder(lineBuilder, height);
        PlayersResult playersResult = executeLadderGame(players, ladder);

        while (true) {
            retryUntilValidated(() -> showItemByInputName(playersResult));
        }
    }

    private PlayersResult executeLadderGame(Players players, Ladder ladder) {
        LadderGame ladderGame = retryUntilValidated(() ->
                new LadderGame(players, ladder, inputView.readLadderResult()));

        outputView.writeLadderResult(ladderGame);
        return ladderGame.climbLadder(new PlayersResult());
    }

    private void showItemByInputName(PlayersResult playersResult) {
        String name = inputView.readPlayerNameWantToSeeResult();
        if (name.equals(WANT_TO_SEE_ALL_RESULT)) {
            outputView.writeAllResultItems(playersResult.getAllResult());
            return;
        }
        ResultItem item = playersResult.findItemByName(name);
        outputView.writeResultItem(item);
    }

    private <T> T retryUntilValidated(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            OutputView.writeErrorMessage(e.getMessage());
            return retryUntilValidated(supplier);
        }
    }

    private void retryUntilValidated(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            OutputView.writeErrorMessage(e.getMessage());
            retryUntilValidated(runnable);
        }
    }
}
