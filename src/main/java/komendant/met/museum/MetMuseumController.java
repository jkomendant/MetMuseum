package komendant.met.museum;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MetMuseumController {

    private MetMuseumService service;
    private JLabel primaryImage;
    private JLabel title;
    private JLabel year;
    private JLabel culture;
    private JLabel artistDisplayName;
    private JLabel city;
    private JLabel country;
    private JButton prevButton;
    private JButton nextButton;
    private JComboBox<MetMuseum.DepartmentList.Department> departmentJComboBox;

    ArrayList<Integer> objectIDs;

    public MetMuseumController(MetMuseumService service, JLabel primaryImage,
                               JLabel title, JLabel year, JLabel culture, JLabel artistDisplayName,
                               JLabel city, JLabel country, JButton prevButton, JButton nextButton,
                               JComboBox<MetMuseum.DepartmentList.Department> departmentJComboBox) {

        this.service = service;
        this.primaryImage = primaryImage;
        this.title = title;
        this.year = year;
        this.culture = culture;
        this.artistDisplayName = artistDisplayName;
        this.city = city;
        this.country = country;
        this.prevButton = prevButton;
        this.nextButton = nextButton;
        this.departmentJComboBox = departmentJComboBox;

    }

    public void requestDepartments() {
        service.getDepartments().enqueue(getCallbackDepartments());
    }

    public Callback<MetMuseum.DepartmentList> getCallbackDepartments() {
        return new Callback<MetMuseum.DepartmentList>() {
            @Override
            public void onResponse(Call<MetMuseum.DepartmentList> call, Response<MetMuseum.DepartmentList> response) {
                MetMuseum.DepartmentList departmentList = response.body();
                assert departmentList != null;
                List<MetMuseum.DepartmentList.Department> departments = departmentList.departments;
                for (MetMuseum.DepartmentList.Department department : departments) {
                    departmentJComboBox.addItem(department);
                }

            }

            @Override
            public void onFailure(Call<MetMuseum.DepartmentList> call, Throwable t) {
                t.printStackTrace();
            }
        };
    }

    public void requestObjects(int depID) {
        service.getDepartmentObjects(depID).enqueue(getCallbackDepartmentObjects());
    }

    public Callback<MetMuseum.DepartmentObjects> getCallbackDepartmentObjects() {
        return new Callback<MetMuseum.DepartmentObjects>() {
            @Override
            public void onResponse(Call<MetMuseum.DepartmentObjects> call, Response<MetMuseum.DepartmentObjects> response) {
                MetMuseum.DepartmentObjects departmentObjects = response.body();
                assert departmentObjects != null;
                objectIDs = departmentObjects.objectIDs;
                requestObjectData(0);
            }

            @Override
            public void onFailure(Call<MetMuseum.DepartmentObjects> call, Throwable t) {
                t.printStackTrace();
            }
        };
    }

    public void requestObjectData(int objectIndex) {
        nextButton.setEnabled(true);
        prevButton.setEnabled(true);
        if (objectIndex == objectIDs.size() - 1) {
            nextButton.setEnabled(false);
        }

        if (objectIndex == 0) {
            prevButton.setEnabled(false);
        }
        service.getObject(objectIDs.get(objectIndex)).enqueue(getCallbackObject());
    }


    public Callback<MetMuseum.Object> getCallbackObject() {
        return new Callback<MetMuseum.Object>() {
            @Override
            public void onResponse(Call<MetMuseum.Object> call, Response<MetMuseum.Object> response) {

                MetMuseum.Object object = response.body();
                assert object != null;

                try {
                    if (object.primaryImage.equals("")) {
                        primaryImage.setIcon(null);
                        primaryImage.setText("No Image Available");
                    } else {
                        URL url = new URL(object.primaryImage);
                        BufferedImage image = ImageIO.read(url);
                        Image scaledImg = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                        primaryImage.setIcon(new ImageIcon(scaledImg));
                        primaryImage.setText("");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    primaryImage.setIcon(null);
                    primaryImage.setText("No Image Available");
                }

                title.setText(object.title);
                year.setText(String.valueOf(object.accessionYear));
                artistDisplayName.setText(object.artistDisplayName);
                culture.setText(object.culture);
                city.setText(object.city);
                country.setText(object.country);
            }

            @Override
            public void onFailure(Call<MetMuseum.Object> call, Throwable t) {
                t.printStackTrace();
            }
        };
    }

}