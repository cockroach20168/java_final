package gui;

import MyAnnotaion.MyAnnotation;
import javafx.scene.image.Image;
@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public enum  MagicBallImage {
    BlackFireBall("/blackfireball.png"), RedOne("/redfireball.png"),
    OrangeOne("/orangefireball.png"), YellowOne("/yellowfireball.png"), GreenOne("/greenfireball.png"),
    CyanOne("/cyanfireball.png"), BlueOne("/bluefireball.png"), PurpleOne("/purplefireball.png"), Grandfather("/whitefireball.png");
    private final String imageURL;
    private final Image image;
    MagicBallImage(String imageURL) {
        this.imageURL = imageURL;
        this.image = new Image(this.getClass().getResource(imageURL).toString());
    }
    @Deprecated
    public String getImageURL(){
        return imageURL;
    }

    public Image getImage(){
        return image;
    }
}
