package bowling;

import bowling.domain.Frame;
import bowling.domain.Frames;
import bowling.domain.NormalFrame;
import bowling.domain.Pins;
import bowling.view.InputView;
import bowling.view.ResultView;

public class BowlingApplication {
    public static void main(String args[]) {
        String player = InputView.getPlayerName();

        Frames frames = new Frames();
        Frame frame = new NormalFrame(1);
        frames.addFirst(frame);

        while (!frame.isFinish()) {
            frame.bowl(Pins.of(InputView.getFrameScore(frame.getFrameNumber())));
            frames.add(frame);
            ResultView.printFrames(player, frames);
            frame = frame.next();
        }
    }
}
