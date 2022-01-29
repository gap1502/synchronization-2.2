class Main {
    public static final int BUYERS = 10;

    public static void main(String[] args) {
        final Dealership dealership = new Dealership();
        for (int i = 1; i < BUYERS; i++) {
            new Thread(null, dealership::sellCar, "Покупатель " + i).start();
        }
        new Thread(null, dealership::receiveCar, "Производитель TOYTA").start();
    }
}
