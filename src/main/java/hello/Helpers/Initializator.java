package hello.Helpers;

import hello.Model.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 23.12.14
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */
public class Initializator {
    private static List<User> users;
    private static List<Role> roles;
    private static List<UserRole> userRoles;
    private static List<Order> orders;
    private static List<DoctorAppointments> doctorAppointmentses;
    private static List<PatientRecords> patientRecordses;
    private static List<Drug> drugs;
    private static List<Apteka> aptekas;

    public Initializator (
            List<User> users,
            List<Role> roles,
            List<UserRole> userRoles,
            List<Order> orders,
            List<DoctorAppointments> doctorAppointmentses,
            List<PatientRecords> patientRecordses,
            List<Drug> drugs,
            List<Apteka> aptekas) {
        this.users = users;
        this.roles = roles;
        this.userRoles = userRoles;
        this.orders = orders;
        this.doctorAppointmentses = doctorAppointmentses;
        this.patientRecordses = patientRecordses;
        this.drugs = drugs;
        this.aptekas = aptekas;
    }


    public static List<Role> getRoles() {
        return roles;
    }

    public static List<UserRole> getUserRoles() {
        return userRoles;
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static List<DoctorAppointments> getDoctorAppointmentses() {
        return doctorAppointmentses;
    }

    public static List<PatientRecords> getPatientRecordses() {
        return patientRecordses;
    }

    public static void setPatientRecordses(List<PatientRecords> patientRecordses) {
        Initializator.patientRecordses = patientRecordses;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        Initializator.users = users;
    }

    public static List<Drug> getDrugs() {
        return drugs;
    }

    public static void setDrugs(List<Drug> drugs) {
        Initializator.drugs = drugs;
    }

    public static List<Apteka> getAptekas() {
        return aptekas;
    }
}
