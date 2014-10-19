import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oksana on 12.10.2014.
 */
public class Main {
    public static List<Room> createRooms(){
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(new Room(RoomType.Econom,3));
        rooms.add(new Room(RoomType.Econom,2));
        rooms.add(new Room(RoomType.Econom,1));
        rooms.add(new Room(RoomType.Standard,1));
        rooms.add(new Room(RoomType.Standard,2));
        rooms.add(new Room(RoomType.Standard,3));
        rooms.add(new Room(RoomType.Lux,3));
        rooms.add(new Room(RoomType.Lux,2));
        rooms.add(new Room(RoomType.Lux,1));
        return rooms;
    }

    public static void main(String[] args) {

        Hotel hotel = new Hotel(createRooms());
        new Thread(new Request("request1",1000,3,RoomType.Lux,hotel)).start();
        new Thread(new Request("request2",500,1,RoomType.Lux,hotel)).start();
        new Thread(new Request("request3",200,3,RoomType.Econom,hotel)).start();
        new Thread(new Request("request4",600,2,RoomType.Standard,hotel)).start();
        new Thread(new Request("request5",300,5,RoomType.Econom,hotel)).start();
        new Thread(new Request("request6",800,4,RoomType.Econom,hotel)).start();
        new Thread(new Request("request7",200,5,RoomType.Standard,hotel)).start();
        new Thread(new Request("request10",400,3,RoomType.Standard,hotel)).start();
        new Thread(new Request("request11",500,1,RoomType.Standard,hotel)).start();
        new Thread(new Request("request12",1000,3,RoomType.Lux,hotel)).start();
        new Thread(new Request("request9",600,3,RoomType.Econom,hotel)).start();
        new Thread(new Request("request13",400,4,RoomType.Econom,hotel)).start();
        new Thread(new Request("request914",300,2,RoomType.Econom,hotel)).start();

    }
}
