package hi.vidmot;

import javafx.beans.binding.Bindings;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import java.util.Random;

/******************************************************************************
 *  Author    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  Útfærir view fyrir voffa og er stýring fyrir voffa. Voffi færist áfram eftir
 *  stefnu.
 *****************************************************************************/
public class Voffi extends ImageView {

    private static final String FXML_SKRA = "voffi-view.fxml";
    private static final Random random = new Random();

    private Stefna stefna; // stefna boltans

    public Voffi() {
        FXML_Lestur.lesa(this, FXML_SKRA);
        setLayoutX(getImage().getWidth() /20 );
        setLayoutY(getImage().getHeight() /20 );
        bindaVidClip();
    }

    private void bindaVidClip() {
        double r = ((Rectangle) getClip()).getWidth();
        double r2 = ((Rectangle) getClip()).getHeight();
        ((Rectangle) getClip()).heightProperty().bind(
                Bindings.createDoubleBinding(() -> this.yProperty().get() + r2,
                        this.yProperty()));
        ((Rectangle) getClip()).widthProperty().bind(
                Bindings.createDoubleBinding(() -> this.xProperty().get() + r,
                        this.xProperty()));
    }

    /**
     * Voffi færist áfram. Ef komið er að jaðrinum kemstu ekki lengra.
     */
    public void afram (){
        Leikbord p = (Leikbord) this.getParent();
        double nextX = getX() + Math.cos(Math.toRadians(getStefnaGradur())) * 8;
        double nextY = getY() - Math.sin(Math.toRadians(getStefnaGradur())) * 8;
        athugaJadar(p, nextX, nextY);
    }

    /**
     * Voffi færist hratt áfram. Ef komið er að jaðrinum kemstu ekki lengra.
     */
    public void hratt() {
        Leikbord p = (Leikbord) this.getParent();
        double nextX = getX() + Math.cos(Math.toRadians(getStefnaGradur())) * 15;
        double nextY = getY() - Math.sin(Math.toRadians(getStefnaGradur())) * 15;
        athugaJadar(p, nextX, nextY);
    }

    /**
     * Athugar hvort voffi sé við jaðar leikborðsins og kemur í veg fyrir
     * að voffi fari útaf.
     */
    public void athugaJadar(Leikbord p, double nextX, double nextY) {
        if (nextX  < 0) {
            nextX = 0;
        }
        if (nextX + getFitWidth() > p.getWidth()) {
            nextX = p.getWidth() - getFitWidth();
        }
        if (nextY < 0) {
            nextY = 0;
        }
        if (nextY + getFitHeight() > p.getHeight()) {
            nextY = p.getHeight() - getFitHeight();
        }
        setX(nextX);
        setY(nextY);
    }

    /**
     * Setja voffa á leikborð b á random stað
     *
     * @param b
     */
    public void setjaABord (Leikbord b) {
        b.getChildren().remove(this);
        setX(random.nextInt((int) (b.getWidth() - getFitWidth())));
        setY(random.nextInt((int)(b.getHeight() - getFitHeight())));
        b.getChildren().add(this);
    }

    /**
     * Athugar hvort voffi finnur (skarast við) gull.
     *
     * @param g gull
     * @return satt ef voffi finnur gull annars false
     */
    public boolean erGull(Gull g) {
        return getBoundsInParent().intersects(g.getBoundsInParent());
    }

    /**
     * Athugar hvort voffi finnur (skarast við) bein.
     *
     * @param b bein
     * @return satt ef voffi finnur bein annars false
     */
    public boolean erBein(Bein b) {
        return getBoundsInParent().intersects(b.getBoundsInParent());
    }

    /**
     * Athugar hvort voffi finnur (skarast við) rúsínu.
     *
     * @param r rúsína
     * @return satt ef voffi finnur rúsínu annars false
     */
    public boolean erRusina(Rusina r) {
        return getBoundsInParent().intersects(r.getBoundsInParent());
    }

    // getterar og setterar
    public void setStefna(Stefna stefna) {
        this.stefna = stefna;
    }


    public double getStefnaGradur() {
        return stefna.getGradur();
    }
}
