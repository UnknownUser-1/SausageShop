package sausegeShop.Fx;

import java.io.IOException;

public class NewMain {

    //Данный класс нужен для того чтобы джарник нормально запустился, так как если главный класс FXClient джарник не видит JavaFX
    private NewMain(){}

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FXClient.main(args);
    }
}
