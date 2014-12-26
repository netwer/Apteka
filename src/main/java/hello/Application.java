package hello;

import hello.Helpers.Initializator;
import hello.Model.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        List<Role> roles = new ArrayList<Role>() ;
        roles.add(new Role(1,"admin"));
        roles.add(new Role(2,"patient"));
        roles.add(new Role(3,"doctor"));

        List<Apteka> aptekas = new ArrayList<Apteka>();
        Apteka apt1 = new Apteka(1,"Ленинский проспект 1");
        aptekas.add(apt1);
        Apteka apt2 = new Apteka(2,"Невский проспект 47");
        aptekas.add(apt2);
        Apteka apt3 = new Apteka(3,"ул. Гагарина 34");
        aptekas.add(apt3);

        List<Drug> drugs = new ArrayList<Drug>();
        drugs.add(new Drug(1,"Карволол",1));
        drugs.add(new Drug(2,"Валерианка",1));
        drugs.add(new Drug(3,"Каптоприл",1));
        drugs.add(new Drug(4,"Називин",1));
        drugs.add(new Drug(5,"Но-шпа",1));

        List<Order> ordersOne = new ArrayList<Order>();
        List<Drug> drugsOne = new ArrayList<Drug>();
        drugsOne.add(drugs.get(0));
        drugsOne.add(drugs.get(1));
        ordersOne.add(new Order(1,drugsOne, "done","12/23/2014",apt1));

        List<Drug> drugsTwo = new ArrayList<Drug>();
        drugsTwo.add(drugs.get(2));
        drugsTwo.add(drugs.get(3));
        drugsTwo.add(drugs.get(4));
        ordersOne.add(new Order(2,drugsTwo, "inProcess","12/27/2014",apt1));

        List<Order> ordersTwo = new ArrayList<Order>();
        List<Drug> drugsThree = new ArrayList<Drug>();
        drugsThree.add(drugs.get(2));
        drugsThree.add(drugs.get(4));
        ordersTwo.add(new Order(3,drugsThree, "inProcess","10/23/2014",apt2));

        List<Drug> drugsFour = new ArrayList<Drug>();
        drugsFour.add(drugs.get(1));
        drugsFour.add(drugs.get(4));
        drugsFour.add(drugs.get(3));
        ordersTwo.add(new Order(4,drugsFour, "inProcess","10/23/2014",apt3));

        List<PatientRecords> patientRecordses = new ArrayList<PatientRecords>();
        patientRecordses.add(new PatientRecords(1,"Адрес Пациента 1","456284950","Головная боль, судороги","Эпидемия"));
        patientRecordses.add(new PatientRecords(2,"Адрес Пациента 2","4567896","",""));

        List<User> users = new ArrayList<User>();
        users.add(new User(1,"Vasya","Василий Петрович","12345",null,null));
        users.add(new User(2,"Petya","Петр Егнатьевич","12345",ordersOne,patientRecordses.get(0)));
        User user = new User(3,"Igor","Игорь Николаевич","12345",ordersTwo,patientRecordses.get(1));
        users.add(user);
        users.add(new User(4,"Svetlana","Светлана Аникенко","12345",null,null)); //doctor
        users.add(new User(5,"Oleg","Олег Святославович","12345",null,null));


        List<UserRole> userRoles = new ArrayList<UserRole>();
        userRoles.add(new UserRole(1,1,1));
        userRoles.add(new UserRole(2,2,2));
        userRoles.add(new UserRole(3,3,2));
        userRoles.add(new UserRole(4,4,3));
        userRoles.add(new UserRole(5,5,2));

        List<Order> orders = new ArrayList<Order>();
        orders.addAll(ordersOne);
        orders.addAll(ordersTwo);

        List<DoctorAppointments> doctorAppointmentses = new ArrayList<DoctorAppointments>();
        doctorAppointmentses.add(new DoctorAppointments(1,4,user,"26.12.2014 12:30","---"));

        Initializator initializator = new Initializator(users,roles,userRoles,orders,doctorAppointmentses,patientRecordses,drugs,aptekas);

        SpringApplication.run(Application.class, args);
    }

} 