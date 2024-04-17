package hi.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/******************************************************************************
 *  Nafn    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  Vinnsluklasi fyrir leikinn. Heldur utan um stigin og bætir við/
 *  dregur frá stig ef þess þarf.
 *****************************************************************************/
public class Leikur {
    private final IntegerProperty stigin = new SimpleIntegerProperty(); // stig leiks

    public IntegerProperty stiginProperty() {
        return stigin;
    }

    public void haekkaStigin() {
        stigin.setValue(stigin.getValue()+1);
    }

    public void laekkaStigin(int i) {
        stigin.setValue(stigin.getValue()-i);
    }

    public void nyrLeikur() {
        stigin.setValue(0);
    }

    public boolean erSigur(int i) {
        return stigin.get() >= i;
    }
}
