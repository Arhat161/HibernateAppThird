package org.example;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Hello world!
 */
public class App {

    // print all records from List<Person>
    public static void printAllPersonsFromList(List<Person> persons) {
        if (persons != null) {
            for (Person person : persons) {
                System.out.println(person);
            }
        } else {
            System.out.println("No persons!");
        }
    }

    public static void main(String[] args) {
        // First, create Configuration (all connection properties in 'hibernate.properties' file
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        // Second, create sessionFactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        // Third, get session to wor from sessionFactory
        Session session = sessionFactory.getCurrentSession();

        try {
            // open transaction
            session.beginTransaction();
            // get all records from table Person
            List<Person> allPersonsFromTable = session.createQuery("SELECT a FROM Person a").getResultList();

            printAllPersonsFromList(allPersonsFromTable);

            // get records from table Person, where age > 30
            List<Person> personsWhereAgeMoreThan30 = session.createQuery("SELECT a FROM Person a WHERE a.age > 30").getResultList();

            printAllPersonsFromList(personsWhereAgeMoreThan30);

            // get records from table Person, where name start at char 'T'
            List<Person> personsWhereNameLikeT = session.createQuery("SELECT a FROM Person a WHERE a.name LIKE 'T%'").getResultList();

            printAllPersonsFromList(personsWhereNameLikeT);

            // update all records from table Person where age < 30
            session.createQuery("UPDATE Person SET name='Test' WHERE age < 30").executeUpdate();

            // delete all records from table Person where age < 30
            session.createQuery("DELETE FROM Person WHERE age < 30").executeUpdate();

            // close transaction
            session.getTransaction().commit();

        } finally {
            // close sessionFactory
            sessionFactory.close();
        }


    }
}
