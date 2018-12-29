package beings;

import gui.MySemaphore;
import javafx.scene.image.ImageView;

public interface Entity {
    public ImageView getImageView();
    public void setImageView(BeingImage imageId);
    public void setMySemaphore(MySemaphore mySemaphore);
    public MySemaphore getMySemaphore();
}

