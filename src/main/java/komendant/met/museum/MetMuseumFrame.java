package komendant.met.museum;

import javax.swing.*;
import java.awt.*;

public class MetMuseumFrame extends JFrame {

    private int index;
    private JComboBox<MetMuseum.DepartmentList.Department> departmentJComboBox;
    private JPanel departmentPanel;
    private JLabel primaryImage;
    private JLabel title;
    private JLabel year;
    private JLabel culture;
    private JLabel artistDisplayName;
    private JLabel city;
    private JLabel country;
    private JPanel objectPanel;
    private JPanel arrowPanel;
    private JButton prevButton;
    private JButton nextButton;
    private MetMuseumService service;
    private MetMuseumController controller;

    public MetMuseumFrame() {
        setSize(800, 600);
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
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        objectPanel.add(title);
        objectPanel.add(year);
        objectPanel.add(culture);
        objectPanel.add(artistDisplayName);
        objectPanel.add(city);
        objectPanel.add(country);
        objectPanel.add(primaryImage);

        prevButton.addActionListener(ActionEvent -> getPrevObject());
        nextButton.addActionListener(ActionEvent -> getNextObject());
        arrowPanel.add(prevButton);
        arrowPanel.add(nextButton);

        add(objectPanel, BorderLayout.CENTER);
        add(arrowPanel, BorderLayout.SOUTH);

        departmentJComboBox.addActionListener(ActionEvent -> getDepartmentObjects());

        service = new MetMuseumServiceFactory().getInstance();
        controller = new MetMuseumController(service, primaryImage, title, year, culture, artistDisplayName,
                city, country, prevButton, nextButton, departmentJComboBox);
        controller.requestDepartments();
    }

    public void getDepartmentObjects() {
        MetMuseum.DepartmentList.Department selectDepartment =
                (MetMuseum.DepartmentList.Department) departmentJComboBox.getSelectedItem();
        assert selectDepartment != null;
        int depID = selectDepartment.departmentId;
        controller.requestObjects(depID);
        index = 0;
    }

    public void getPrevObject() {
        index--;
        controller.requestObjectData(index);
    }

    public void getNextObject() {
        index++;
        controller.requestObjectData(index);
    }

    public static void main(String[] args) {

        new MetMuseumFrame().setVisible(true);
    }
}