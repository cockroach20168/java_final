package beings;

import MyAnnotaion.MyAnnotation;
import gui.BeingImage;
import gui.MySemaphore;
import javafx.scene.image.ImageView;

@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public interface Entity {
    public ImageView getImageView();
    public void setImageView(BeingImage imageId);
    public void setMySemaphore(MySemaphore mySemaphore);
    public MySemaphore getMySemaphore();
}

