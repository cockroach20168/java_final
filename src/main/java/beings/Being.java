package beings;

import MyAnnotaion.MyAnnotation;
import gui.MySemaphore;
import javafx.scene.image.ImageView;

@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public class Being{
    public Being(){
    }
    public void setEntity(Entity entity){
        this.entity = entity;
    }
    public Entity getEntity(){
        return entity;
    }
    public void toldname() {
        System.out.print(name);
    }
    public String getname(){
        return name;
    }
    public int getPositionx(){
        return positionx;
    }
    public int getPositiony(){
        return positiony;
    }

    public void setPosition(int positionx, int positiony) {
        this.positionx = positionx;
        this.positiony = positiony;
    }
    protected String name;
    protected int positionx = 0;
    protected int positiony = 0;
    private Entity entity;
}
