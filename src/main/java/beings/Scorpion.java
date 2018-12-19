package beings;

public class Scorpion extends EvilParty{
    public Scorpion(){
        super();
        name = "蝎子精";
        blood = 1200;
        fullblood = 1200;
        attackStrength = 80;
    }
    @Override
    public void toldname(){
        System.out.print("|"+name);
    }
}