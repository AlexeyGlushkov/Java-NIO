import com.viliric.spring.DAO.model.Student;
import com.viliric.spring.DAO.model.StudentDAOImpl;

/**
 * Created by alexey on 1/15/16.
 */
public class StudentDAOImplTest {

    public void test() {
        StudentDAOImpl studentDAOImpl = new StudentDAOImpl();

        Student student = new Student();
        student.setSurname("Иванов");
        student.setName("Иван");
        student.setPatronymic("Иванович");

        studentDAOImpl.insert(student);
    }
}
