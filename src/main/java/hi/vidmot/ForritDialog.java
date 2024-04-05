package hi.vidmot;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/******************************************************************************
 *  Author    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  : Útfærir upplýsinga dialog sem kallað er á þegar notandi biður
 *  um upplýsingar um forritið
 *****************************************************************************/
public class ForritDialog extends Alert {
    private static final String titill = "DogRush!";
    private static final String haus = "Höfundar: Steinunn og Sigurjón ";
    private static final String lysing = "DogRush var forritað árið 2024. \n" +
            "DogRush er leikur sem snýst um hjálpa voffa" +
            " að safna ákveðnum fjölda stiga áður en tíminn rennur út. " +
            "Ef voffi rekst á gull fær hann eitt stig. " +
            "Ef voffi rekst á rúsínu þá missir hann stig. Ef voffi rekst á bein þá ferðast hann" +
            " tvöfalt hraðar í 10 sekúndur.\n" +
            " Hægt er að velja um þrjú erfiðleikastig. \n" +
            "Easy Edition: Safna á 30 stigum á 50 sekúndum. Rúsína veldur 3 stiga tapi. \n" +
            "Normal Edition: Safna á 26 stigum á 30 sekúndum. Rúsína veldur 2 stiga tapi. \n" +
            "Hard Edition: Safna á 18 stigum á 15 sekúndum. Rúsína veldur 1 stigs tapi.";

    private static final String I_LAGI = "Í lagi";
    public static final ButtonType BTYPE = new ButtonType(I_LAGI,
            ButtonBar.ButtonData.OK_DONE);

    /**
     * Smiður sem setur inn titil, haus og lýsingu í Alert dialog
     */
    public ForritDialog() {
        super(Alert.AlertType.NONE, lysing, BTYPE);
        setTitle(titill);
        setHeaderText(haus);
    }
}
