package hi.vidmot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

/******************************************************************************
 *  Author    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  Sérstakur controller fyrir MenuBar. Notandi getur hafið
 *  nýjan leik eða hætt leik. Notandi getur valið erfiðaleikastig og fengið
 *  upplýsingar um forritið
 *****************************************************************************/
public class MenuController {
    @FXML
    private DogController dogController;
    @FXML
    private ToggleGroup toggleGroup1;
    @FXML
    private ToggleGroup toggleGroup2;

    /**
     * upphafsstillir Controller
     */
    @FXML
    public void initialize() {
    }

    /**
     * Notandi biður um nýjan leik og DogController látinn vita
     * @param actionEvent
     */
    @FXML
    public void onNyrLeikur(ActionEvent actionEvent) {
        dogController.nyrLeikur();
    }

    /**
     * Notandi biður um að hætta leik og DogController látinn vita
     * @param actionEvent
     */
    @FXML
    public void onHaetta(ActionEvent actionEvent) {
        dogController.leikLokid(" ");
    }

    /**
     * Tenging sett á leikborðið
     * @param aThis
     */
    public void setDogController(DogController aThis) {
        dogController = aThis;
    }

    /**
     * Togglegroup sé um að aðeins eitt eriðleikastig er valið í einu
     * DogController látinn vita hvaða erfiðaleikastig notandi valdi
     * @param actionEvent
     */
    @FXML
    public void onErfidleikastig(ActionEvent actionEvent) {
        RadioMenuItem selectedToggle = (RadioMenuItem) toggleGroup1.getSelectedToggle();
        dogController.erfidaleikaHandler(selectedToggle.getText());
    }

    @FXML
    public void onValidVoffa(ActionEvent actionEvent) {
        RadioMenuItem selectedToggle = (RadioMenuItem) toggleGroup2.getSelectedToggle();
        dogController.veljaVoffaHandler(selectedToggle.getText());
    }

    /**
     * Notandi biður um upplýsingar um forritið og DogController látinn vita
     * @param actionEvent
     */
    @FXML
    public void onUmForritid(ActionEvent actionEvent) {
        dogController.umForritidHandler();
    }

}
