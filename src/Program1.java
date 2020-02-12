/*
 * Name: Jesse Zhang
 * EID: jyz225
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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
        /*//last index reserved for unassigned employees
        ArrayList<ArrayList<Integer>> location = new ArrayList<>();
        for (int i = 0; i <= marriage.getLocationCount(); i++) {
            location.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < marriage.getEmployeeCount(); i++) {
            int employee_location = marriage.getEmployeeMatching().get(i);
            if (employee_location < 0) {
                employee_location = marriage.getLocationCount();
            }
            location.get(employee_location).add(i);
        }

        //Instability type 1 : involves unassigned employees
        for (int i = 0; i < marriage.getLocationCount(); i++) {
            for (int assigned_employee : location.get(i)) {
                for (int unassigned_employee : location.get(location.size() - 1)) {
                    if (marriage.getLocationPreference().get(i).indexOf(assigned_employee) > marriage.getLocationPreference().get(i).indexOf(unassigned_employee)) {
                        return false;
                    }
                }
            }
        }

        //Instability type 2
        for (int i = 0; i < marriage.getLocationCount() - 1; i++) {
            for (int employee_here : location.get(i)) {
                for (int j = i + 1; j < marriage.getLocationCount(); j++) {
                    for(int employee_there : location.get(j)) {
                        if (marriage.getLocationPreference().get(i).indexOf(employee_here) > marriage.getLocationPreference().get(i).indexOf(employee_there)) {
                            if (marriage.getEmployeePreference().get(j).indexOf(j) > marriage.getEmployeePreference().get(j).indexOf(i)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }*/

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
        /* TODO implement this function */
        return null; /* TODO remove this line */
    }

    /**
     * Determines a location optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_locationoptimal(Matching marriage) {
        /* TODO implement this function */
        return null; /* TODO remove this line */
    }
}
