package hi.vinnsla;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/******************************************************************************
 *  Nafn    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  Vinnsluklasi fyrir leikinn.
 *  Heldur utan um tímann og niðurtalningu
 *****************************************************************************/
public class Klukka {

    private final IntegerProperty nidurtal = new SimpleIntegerProperty();
    private Timeline t;

    public Klukka(int i) {
        this.nidurtal.setValue(i);
        t =  new Timeline();
        KeyFrame k = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Action to be performed every second
                if (getNidurtal() > 0) {
                    tic(); //klukka telur nidur á sekúndufresti
                }
            }
        });
        t.getKeyFrames().add(k);
        t.setCycleCount(Timeline.INDEFINITE);
    }
    public IntegerProperty nidurtalProperty() {
        return nidurtal;
    }

    public void start() {
        t.play();
    }

    public void stop() {
        t.stop();
    }

    public void nyKlukka(int i) {
        this.nidurtal.setValue(i);
    }

    public void tic() {
        if (nidurtal.getValue() >=0) {
            this.nidurtal.setValue(nidurtal.getValue()-1);;
        }
    }

    public int getNidurtal() {
        return nidurtal.get();
    }
}
