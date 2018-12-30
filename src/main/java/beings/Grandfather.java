package beings;

import MyAnnotaion.MyAnnotation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public class Grandfather extends JustParty implements Cheer {
    public Grandfather(){
        super();
        this.name = "爷爷";
        blood = 400;
        fullblood = 400;
        bloodOfbloodBar = 400;
        attackStrength = 40;
        //magicBall = new ImageView(new Image("/whitefireball.png"));
    }
    @Override
    public void toldname(){
        System.out.print("|"+name+"  ");
    }

    public void cheer(){
        ;
    }
}
