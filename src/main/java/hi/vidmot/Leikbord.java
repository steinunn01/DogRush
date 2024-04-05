package hi.vidmot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/******************************************************************************
 *  Author    : Steinunn María Bergþórsdóttir
 *
 *  Lýsing  : Aðalleikborðið. Inniheldur grafara og gull.
 *****************************************************************************/
public class Leikbord extends Pane {

    @FXML
    private Voffi fxVoffi;

    public boolean erSuper = false; //ræður hraða voffa
    private ObservableList<Gull> gullin = FXCollections.observableArrayList();
    private ObservableList<Bein> beinin = FXCollections.observableArrayList();
    private ObservableList<Rusina> rusinur = FXCollections.observableArrayList();

    public Leikbord() {
        upphafsstilla();
    }

    private void upphafsstilla() {
        FXML_Lestur.lesa(this, "leikbord-view.fxml");
    }

    /**
     * Færir voffa áfram.
     * Ef erSuper er true færist voffi hraðar
     */
    public void afram () {
        if (erSuper) {
            fxVoffi.hratt();
        } else {
            fxVoffi.afram();
        }
    }
    /**
     * Lætur boolean erSuper vera false.
     * þegar ErSuper er false færist voffi á venjulegum hraða
     */
    public void normalDog() {
        erSuper = false;
    }
    /**
     * Lætur boolean erSuper vera true.
     * þegar erSuper er true færist voffi hraðar
     */
    public void superDog() {
        erSuper = true;
    }

    /**
     * Býr til nýtt gull og setur á random stað á leikborði
     * Passar að setja ekki gull þar sem rúsína er
     */
    public void meiraGull() {
        Gull g = new Gull();
        gullin.add(g);
        g.setjaGull(this);
        for (Rusina r : rusinur) {
            while (r.areksturGull(g)) {
                getChildren().remove(g);
                gullin.remove(g);
                gullin.add(g);
                g.setjaGull(this);
            }
        }
    }

    /**
     * Býr til nýtt bein og setur á random stað á leikborði
     * Passar að setja ekki bein þar sem rúsína er
     */
    public void addBein() {
        Bein b = new Bein();
        beinin.add(b);
        b.setjaBein(this);
        for (Rusina r : rusinur) {
            while (r.areksturBein(b)) {
                getChildren().remove(b);
                beinin.remove(b);
                beinin.add(b);
                b.setjaBein(this);
            }
        }
    }

    /**
     * Býr til rúsínu hlut og setur á random stað á leikborði
     * Passar að setja ekki rúsínu þar sem gull eða voffi eru.
     */
    public void addRusina() {
        Rusina r = new Rusina();
        rusinur.add(r);
        r.setjaRusina(this);
        while (fxVoffi.erRusina(r)) {
            getChildren().remove(r);
            rusinur.remove(r);
            rusinur.add(r);
            r.setjaRusina(this);
        }
        for (Gull g : gullin) {
            while (r.areksturGull(g)) {
                getChildren().remove(r);
                rusinur.remove(r);
                rusinur.add(r);
                r.setjaRusina(this);
            }
        }
    }

    /**
     * Athugar hvort voffi finnur gull
     *
     * @return true ef voffi fann gull
     */
    public boolean erGrefurGull() {
        for (Gull g : gullin) {
            if (fxVoffi.erGull(g)) {
                getChildren().remove(g);
                gullin.remove(g);
                return true;
            }
        }
        return false;
    }

    /**
     * Athugar hvort voffi finnur bein
     *
     * @return true ef voffi fann bein
     */
    public boolean erGrefurBein() {
        for (Bein b : beinin) {
            if (fxVoffi.erBein(b)) {
                getChildren().remove(b);
                beinin.remove(b);
                return true;
            }
        }
        return false;
    }

    /**
     * Athugar hvort voffi finnur rúsínu
     *
     * @return true ef voffi fann rúsínu
     */
    public boolean erGrefurRusina() {
        for (Rusina r : rusinur) {
            if (fxVoffi.erRusina(r)) {
                getChildren().remove(r);
                rusinur.remove(r);
                return true;
            }
        }
        return false;
    }

    /**
     * Nýr leikur. Gulli eytt og upphafsstillt.
     * Voffi settur á random stað
     */
    public void nyrLeikur() {
        getChildren().clear();
        gullin.clear();
        beinin.clear();
        rusinur.clear();
        erSuper = false;
        fxVoffi.setjaABord(this);
        fxVoffi.setStefna(null); // stoppa grafara fyrir næsta leik
    }

    /**
     * Getter fyrir voffa
     */
    public Voffi getVoffi() {
        return fxVoffi;
    }

    /**
     * Breytir útliti voffa
     *
     * @param i 0 = Emma, 1 = Pug, 2 = Pochita
     */
    public void breytaVoffi(int i) {
        Image image = null;
        if (i == 0) {
            image = new Image(getClass().getResource("css/emma.png").toExternalForm());
        } else if (i == 1) {
            image = new Image(getClass().getResource("css/pug.png").toExternalForm());
        } else {
            image = new Image(getClass().getResource("css/pochita.png").toExternalForm());
        }
        fxVoffi.setImage(image);
    }
}
