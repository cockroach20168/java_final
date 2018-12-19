package beings;

public class LittleMonster extends EvilParty {
    public LittleMonster(){
        super();
        name = "小喽啰";
        blood = 200;
        fullblood = 200;
        attackStrength = 50;
    }
    @Override
    public void toldname(){
        System.out.print("|"+name);
    }
}