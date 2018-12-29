package gui;

import formations.Battlefield;

import java.util.concurrent.Semaphore;

public class Global {
    public static Semaphore animationEnd = new Semaphore(0);
    public static Semaphore animationStart = new Semaphore(0);
    public static Battle battle;
}
