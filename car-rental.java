package com.carrental.notification;

import java.util.HashMap;
import java.util.Map;

public class SpeedAlertService {

    // Map to store customer-specific speed limits
    private final Map<String, Integer> customerSpeedLimits = new HashMap<>();

    // Set the speed limit for a specific customer.
    public void setSpeedLimit(String customerId, int speedLimit) {
        customerSpeedLimits.put(customerId, speedLimit);
    }

    //Get the speed limit for a specific customer.**/
    public Integer getSpeedLimit(String customerId) {
        return customerSpeedLimits.get(customerId);
    }

    // Notify if a car exceeds the speed limit.
    public void checkAndNotify(String customerId, int speed) {
        Integer speedLimit = customerSpeedLimits.get(customerId);

        if (speedLimit != null && speed > speedLimit) {
            // Call Firebase or alternative communication channels here
            firebaseService.notify("Speed limit exceeded", message);
            // Alert the driver
            alertDriver(carId);
        }
    }

    //Sending an alert to the driver.
    private void alertDriver(String carId) {
		inotifyCluster();
    }
	
	//Fetch car speed value through card id and notify
	public void fetchSpeedData(Map<String, Integer> customerData) {
        for (Map.Entry<String, Integer> entry : customerData.entrySet()) {
            String customerId = entry.getKey();
            int car_id = entry.getValue();
            //API to fetch live speed value from HW sensor thorough some 3rd party lib for a car id
			int speed = getCarSpeed_CAN_protocol(car_id);
            checkAndNotify(customerId, speed);
        }
    }
	
    public static void main(String[] args) {
        SpeedAlertService service = new SpeedAlertService();

        // add customer details
        service.setSpeedLimit("customer1", 100);
		service.setSpeedLimit("customer2", 100);
		service.setSpeedLimit("customer3", 150);

        // Create a map of customer and car ID
        Map<String, Integer> customerCardDetails = new HashMap<>();
        customerCardDetails.put("customer1", 112345); 
        customerCardDetails.put("customer2", 702145);  

        // Fetch the speed value for a particular customer
        service.fetchSpeedData(customerCardDetails);
    }
}
