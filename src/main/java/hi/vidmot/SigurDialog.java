package hi.vidmot;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/******************************************************************************
 *  Author    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  : Útfærir um tilkynningar dialog sem kallað er á þegar notandi vinnur
 *  leikinn.
 *****************************************************************************/
public class SigurDialog extends Alert {

    private static final String HAETTA_VID = "Hætta";
    private static final String I_LAGI = "Spila annan leik";
    public static final ButtonType BTYPE = new ButtonType(I_LAGI,
            ButtonBar.ButtonData.OK_DONE);
    public static final ButtonType HTYPE = new ButtonType(HAETTA_VID,
            ButtonBar.ButtonData.CANCEL_CLOSE);

    /**
     * Smiður sem setur inn titil og spurningu í Alert dialog
     */
    public SigurDialog() {
        super(AlertType.NONE, "Viltu spila annan leik, meistari?", BTYPE, HTYPE);
        setTitle("ÞÚ VANNST!!!");
    }
}
