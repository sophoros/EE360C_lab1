/*
 * Name: Jesse Zhang
 * EID: jyz225
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the Stable Marriage problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching marriage) {
        int current_location;
        int comparing_location;

        for (int i = 0; i < marriage.getEmployeeCount() - 1; i++) {
            current_location = marriage.getEmployeeMatching().get(i);
            if (current_location >= 0) {
                for (int j = i + 1; j < marriage.getEmployeeCount(); j++) {
                    comparing_location = marriage.getEmployeeMatching().get(j);
                    if (current_location != comparing_location) {
                        //Instability type 2
                        if (comparing_location >= 0) {
                            if (marriage.getLocationPreference().get(current_location).indexOf(i) > marriage.getLocationPreference().get(current_location).indexOf(j)) {
                                if (marriage.getEmployeePreference().get(j).indexOf(comparing_location) > marriage.getEmployeePreference().get(j).indexOf(current_location)) {
                                    return false;
                                }
                            }
                        }
                        //Instability type 1
                        else {
                            if (marriage.getLocationPreference().get(current_location).indexOf(i) > marriage.getLocationPreference().get(current_location).indexOf(j)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * Determines a employee optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_employeeoptimal(Matching marriage) {
        ArrayList<Integer> matching_list = new ArrayList<>();
        //contains employees that are unassigned and did not ask all locations
        Queue<Integer> available_employees  = new LinkedList<>();
        //stores the index of each preference list for each employeee
        int[] employee_preference_index = new int[marriage.getEmployeeCount()];
        //stores amount of employees assigned to a location
        int[] location_employee_count = new int[marriage.getLocationCount()];

        for (int i = 0; i < marriage.getEmployeeCount(); i++) {
            available_employees.add(i);
        }
        
        for (int i = 0; i < marriage.getEmployeeCount(); i++) {
            matching_list.add(-1);
        }

        while (!available_employees.isEmpty()) {
            int current_employee = available_employees.peek();
            int prospective_location = marriage.getEmployeePreference().get(current_employee).get(employee_preference_index[current_employee]++);

            //case: store is not full
            if (location_employee_count[prospective_location] < marriage.getLocationSlots().get(prospective_location)) {
                matching_list.set(current_employee, prospective_location);
                location_employee_count[prospective_location]++;

            }
            //case: store is full and only option is to swap
            else {
                int worst_employee = findWorstEmployee(prospective_location, marriage.getLocationPreference().get(prospective_location), matching_list);
                if (marriage.getLocationPreference().get(prospective_location).indexOf(current_employee) < marriage.getLocationPreference().get(prospective_location).indexOf(worst_employee)) {
                    matching_list.set(current_employee, prospective_location);
                    matching_list.set(worst_employee, -1);
                    if (employee_preference_index[worst_employee] < marriage.getLocationCount()) {
                        available_employees.add(worst_employee);
                    }
                }
            }
            //remove employee from queue if assigned or maxed
            if (employee_preference_index[current_employee] >= marriage.getLocationCount() || matching_list.get(current_employee) >= 0) {
                available_employees.remove();
            }
        }

        return new Matching(marriage, matching_list);
    }

    /**
     * Determines a location optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_locationoptimal(Matching marriage) {
        ArrayList<Integer> matching_list = new ArrayList<>();
        //stores locations with availible slots
        Queue<Integer> unfilled_locations = new LinkedList<>();
        //contains index of every preference list for every location
        int[] location_preference_index = new int[marriage.getLocationCount()];
        int[] location_employee_count = new int[marriage.getLocationCount()];

        for (int i = 0; i < marriage.getEmployeeCount(); i++) {
            matching_list.add(-1);
        }

        for (int i = 0; i < marriage.getLocationCount(); i++) {
            unfilled_locations.add(i);
        }

        while (!unfilled_locations.isEmpty()) {
            int current_location = unfilled_locations.peek();

            //apply for employees until full
            do {
                int prospective_employee = marriage.getLocationPreference().get(current_location).get(location_preference_index[current_location]++);
                int comparing_location = matching_list.get(prospective_employee);

                //case: employee is assigned to another store
                if (comparing_location >= 0) {
                    if (marriage.getEmployeePreference().get(prospective_employee).indexOf(current_location) < marriage.getEmployeePreference().get(prospective_employee).indexOf(comparing_location)) {
                        matching_list.set(prospective_employee, current_location);
                        location_employee_count[current_location]++;
                        if (location_employee_count[current_location] >= marriage.getLocationSlots().get(current_location)) {
                            unfilled_locations.remove();
                        }
                        location_employee_count[comparing_location]--;
                        if (!unfilled_locations.contains(comparing_location)) {
                            unfilled_locations.add(comparing_location);
                        }
                    }
                }
                //case: employee is unassigned
                else {
                    matching_list.set(prospective_employee, current_location);
                    location_employee_count[current_location]++;
                    if (location_employee_count[current_location] >= marriage.getLocationSlots().get(current_location)) {
                        unfilled_locations.remove();
                    }
                }
            } while (!unfilled_locations.isEmpty() && unfilled_locations.peek() == current_location);
        }
        return new Matching(marriage, matching_list);
    }

    //returns the least preferred employee assigned to a particular store
    public int findWorstEmployee(int location, ArrayList<Integer> preference_list, ArrayList<Integer> matching_list) {
        int worst_employee = -1;
        for (int i = 0; i < matching_list.size(); i++) {
            if (matching_list.get(i) == location) {
                if (worst_employee >= 0) {
                    if (preference_list.indexOf(i) > preference_list.indexOf(worst_employee)) {
                        worst_employee = i;
                    }
                }
                else {
                    worst_employee = i;
                }
            }
        }
        return worst_employee;
    }
}
