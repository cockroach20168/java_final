package beings;

import MyAnnotaion.MyAnnotation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public class Scorpion extends EvilParty{
    public Scorpion(){
        super();
        name = "蝎子精";
        blood = 1200;
        fullblood = 1200;
        bloodOfbloodBar = 1200;
        attackStrength = 80;
        //magicBall = new ImageView(new Image("/blackfireball.png"));
    }
    @Override
    public void toldname(){
        System.out.print("|"+name);
    }
}