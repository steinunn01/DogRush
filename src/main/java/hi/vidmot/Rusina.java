package hi.vidmot;

import javafx.beans.binding.Bindings;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import java.util.Random;

/******************************************************************************
 *  Author    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  Útfærir view fyrir rúsínu.
 *****************************************************************************/
public class Rusina extends ImageView {

    private static final Random random = new Random();

    public Rusina() {
        FXML_Lestur.lesa(this, "rusina-view.fxml");
        setLayoutX(getImage().getWidth() / 20);
        setLayoutY(getImage().getHeight() / 20);
        bindaVidClip();
    }

    /**
     * Binda rúsínu við clip sem afmarkar ferning
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
     * Setur rúsínu á random stað á leikborð b
     * @param b
     */
    public void setjaRusina(Leikbord b) {
        b.getChildren().remove(this);
        setX(random.nextInt((int) (b.getWidth() - getFitWidth() )));
        setY(random.nextInt((int) (b.getHeight() - getFitHeight())));
        b.getChildren().add(this);

    }

    /**
     * Athugar hvort rúsína er á sama stað og gull
     * @param g
     */
    public boolean areksturGull(Gull g) {
        if (this.getBoundsInParent().intersects(g.getBoundsInParent())) {
            return true;
        }
        return false;
    }

    /**
     * Athugar hvort rúsína er á sama stað og bein
     * @param b
     */
    public boolean areksturBein(Bein b) {
        if (this.getBoundsInParent().intersects(b.getBoundsInParent())) {
            return true;
        }
        return false;
    }
}
