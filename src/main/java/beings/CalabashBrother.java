package beings;

public class CalabashBrother extends JustParty{
    private Color selfColor;
    public CalabashBrother(int i){
        super();
        selfColor = Color.values()[i];
        name = selfColor.getName();
        blood = 800;
        fullblood = 800;
        attackStrength = 120;
    }
    public String getName(){
        return selfColor.getName();
    }
    public int getRank(){
        return selfColor.ordinal();
    }
    @Override
    public void toldname(){
        System.out.print("|"+selfColor.getName()+"  " );
    }
}
