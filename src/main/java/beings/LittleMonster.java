package beings;

import MyAnnotaion.MyAnnotation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public class LittleMonster extends EvilParty {
    public LittleMonster(){
        super();
        name = "小喽啰";
        blood = 200;
        fullblood = 200;
        bloodOfbloodBar = 200;
        attackStrength = 50;
        //magicBall = new ImageView(new Image("/blackfireball.png"));
    }
    @Override
    public void toldname(){
        System.out.print("|"+name);
    }
}