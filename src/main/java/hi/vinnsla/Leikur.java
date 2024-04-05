package hi.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/******************************************************************************
 *  Nafn    : Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  Vinnsluklasi fyrir leikinn. Heldur utan um stigin og bætir við/
 *  dregur frá stig ef þess þarf.
 *****************************************************************************/
public class Leikur {// stigatafla
    private final IntegerProperty stigin = new SimpleIntegerProperty();

    public IntegerProperty stiginProperty() {
        return stigin;
    }

    public int getStigin() {
        return stigin.get();
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
