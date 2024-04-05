package hi.vidmot;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/******************************************************************************
 *  Author    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *  Lýsing  : Útfærir aðvörunar dialog sem kallað er á þegar notandi er spurður hvort
 *  halda eigi áfram
 *****************************************************************************/
public class AdvorunDialog extends Alert {
    private static final String HAETTA_VID = "Hætta við";
    private static final String I_LAGI = "Í lagi";
    public static final ButtonType BTYPE = new ButtonType(I_LAGI,
            ButtonBar.ButtonData.OK_DONE);
    public static final ButtonType HTYPE = new ButtonType(HAETTA_VID,
            ButtonBar.ButtonData.CANCEL_CLOSE);

    /**
     * Smiður sem setur inn titil, haus og spurningu í Alert dialog
     *
     * @param titill   titillinn
     * @param haus     hausinn
     * @param spurning spurning sem borin er upp
     */
    public AdvorunDialog(String titill, String haus, String spurning) {
        super(AlertType.NONE, spurning, BTYPE, HTYPE);
        setTitle(titill);
        setHeaderText(haus);
    }
}
