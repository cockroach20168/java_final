package beings;

import MyAnnotaion.MyAnnotation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public class CalabashBrother extends JustParty{
    private Color selfColor;
    public CalabashBrother(int i){
        super();
        selfColor = Color.values()[i];
        name = selfColor.getName();
        blood = 800;
        fullblood = 800;
        bloodOfbloodBar = 800;
        attackStrength = 120;
        /*
        switch (i){
            case 0:magicBall = new ImageView(new Image("/redfireball.png"));break;
            case 1:magicBall = new ImageView(new Image("/orangefireball.png"));break;
            case 2:magicBall = new ImageView(new Image("/yellowfireball.png"));break;
            case 3:magicBall = new ImageView(new Image("/greenfireball.png"));break;
            case 4:magicBall = new ImageView(new Image("/cyanfireball.png"));break;
            case 5:magicBall = new ImageView(new Image("/bluefireball.png"));break;
            case 6:magicBall = new ImageView(new Image("/purplefireball.png"));break;
        }
        */
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
