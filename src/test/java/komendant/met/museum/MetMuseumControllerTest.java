package komendant.met.museum;

import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MetMuseumControllerTest {

    @Test
    public void requestDepartments() {
        //given
        MetMuseumService service = mock(MetMuseumService.class);
        JLabel label = mock(JLabel.class);
        JButton button = mock(JButton.class);
        JComboBox<MetMuseum.DepartmentList.Department> comboBox = mock(JComboBox.class);
        MetMuseumController controller = new MetMuseumController(service, label, label, label, label, label,
                label, label, button, button, comboBox);
        Call<MetMuseum.DepartmentList> call = mock(Call.class);
        Response<MetMuseum.DepartmentList> response = mock(Response.class);

        MetMuseum.DepartmentList depList = new MetMuseum.DepartmentList();

        MetMuseum.DepartmentList.Department dep = new MetMuseum.DepartmentList.Department();
        dep.displayName = "dep";
        dep.departmentId = 1;
        List<MetMuseum.DepartmentList.Department> deps = new ArrayList<>();
        deps.add(dep);
        depList.departments = deps;


        doReturn(depList).when(response).body();

        //when
        controller.getCallbackDepartments().onResponse(call, response);

        //then
        verify(comboBox).addItem(depList.departments.get(0));    }

    @Test
    public void requestObjects() {
        //given
        MetMuseumService service = mock(MetMuseumService.class);
        JLabel label = mock(JLabel.class);
        JButton button = mock(JButton.class);
        JComboBox<MetMuseum.DepartmentList.Department> comboBox = mock(JComboBox.class);
        MetMuseumController controller = new MetMuseumController(service, label, label, label, label, label, label,
                label, button, button, comboBox);
        ArrayList<Integer> objectIDs = mock(ArrayList.class);
        Call<MetMuseum.DepartmentObjects> call = mock(Call.class);
        Call<MetMuseum.Object> objCall = mock(Call.class);
        Response<MetMuseum.DepartmentObjects> response = mock(Response.class);

        MetMuseum.DepartmentObjects depObjects = new MetMuseum.DepartmentObjects();
        ArrayList<Integer> objIds = new ArrayList<>();
        objIds.add(67676);

        depObjects.total = 1;
        depObjects.objectIDs = objIds;
        doReturn(objCall).when(service).getObject(depObjects.objectIDs.get(0));


        doReturn(depObjects).when(response).body();

        //when
        controller.getCallbackDepartmentObjects().onResponse(call, response);

        //then
        verify(objectIDs).equals(depObjects.objectIDs);

    }

    @Test
    public void requestObjectData() {
        //given
        MetMuseumService service = mock(MetMuseumService.class);
        JLabel label = mock(JLabel.class);
        JButton button = mock(JButton.class);
        JComboBox<MetMuseum.DepartmentList.Department> comboBox = mock(JComboBox.class);
        MetMuseumController controller = new MetMuseumController(service, label, label, label, label, label,
                label, label, button, button, comboBox);
        Call<MetMuseum.Object> call = mock(Call.class);
        Response<MetMuseum.Object> response = mock(Response.class);

        MetMuseum.Object object = new MetMuseum.Object();
        object.primaryImage = "";
        object.accessionYear = 1912;
        object.artistDisplayName = "name";
        object.city = "city";
        object.country = "country";
        object.culture = "culture";
        object.title = "title";

        doReturn(object).when(response).body();

        //when
        controller.getCallbackObject().onResponse(call, response);

        //then
        verify(label).setText(object.artistDisplayName);
        verify(label).setText(object.country);
        verify(label).setText(object.city);
        verify(label).setText(object.culture);
        verify(label).setText(object.title);
        verify(label).setText(String.valueOf(object.accessionYear));
        verify(label).setText("No Image Available");
    }
}