package sample;

import javafx.scene.control.Button;
import sample.callback.StateEvent;

public class StateControlButton extends Button implements StateEvent {
    private boolean started = false;
    private StateEvent buttonClicked;

    public StateControlButton (StateEvent buttonClicked) {
        this.buttonClicked = buttonClicked;
        onStop();
        setOnMouseClicked(e->{
            if (started)
                onStop();
            else
                onStart();
        });
    }

    @Override
    public void onStart() {
        started = true;
        setText("Stop");
        buttonClicked.onStart();
    }

    @Override
    public void onStop() {
        started = false;
        setText("Start");
        buttonClicked.onStop();
    }
}
