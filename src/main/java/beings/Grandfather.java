package beings;

public class Grandfather extends JustParty implements Cheer {
    public Grandfather(){
        super();
        this.name = "爷爷";
        blood = 400;
        fullblood = 400;
        attackStrength = 40;
    }
    @Override
    public void toldname(){
        System.out.print("|"+name+"  ");
    }

    public void cheer(){
        ;
    }
}
