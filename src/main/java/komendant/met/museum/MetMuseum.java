package komendant.met.museum;

import java.util.ArrayList;
import java.util.List;

public class MetMuseum {

    static class DepartmentList {
        List<Department> departments;


        static class Department {
            int departmentId;
            String displayName;

            @Override
            public String toString() {

                return displayName;
            }

        }
    }

    static class DepartmentObjects {
        int total;
        ArrayList<Integer> objectIDs;
    }

    static class Object {
        int accessionYear;
        String primaryImage;
        String title;
        String culture;
        String artistDisplayName;
        String city;
        String country;
    }


}
