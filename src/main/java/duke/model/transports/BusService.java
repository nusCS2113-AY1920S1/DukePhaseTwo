package duke.model.transports;

import java.util.ArrayList;

/**
<<<<<<< HEAD
 * Class representing a Bus Service.
=======
 * Represents a Bus and its route.
>>>>>>> 20921c7839efb1481af98f3b5c7e0465ff5bb57e
 */
public class BusService {
    private String bus;
    private ArrayList<String> direction1;
    private ArrayList<String> direction2;

    /**
     * Create bus object.
     */
    public BusService(String bus) {
        this.bus = bus;
        this.direction1 = new ArrayList<>();
        this.direction2 = new ArrayList<>();
    }

    /**
     * get the route of bus in direction indicated.
     *
     * @param direction direction of travel
     * @return All bus stop in direction of travel
     */
    public ArrayList<String> getDirection(int direction) {
        if (direction == 1) {
            return direction1;
        } else {
            return direction2;
        }
    }

    public String getBus() {
        return bus;
    }

    /**
     * Add the bus stop code to the route which the bus would travel to.
     *
     * @param busCode Code of bus stop
     * @param direction Direction of travel to next bus stop
     */
    public void addRoute(String busCode, int direction) {
        if (direction == 1) {
            this.direction1.add(busCode);
        } else {
            this.direction2.add(busCode);
        }
    }
}

