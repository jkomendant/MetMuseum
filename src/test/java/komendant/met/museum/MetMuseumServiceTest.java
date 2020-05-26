package komendant.met.museum;

import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MetMuseumServiceTest {

    @Test
    public void getDepartments() throws IOException {
        //given
        MetMuseumService service = new MetMuseumServiceFactory().getInstance();

        //when
        Response<MetMuseum.DepartmentList> response = service.getDepartments().execute();

        //then
        assertTrue(response.toString(), response.isSuccessful());
        MetMuseum.DepartmentList departmentsList = response.body();
        assertNotNull(departmentsList);

        List<MetMuseum.DepartmentList.Department> departments = departmentsList.departments;
        assertFalse(departments.isEmpty());
        MetMuseum.DepartmentList.Department department = departments.get(0);
        assertNotNull(department.displayName);
        assert(department.departmentId > 0);
    }

    @Test
    public void getDepartmentObjects() throws IOException {
        //given
        MetMuseumService service = new MetMuseumServiceFactory().getInstance();

        //when
        Response<MetMuseum.DepartmentObjects> response = service.getDepartmentObjects(18).execute();

        //then
        assertTrue(response.toString(), response.isSuccessful());
        MetMuseum.DepartmentObjects departmentObjects = response.body();
        assertNotNull(departmentObjects);

        assert(departmentObjects.total > 0);
        ArrayList<Integer> objectIDs = departmentObjects.objectIDs;
        assertNotNull(objectIDs);
        assertFalse(objectIDs.isEmpty());
        int objectID = objectIDs.get(0);
        assert(objectID > 0);

    }

    @Test
    public void getObject() throws IOException {
        //given
        MetMuseumService service = new MetMuseumServiceFactory().getInstance();

        //when
        Response<MetMuseum.Object> response = service.getObject(49).execute();

        //then
        assertTrue(response.toString(), response.isSuccessful());
        MetMuseum.Object object = response.body();
        assertNotNull(object);

        assert(object.accessionYear > 0);
        assertNotNull(object.artistDisplayName);
        assertNotNull(object.city);
        assertNotNull(object.country);
        assertNotNull(object.culture);
        assertNotNull(object.department);
        assertNotNull(object.primaryImage);
        assertNotNull(object.title);


    }
}