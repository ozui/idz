
import java.util.*;

/**
 * Created by Oksana on 12.10.2014.
 */
public class Hotel {
    private List<Request> requests;
    private List<Room> rooms;
    private int delta;
    private Room closestRoom1;
    private Room closestRoom2;

    public Hotel(List<Room> rooms) {
        requests = new ArrayList<Request>();
        this.rooms = rooms;
    }

    public synchronized boolean settle(Request request) {
        System.out.println("Trying to settle " + request.getSurname());
        List<Room> freeRooms = getFreeRooms(request);
        if (freeRooms != null) {
            System.out.println("Settle " + request);
            for (Room room : freeRooms) {
                room.setRequest(request);
                System.out.println("In room with " + room.getCapacity() + " beds.");
            }
            System.out.println("***************\n\n");
            return requests.add(request);
        } else {
            System.out.println("Can not settle " + request.getSurname() + " with type " + request.getRoomType()
                    + ". Needs: " + request.getAmount() + " beds. Available: " + countFreeRooms(request.getRoomType()));
            System.out.println("***************\n\n");
            return false;
        }
    }

    public synchronized void moveOut(Request request) {
        System.out.println("Move out " + request);
        System.out.println("Current time: " + new Date(System.currentTimeMillis()));
        for (Room room : rooms) {
            if (request.equals(room.getRequest())) {
                room.setRequest(null);
            }
        }
        System.out.println("Quantity of available beds for type " + request.getRoomType()
                + ": " + countFreeRooms(request.getRoomType()) + '\n');
        requests.remove(request);
        System.out.println();
    }

    public synchronized boolean search(String surname) {
        for (Request request : requests) {
            if (surname.equals(request.getSurname())) {
                return true;
            }
        }
        return false;
    }

    public List<Room> getFreeRooms(Request request) {
        List<Room> freeRooms = new ArrayList<Room>();
        int freeBeds = 0;
        int requestAmount = request.getAmount();
        for (Room room : rooms) {
            if (room.getRoomType().equals(request.getRoomType()) && room.getRequest() == null) {
                if (room.getCapacity() == requestAmount) {
                    return Arrays.asList(room);
                }
                freeRooms.add(room);
                freeBeds += room.getCapacity();
            }
        }
        closestRoom1 = null;
        closestRoom2 = null;
        delta = 1000;
        for (Room room1 : freeRooms) {
            for (Room room2 : freeRooms) {
                if (room1.equals(room2)) {
                    continue;
                }
                int room1Capacity = room1.getCapacity();
                int room2Capacity = room2.getCapacity();
                if (room1Capacity + room2Capacity == requestAmount) {
                    return Arrays.asList(room1, room2);
                }
                if (room1Capacity < requestAmount && room2Capacity < requestAmount) {
                    verifyDelta(room1Capacity + room2Capacity - requestAmount, room1, room2);
                } else if (room1Capacity > requestAmount) {
                    verifyDelta(room1Capacity - requestAmount, room1, null);
                } else {
                    verifyDelta(room2Capacity - requestAmount, room2, null);
                }
            }
        }
        if (closestRoom1 != null) {
            if (closestRoom2 != null) {
                return Arrays.asList(closestRoom1, closestRoom2);
            }
            return Arrays.asList(closestRoom1);
        }
        return freeBeds >= request.getAmount() ? freeRooms : null;
    }

    private void verifyDelta(int temp, Room room1, Room room2) {
        if (temp > 0 && temp < delta) {
            closestRoom1 = room1;
            closestRoom2 = room2;
            delta = temp;
        }
    }

    public int countFreeRooms(RoomType roomType) {
        int count = 0;
        for (Room room : rooms) {
            if (room.getRoomType().equals(roomType) && room.getRequest() == null) {
                count += room.getCapacity();
            }

        }
        return count;
    }


}
