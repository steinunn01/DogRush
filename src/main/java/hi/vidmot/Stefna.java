package hi.vidmot;

/******************************************************************************
 *  Nafn    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  enum fyrir Stefnu voffans
 *****************************************************************************/
public enum Stefna {
    UPP(90),
    NIDUR(270),
    VINSTRI(180),
    HAEGRI(360);
    private final int gradur;

    Stefna(int s) {
        gradur = s;
    }

    public int getGradur() {
        return gradur;
    }
}
