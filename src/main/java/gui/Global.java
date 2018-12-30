package gui;

import MyAnnotaion.MyAnnotation;
import formations.Battlefield;

import java.util.concurrent.Semaphore;
@MyAnnotation(Author = "zmc", Date = "2018/12/29")
public class Global {
    public static Semaphore animationEnd = new Semaphore(0);
    public static Semaphore animationStart = new Semaphore(0);
    public static Battle battle;
}
