package hi.vidmot;

import javafx.scene.shape.Rectangle;
import java.util.Random;

/******************************************************************************
 *  Author    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  Útfærir view fyrir gull.
 *****************************************************************************/
public class Gull extends Rectangle {

    private static final Random random = new Random();

    public Gull() {
        FXML_Lestur.lesa(this, "gull-view.fxml");
    }

    /**
     * Setur gullið á random stað á leikborð b
     * @param b
     */
    public void setjaGull (Leikbord b) {
        b.getChildren().remove(this);
        setX(random.nextInt((int) (b.getWidth() - getWidth())));
        setY(random.nextInt((int) (b.getHeight() - getHeight())));
        b.getChildren().add(this);
    }
}
