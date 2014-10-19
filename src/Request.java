import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oksana on 12.10.2014.
 */
public class Request implements Runnable {
    private String surname;
    private long milliseconds;
    private int amount;
    private RoomType roomType;
    private Hotel hotel;

    public Request(String surname, long milliseconds, int amount, RoomType roomType, Hotel hotel) {
        this.surname = surname;
        this.milliseconds = milliseconds;
        this.amount = amount;
        this.roomType = roomType;
        this.hotel = hotel;
    }

    public String getSurname() {
        return surname;
    }


    public long getMilliseconds() {
        return milliseconds;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getAmount() {
        return amount;
    }


    @Override
    public String toString() {
        return "Request: " +
                "surname='" + surname + '\'' +
                ", ms=" + milliseconds +
                ", amount=" + amount +
                ", roomType=" + roomType +
                '.';
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (hotel.settle(this)) {
                    Thread.sleep(milliseconds);
                    hotel.moveOut(this);
                    synchronized (roomType) {
                        roomType.notify();
                    }
                    Thread.sleep(5000);
                } else {

                    synchronized (roomType) {
                        roomType.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
