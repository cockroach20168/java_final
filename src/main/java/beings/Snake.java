package beings;

import MyAnnotaion.MyAnnotation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public class Snake extends EvilParty implements Cheer {
    public Snake(){
        super();
        name = "蛇精";
        blood = 1000;
        bloodOfbloodBar = 1000;
        fullblood = 1000;
        attackStrength = 50;
        //magicBall = new ImageView(new Image("/blackfireball.png"));
    }
    @Override
    public void toldname(){
        System.out.print("|"+name+"  ");
    }

    public void cheer(){
        ;
    }
}
