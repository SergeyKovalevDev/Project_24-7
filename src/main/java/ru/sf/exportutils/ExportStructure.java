package ru.sf.exportutils;

import ru.sf.models.Statistics;
import ru.sf.models.Student;
import ru.sf.models.University;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportStructure {

    @XmlElementWrapper(name = "studentInfo")
    @XmlElement(name = "studentEntry")
    private List<Student> studentList;

    @XmlElementWrapper(name = "universitiesInfo")
    @XmlElement(name = "universityEntry")
    private List<University> universityList;

    @XmlElementWrapper(name = "statisticalInfo")
    @XmlElement(name = "statisticsEntry")
    private List<Statistics> statisticsList;

    private ExportStructure() {//TODO как сделать без этого конструктора?
    }

    public ExportStructure(List<Student> studentList, List<University> universityList, List<Statistics> statisticsList) {
        this.studentList = studentList;
        this.universityList = universityList;
        this.statisticsList = statisticsList;
    }
}
