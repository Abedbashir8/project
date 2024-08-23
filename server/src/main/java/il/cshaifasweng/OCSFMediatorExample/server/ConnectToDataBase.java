//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ConnectToDataBase {
    private static Session session;
    private static List<Movie> movies;

    public ConnectToDataBase() {
    }

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Movie.class);
        ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder()).applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void CreateData() throws Exception {
        System.out.print("Data Creation Start\n");
        String description1="The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.";
        Movie movie1 = new Movie("The GodFather","10:00","ALbert Rody", "Al pacino",description1);

        String description2="A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.";
        Movie movie2 = new Movie("Inception","11:00","Christopher Nolan", "Leonardo Di Caprio",description2);

        String description3="An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.";
        Movie movie3 = new Movie("Fight Club","18:00","Joel Silver", "Art Linson",description3);

        String description4="A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.";
        Movie movie4 = new Movie("The Matrix","15:00","Joel Silver", "Keanu Reeves",description4);

        String description5="The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.";
        Movie movie5 = new Movie("Goodfellas","16:00","ALbert Rody", "Ray Liotta",description5);

        String description6="Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.";
        Movie movie6 = new Movie("Se7en","14:00","Christopher Nolan", "Brad pitt",description6);
        session.save(movie1);
        session.flush();
        session.save(movie2);
        session.flush();
        session.save(movie3);
        session.flush();
        session.save(movie4);
        session.flush();
        session.save(movie5);
        session.flush();
        session.save(movie6);
        session.flush();
        System.out.print("Data Creation Finish");
    }

    static List<Movie> getAllMovies() throws Exception {
        Session session = null;

        List var6;
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
            Root<Movie> root = query.from(Movie.class);
            query.select(root);
            List<Movie> data = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            var6 = data;
        } catch (Exception var10) {
            if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }

            var10.printStackTrace();
            throw var10;
        } finally {
            if (session != null) {
                session.close();
            }

        }

        return var6;
    }

    private static void printAllMovies() throws Exception {
        List<Movie> movies = getAllMovies();
        System.out.println("******** Movies ********\n");
        Iterator mov1 = movies.iterator();

        while(mov1.hasNext()) {
            Movie movie = (Movie)mov1.next();
            System.out.println(movie);
        }

    }

    public static void updateShowtime(String title, String newShowtime) throws Exception {
        System.out.println("Update function reached...");
        System.out.println("The new time is " + newShowtime);
        SessionFactory sessionFactory = getSessionFactory();
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
            Root<Movie> root = query.from(Movie.class);
            query.select(root).where(builder.equal(root.get("name"), title));
            List<Movie> movies = session.createQuery(query).getResultList();
            if (!movies.isEmpty()) {
                Movie temp = (Movie)movies.get(0);
                temp.setTime(newShowtime);
                session.update(temp);
                session.getTransaction().commit();
                System.out.println("Updated showtime for movie: " + title);
                return;
            }

            System.out.println("Movie with title \"" + title + "\" not found.");
            session.getTransaction().rollback();
        } catch (Exception var12) {
            if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }

            System.out.println("Error updating showtime: " + var12.getMessage());
            throw var12;
        } finally {
            if (session != null) {
                session.close();
            }

        }

    }

    public static Session initializeDatabase() throws IOException {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.clear();
            CreateData();
            session.getTransaction().commit();
        } catch (Exception var4) {
            if (session != null) {
                session.getTransaction().rollback();
            }

            System.err.println("An error occured, changes have been rolled back.");
            var4.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }

        }

        return null;
    }

    public static void EndConnection() {
        session.getTransaction().commit();
        session.close();
    }
}
