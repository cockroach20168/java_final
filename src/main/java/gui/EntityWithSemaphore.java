package gui;

import beings.BeingImage;
import beings.Entity;
import javafx.scene.image.ImageView;

public class EntityWithSemaphore implements Entity {
    public EntityWithSemaphore(BeingImage imageId){
        setImageView(imageId);
    }
    public EntityWithSemaphore(BeingImage imageId, MySemaphore mySemaphore){
        setImageView(imageId);
        setMySemaphore(mySemaphore);
    }
    public ImageView getImageView(){
        return imageView;
    }
    public void setImageView(BeingImage imageId){
        this.imageView = new ImageView(imageId.getImage());
        imageView.setFitHeight(74);
        imageView.setFitWidth(74);
    }
    public void setMySemaphore(MySemaphore mySemaphore){
        this.mySemaphore = mySemaphore;
    }
    public MySemaphore getMySemaphore(){
        return mySemaphore;
    }

    protected ImageView imageView;
    protected MySemaphore mySemaphore;
}
