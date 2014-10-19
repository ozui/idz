/**
 * Created by Oksana on 12.10.2014.
 */
public class Room {
    private RoomType roomType;
    private Integer capacity;
    private Request request;

    public Room(RoomType roomType, int capacity) {
        this.roomType = roomType;
        this.capacity = capacity;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
