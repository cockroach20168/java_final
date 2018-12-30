package beings;

import MyAnnotaion.MyAnnotation;
import gui.Controller;
import javafx.animation.*;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import static java.lang.Math.sqrt;

@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public class Creature extends Being {
    Creature(){
        name = "      ";
        bloodBar = new Rectangle();
        bloodBar.setX(75*getPositionx());
        bloodBar.setY(75*getPositiony());
        bloodBar.setWidth(75);
        bloodBar.setHeight(5);
        bloodBar.setArcWidth(20);
        bloodBar.setArcHeight(20);
        bloodBar.setFill(Color.RED);
        bloodBar.setStroke(Color.GRAY);
        bloodBar.setStrokeWidth(2);
    }
    protected Boolean IsDead = false;
    protected int blood;
    protected int fullblood;
    protected int bloodOfbloodBar;
    protected int attackStrength;
    protected Rectangle bloodBar = new Rectangle();
    protected ImageView magicBall;
    public Rectangle getBloodBar(){
        return bloodBar;
    }
    public ImageView getMagicBall(){
        return magicBall;
    }
    public void setMagicBall(ImageView magicBall){
        this.magicBall = magicBall;
    }
    public int getBlood(){
        return blood;
    }
    public void setBlood(int blood){
        this.blood = blood;
    }
    public int getBloodOfbloodBar(){
        return bloodOfbloodBar;
    }
    public void setBloodOfbloodBar(int bloodOfbloodBar){
        this.bloodOfbloodBar = bloodOfbloodBar;
    }
    public int getAttackStrength(){
        return attackStrength;
    }
    public RotateTransition getVictimRotateTransition(){
        RotateTransition victimRotateTransition = new RotateTransition(Duration.millis(500), getEntity().getImageView());
        victimRotateTransition.setAxis(new Point3D(0, 0, 1));
        victimRotateTransition.setByAngle(180);
        victimRotateTransition.setCycleCount(1);
        victimRotateTransition.setAutoReverse(false);
        return victimRotateTransition;
    }
    public Transition getVictimColorAdjustTransition(){
        Transition victimColorAdjustTransition = new Transition() {
            {
                setCycleDuration(Duration.millis(200));
            }
            @Override
            protected void interpolate(double frac) {
                final ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(-0.5);
                if(frac != 0)   // frac是当前帧，这边一开始就直接亮度减半
                    getEntity().getImageView().setEffect(colorAdjust);
            }
        };
        return victimColorAdjustTransition;
    }
    public Transition getVictimBloodAdjustTransition(){
        Transition victimBloodAdjustTransition = new Transition() {
            {
                setCycleDuration(Duration.millis(200));
            }
            @Override
            protected void interpolate(double frac) {
                if(frac != 0)   // frac是当前帧，这边一开始就直接亮度减半
                    bloodBar.setWidth(75*(bloodOfbloodBar/(1.0*fullblood)));
            }
        };
        return victimBloodAdjustTransition;
    }
    public TranslateTransition getMagicBalltranslateTransition(Node magicBall, Creature victim) {
        double ratio = sqrt((victim.getPositionx() - getPositionx())*(victim.getPositionx() - getPositionx())
                +(victim.getPositiony() - getPositiony())*(victim.getPositiony() - getPositiony()))/sqrt(7*7+15*15);
        TranslateTransition magicBalltranslateTransition = new TranslateTransition(Duration.millis(3000*ratio), magicBall);
        magicBalltranslateTransition.setFromX(0);
        magicBalltranslateTransition.setToX((victim.getPositionx() - getPositionx()) * 75);
        magicBalltranslateTransition.setFromY(0);
        magicBalltranslateTransition.setToY((victim.getPositiony() - getPositiony()) * 75);
        magicBalltranslateTransition.setCycleCount(1);
        magicBalltranslateTransition.setAutoReverse(false);
        return magicBalltranslateTransition;
    }
    public ScaleTransition getMagicBallscaleTransition(Node magicBall){
        ScaleTransition magicBallscaleTransition = new ScaleTransition(Duration.millis(1000), magicBall);
        magicBallscaleTransition.setFromX(1);
        magicBallscaleTransition.setToX(2);
        magicBallscaleTransition.setFromY(1);
        magicBallscaleTransition.setToY(2);
        magicBallscaleTransition.setCycleCount(1);
        magicBallscaleTransition.setAutoReverse(false);
        return magicBallscaleTransition;
    }
    public Boolean getIsDead(){
        return IsDead;
    }

    public void setIsDead(boolean flag){
        IsDead = flag;
    }
}
