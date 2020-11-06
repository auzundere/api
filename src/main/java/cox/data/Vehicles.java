package cox.data;

import java.util.List;

public class Vehicles {
    private List<Integer> vehicleIds;

    public Vehicles() {
    }

    public Vehicles(List<Integer> vehicleIds) {
        this.vehicleIds = vehicleIds;
    }

    public List<Integer> getVehicleIds() {
        return vehicleIds;
    }

    public void setVehicleIds(List<Integer> vehicleIds) {
        this.vehicleIds = vehicleIds;
    }
}
