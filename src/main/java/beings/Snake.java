package beings;

public class Snake extends EvilParty implements Cheer {
    public Snake(){
        super();
        name = "蛇精";
        blood = 1000;
        fullblood = 1000;
        attackStrength = 50;
    }
    @Override
    public void toldname(){
        System.out.print("|"+name+"  ");
    }

    public void cheer(){
        ;
    }
}
