package komendant.met.museum;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class MetMuseumFrame extends JFrame {

    int index;
    JComboBox<MetMuseum.DepartmentList.Department> departmentJComboBox;
    JPanel departmentPanel;
    JLabel primaryImage;
    JLabel title;
    JLabel year;
    JLabel culture;
    JLabel artistDisplayName;
    JLabel city;
    JLabel country;
    JPanel objectPanel;
    JPanel arrowPanel;
    BasicArrowButton prevButton;
    BasicArrowButton nextButton;
    MetMuseumService service;
    MetMuseumController controller;

    public MetMuseumFrame(){
        setSize(700,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Virtual Met Museum");
        setLayout(new BorderLayout());

        departmentPanel = new JPanel();
        departmentPanel.setLayout(new FlowLayout());

        departmentJComboBox = new JComboBox<>();
        departmentJComboBox.isEditable();
        departmentPanel.add(departmentJComboBox);
        add(departmentPanel, BorderLayout.WEST);

        objectPanel = new JPanel();
        objectPanel.setLayout(new BoxLayout(objectPanel, BoxLayout.Y_AXIS));
        arrowPanel = new JPanel();
        arrowPanel.setLayout(new FlowLayout());

        primaryImage = new JLabel();
        title = new JLabel();
        year = new JLabel();
        culture = new JLabel();
        artistDisplayName = new JLabel();
        city = new JLabel();
        country = new JLabel();
        prevButton = new BasicArrowButton(BasicArrowButton.WEST);
        nextButton = new BasicArrowButton(BasicArrowButton.EAST);

        objectPanel.add(primaryImage);
        objectPanel.add(title);
        objectPanel.add(year);
        objectPanel.add(culture);
        objectPanel.add(artistDisplayName);
        objectPanel.add(city);
        objectPanel.add(country);

        prevButton.addActionListener(ActionEvent -> getPrevObject());
        nextButton.addActionListener(ActionEvent -> getNextObject());
        arrowPanel.add(prevButton);
        arrowPanel.add(nextButton);

        add(objectPanel, BorderLayout.CENTER);
        add(arrowPanel, BorderLayout.SOUTH);

        departmentJComboBox.addActionListener(ActionEvent -> getDepartmentObjects());

        service = new MetMuseumServiceFactory().getInstance();
        controller = new MetMuseumController(service, primaryImage, title, year, culture, artistDisplayName,
                city, country, prevButton, nextButton);
        controller.requestDepartments(departmentJComboBox);
    }

    public void getDepartmentObjects(){
        MetMuseum.DepartmentList.Department selectDepartment =
                (MetMuseum.DepartmentList.Department) departmentJComboBox.getSelectedItem();
        assert selectDepartment != null;
        int depID = selectDepartment.departmentId;
        controller.requestObjects(depID);
        index = 0;
    }

    public void  getPrevObject(){
        index --;
        controller.requestObjectData(index);
    }

    public void getNextObject(){
        index++;
        controller.requestObjectData(index);
    }

    public static void main(String[] args) {
        new MetMuseumFrame().setVisible(true);
    }
}