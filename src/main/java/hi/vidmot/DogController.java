package hi.vidmot;

import hi.vinnsla.Klukka;
import hi.vinnsla.Leikur;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Optional;

/******************************************************************************
 * Author : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 * Lýsing : Controller klasinn fyrir GoldRush leik - notandi getur fært grafara til
 * með örvatökkum í eftirfarandi áttir: upp, niður, hægri, vinstri.
 *****************************************************************************/
public class DogController {

    // fastar
    private int bendir = 0; // hvaða erfiðleikastig er valið [0;2]
    private boolean Start = false; // athugar hvort upphafsstilla þarf viðmotshluti
    private int[] RushTimi = {50, 30, 15}; // lengd leiks
    private int[] SigurSkor = {30, 26, 18}; // fjöldi stiga til að vinna leikk

    // Býr til beinan aðgang frá KeyCode og í heiltölu. Hægt að nota til að fletta upp
    // heiltölu fyrir KeyCode
    private final HashMap<KeyCode, Stefna> map = new HashMap<>();

    // viðmóts tilviksbreytur
    @FXML
    private AnchorPane fxAnchorPane;
    @FXML
    private Button fxPasa;
    @FXML
    private Label fxErfidleikaTexti;
    @FXML
    private Label fxFjoldiStiga;
    @FXML
    private Label fxRushTimi;
    @FXML
    private Label fxStigin;
    @FXML
    private Label fxStada;
    @FXML
    private Label fxSuperDog;
    @FXML
    private Label fxTitill;
    @FXML
    private Leikbord fxLeikbord;
    @FXML
    private MenuController menuStyringController;

    private Timeline timeline;
    private Leikur leikur;
    private Klukka klukka; // klukka fyrir leik
    private Klukka superklukka; // niðurtalning þangað til voffi hættir að færast hratt

    /**
     * Frumstilla controller eftir að búið er að hlaða inn .fxml skrá
     */
    public void initialize() {
        leikur = new Leikur();
        klukka = new Klukka(RushTimi[bendir]);
        superklukka = new Klukka(0);
        fxStigin.textProperty().bind(leikur.stiginProperty().asString());
        fxStigin.setFocusTraversable(false);
        fxRushTimi.textProperty().bind(klukka.nidurtalProperty().asString());
        fxRushTimi.setFocusTraversable(false);
        fxSuperDog.setFocusTraversable(false);
        menuStyringController.setDogController(this);
    }


    /**
     * Tengir örvatakka við fall sem á að keyra í controller
     **/
    public void orvatakkar() {
        map.put(KeyCode.UP, Stefna.UPP);
        map.put(KeyCode.DOWN, Stefna.NIDUR);
        map.put(KeyCode.RIGHT, Stefna.HAEGRI);
        map.put(KeyCode.LEFT, Stefna.VINSTRI);
        fxLeikbord.getScene().addEventFilter(KeyEvent.ANY,
                event -> {
                    try {
                        if (map.get(event.getCode()) == null)
                            event.consume();
                        else
                            fxLeikbord.getVoffi().setStefna(map.get(event.getCode()));
                    } catch (NullPointerException e) {
                        event.consume();
                    }
                });
    }

    /**
     * Stillir upp nýjum leik og byrjar hann
     */
    public void nyrLeikur() {
        fxStada.setText("Leik lýkur eftir: ");
        fxFjoldiStiga.setText("/ " + SigurSkor[bendir]);
        leikur.nyrLeikur();
        fxLeikbord.nyrLeikur();
        raesaKlukka();
        startTimi();
        stopSuperDog();
        Start = true;
    }

    /**
     * Setur upp Animation fyrir leikinn og setur upp leikjalykkjuna
     */
    public void hefjaLeik() {
        timeline = new Timeline(); //búa til tímalínu fyrir voffa/hreyfingu voffa
        KeyFrame k = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!fxLeikbord.getVoffi().isStefnaNull())
                    fxLeikbord.afram(); //klukka telur nidur á sekúndufresti

                if (fxLeikbord.erGrefurGull()) {
                    fannGull();
                    framleidaGull(); // taka gull af leikbordi ef voffi rekst a það
                }
                if (fxLeikbord.erGrefurBein()) {
                    fannBein();
                }
                if (fxLeikbord.erGrefurRusina()) {
                    fannRusina();
                }
                if (Start) {
                    for (int i = 0; i < 3; i++) {
                        framleidaRusina();
                    }
                    framleidaBein();
                    for (int i = 0; i < 8; i++) {
                        framleidaGull();
                    }
                    Start = false;
                }
                if (klukka.getNidurtal() == 0) {
                    leikLokid("Æ æ, tíminn er búinn :( ");
                    klukka.stop(); // tíminn er búinn
                }
                if (superklukka.getNidurtal() <= 0 && fxLeikbord.erSuper) {
                    // superklukka er búinn
                    stopSuperDog();
                    framleidaBein();
                }
            }
        });
        timeline.getKeyFrames().add(k);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Leikur er stöðvaður.
     *
     * @param actionEvent
     */
    @FXML
    public void onPlayPause(ActionEvent actionEvent) {
        pasuTakki();
    }

    public void pasuTakki() {
        if (fxPasa.getText().equals("Start")) {
            nyrLeikur();
            fxPasa.setText("||");
        }
        else if (fxPasa.getText().equals("||")) {
            fxPasa.setText(">");
            stopTimi();
        }
        else if (fxPasa.getText().equals(">")) {
            fxPasa.setText("||");
            startTimi();
        }
    }

    /**
     * Leik er lokið.
     * Leikmaður spurður hvort hann vilji leika annan leik
     *
     * @param skilabod ástæðan fyrir að leik lauk
     */
    public void leikLokid(String skilabod) {
        stopTimi();
        fxStada.setText("Tíminn er búinn ");
        Platform.runLater(() -> synaAlert(skilabod));
    }

    /**
     * Stöðvar leik og sýnir sigur Dialog.
     */
    public void sigur() {
        stopTimi();
        Platform.runLater(() -> synaSigur());
    }

    /**
     * Spyr notanda hvort hann vilji leika annan leik. Hefur nýjan leik ef svo er.
     * Annars er leiknum hætt.
     */
    private void synaSigur() {
        Alert a = new SigurDialog();
        Optional<ButtonType> u = a.showAndWait();
        if (u.isPresent() && !u.get().getButtonData().isCancelButton())
            nyrLeikur();
        else
            System.exit(0);
    }

    /**
     * Spyr notanda hvort hann vilji leika annan leik. Hefur nýjan leik ef svo er.
     * Annars er leiknum hætt.
     *
     * @param s skilaboð
     */
    private void synaAlert(String s) {
        Alert a = new AdvorunDialog("", "DogRush", s + " Viltu spila annan leik? ");
        Optional<ButtonType> u = a.showAndWait();
        if (u.isPresent() && !u.get().getButtonData().isCancelButton())
            nyrLeikur();
        else
            System.exit(0);
    }

    /**
     * Notandi vill vita meira um forritið.
     * Leikurinn ser stöðvaður á meðan.
     */
    public void umForritidHandler() {
        stopTimi();
        Platform.runLater(() -> synaForritAlert());
    }

    /**
     * Útskýrir fyirr notanda hvernig leikurinn virkar.
     * Fer aftur til baka í leikgluggann.
     */
    private void synaForritAlert() {
        Alert a = new ForritDialog();
        a.showAndWait();
    }

    /**
     * SuperDog power-up hættir.
     * Voffi hættir að hreyfast hratt.
     * Viðmótshlutir/breytur stillt á viðeigandi hátt.
     */
    public void stopSuperDog() {
        fxAnchorPane.getStyleClass().removeAll("supertitill"); // fjarlægja græna styleClass
        fxAnchorPane.getStyleClass().add("titill");
        superklukka.stop();
        fxSuperDog.textProperty().unbind();
        fxSuperDog.setText("");
        fxTitill.setText("DogRush");
        fxLeikbord.normalDog();
    }

    /**
     * SuperDog power-up virkjast.
     * Voffi hreyfist hratt.
     * Superklukka telur niður frá 10 sek.
     * Viðmótshlutir/breytur stillt á viðeigandi hátt.
     */
    public void startSuperDog() {
        superklukka.nyKlukka(10);
        fxAnchorPane.getStyleClass().removeAll("titill"); // fjarlægja græna styleClass
        fxAnchorPane.getStyleClass().add("supertitill");
        superklukka.start();
        fxSuperDog.textProperty().bind(superklukka.nidurtalProperty().asString());
        fxTitill.setText("SuperDog");
        fxLeikbord.superDog();
    }

    /**
     * Stöðvar leik og setur pásu á SuperDog niðurtalningu.
     * ef hún var í gangi fyrir.
     */
    public void startTimi() {
        fxPasa.setText("||");
        timeline.play();
        klukka.start();
        if (fxLeikbord.erSuper) {
            superklukka.start();
        }
    }

    /**
     * Byrjar leik og SuperDog niðurtalning byrjar
     * ef hún var í gangi fyrir.
     */
    public void stopTimi() {
        timeline.stop();
        klukka.stop();
        if (fxLeikbord.erSuper) {
            superklukka.stop();
        }
    }

    /**
     * Býr til klukku sem geymir lengd niðurtalningu.
     * Þegar leikurinn byrjar heldur klukkan utan um tímann og telur niður á sekúndufresti.
     */
    public void raesaKlukka() {
        klukka.nyKlukka(RushTimi[bendir]);
    }

    /**
     * hækkar stigin ef voffi finnur gull
     */
    public void fannGull() {
        leikur.haekkaStigin();
        if (leikur.erSigur(SigurSkor[bendir])) {
            sigur();
        }
    }

    /**
     * Voffi rekst á bein og SuperDog power-up virkjast.
     */
    public void fannBein() {
        startSuperDog();
    }

    /**
     * Voffi rekst á rúsínu og missir stig.
     * Ný rúsína er sett á leikborð í stað gömlu
     */
    public void fannRusina() {
        leikur.laekkaStigin(3-bendir);
        fxLeikbord.addRusina();
    }

    /**
     * Býr til gull fyrir leikborðið
     */
    public void framleidaGull() {
        fxLeikbord.meiraGull();
    }

    /**
     * Býr til bein fyrir leikborðið
     */
    public void framleidaBein() {
        fxLeikbord.addBein();
    }
    /**
     * Býr til rúsínu fyrir leikborðið
     */
    public void framleidaRusina() {
        fxLeikbord.addRusina();
    }

    /**
     * Breytir erfiðleikastigi.
     * Uppfærir notendaviðmótið svo notandi sér hvaða
     * erfiðleikastig er valið.
     * Byrjar sjálfkrafa nýjan leik ef það er nú
     * þegar leikur í gangi
     *
     * @param s strengur sem inniheldur heiti erfiðleikastigsins
     */
    public void erfidaleikaHandler(String s) {
        fxErfidleikaTexti.setText(s);
        setErfidaleikaTexti();
        //byrjum nýjan leik ef það er nú þegar leikur í gangi
        nyrLeikur();
    }

    /**
     * Breytir textum í notendaviðmótinu sem segir notenda hvaða
     * erfiðleikastig er valið og lengd niðurtalningu..
     */
    public void setErfidaleikaTexti() {
        if (fxErfidleikaTexti.getText().equals("Easy Edition")) {
            bendir = 0;
        } else if (fxErfidleikaTexti.getText().equals("Normal Edition")) {
            bendir = 1;
        } else if (fxErfidleikaTexti.getText().equals("Hard Edition")) {
            bendir = 2;
        }
    }

    /**
     * Lætur leikborð vita hvaða voffi er valin.
     *
     * @param s strengur sem inniheldur heiti voffa.
     */
    public void veljaVoffaHandler(String s) {
        if (s.equals("Emma")) {
            fxLeikbord.breytaVoffi(0);
        } else if (s.equals("Pug")) {
            fxLeikbord.breytaVoffi(1);
        } else if (s.equals("Pochita")) {
            fxLeikbord.breytaVoffi(2);
        }
    }
}
