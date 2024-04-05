package hi.vidmot;

import javafx.beans.binding.Bindings;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import java.util.Random;

/******************************************************************************
 *  Author    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  Útfærir view fyrir bein.
 *****************************************************************************/
public class Bein extends ImageView {

    private static final Random random = new Random();

    public Bein() {
        FXML_Lestur.lesa(this, "bein-view.fxml");
        setLayoutX(getImage().getWidth() / 20);
        setLayoutY(getImage().getHeight() / 20);
        bindaVidClip();
    }

    /**
     * Binda beinið við clip sem afmarkar hring
     */
    private void bindaVidClip() {
        double r = ((Rectangle) getClip()).getWidth();
        ((Rectangle) getClip()).heightProperty().bind(
                Bindings.createDoubleBinding(() -> this.yProperty().get() + r,
                        this.yProperty()));
        ((Rectangle) getClip()).widthProperty().bind(
                Bindings.createDoubleBinding(() -> this.xProperty().get() + r,
                        this.xProperty()));
    }

    /**
     * Setur beinið á random stað á leikborð b
     *
     * @param b
     */
    public void setjaBein (Leikbord b) {
        b.getChildren().remove(this);
        setX(random.nextInt((int) (b.getWidth() - getFitWidth() )));
        setY(random.nextInt((int) (b.getHeight() - getFitHeight())));
        b.getChildren().add(this);
    }
}
