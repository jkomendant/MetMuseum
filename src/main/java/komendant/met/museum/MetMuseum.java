package komendant.met.museum;

import java.util.ArrayList;
import java.util.List;

public class MetMuseum {

    class DepartmentList {
        List<Department> departments;


        class Department {
            int departmentId;
            String displayName;

            @Override
            public String toString() {

                return displayName;
            }

        }
    }

    class DepartmentObjects {
        int total;
        ArrayList<Integer> objectIDs;
    }

    class Object {
        int accessionYear;
        String primaryImage;
        String title;
        String culture;
        String artistDisplayName;
        String city;
        String country;
    }


}
