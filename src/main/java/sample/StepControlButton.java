package sample;

import javafx.scene.control.Button;
import sample.callback.StepEvent;

public class StepControlButton extends Button implements StepEvent {
    private StepEvent stepEvent;
    public StepControlButton (StepEvent stepEvent) {
        this.stepEvent = stepEvent;
        setText("Next");
        setOnMouseClicked(e-> onStep());
    }
    @Override
    public void onStep() {
        stepEvent.onStep();
    }

}
