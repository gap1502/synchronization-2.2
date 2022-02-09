import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dealership {
    private static final int BUILD_TIME = 2500;
    private static final int BUY_TIME = 1000;
    private static final int CAR_COUNT = 10;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    List<Car> cars = new ArrayList<>();
    Dealer dealer;

    {
        dealer = new Dealer(this);
    }

    public void receiveCar() {
        for (int i = 1; i <= CAR_COUNT; i++) {
            try {
                lock.lock();
                Thread.sleep(BUILD_TIME);
                cars.add(new Car());
                System.out.println(Thread.currentThread().getName() + " выпустил 1 авто");
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
            while (cars.size() == 0) {
                System.out.println("Машин нет!");
                condition.await();
            }
            Thread.sleep(BUY_TIME);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
            cars.remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
