import com.weijie.core.common.KryoSerializer;
import com.weijie.core.common.PasswordHashing;
import com.weijie.core.entities.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class testEntities {
    public static void main(String[] args) {
        ArrayList<String> friends1 = new ArrayList<>();
        friends1.add("Alexander|hjrt56@gmail.com");
        friends1.add("Emma|dfoe435@gmail.com");
        friends1.add("Sophia|bvvmjw4@gmail.com");
        friends1.add("Hannah|aquprr5@gmail.com");
        friends1.add("William|fhnwwy@gmail.com");
        friends1.add("John|sdnhkju@gmail.com");
        friends1.add("Christopher|qwerpjb@gmail.com");
        friends1.add("Richard|vcxnwrt@gmail.com");
        ArrayList<String> friends2 = new ArrayList<>();
        friends2.add("Grace|qqwe545@gmail.com");
        friends2.add("Amelia|sdfh@gmail.com");
        friends2.add("Benjamin|nkie456@gmail.com");
        friends2.add("Andrew|sdfh@gmail.com");
        friends2.add("Lily|dfbh4353@gmail.com");
        friends2.add("Student3|gfjj@gmail.com");
        ArrayList<String> finishedQuiz1 = new ArrayList<>();
        finishedQuiz1.add("The Periodic Table Challenge");
        finishedQuiz1.add("Renewable Energy Sources");
        finishedQuiz1.add("Engineering Design Process");
        ArrayList<String> finishedQuiz2 = new ArrayList<>();
        finishedQuiz2.add("Computer Hardware Components");
        finishedQuiz2.add("Coding Fundamentals");
        finishedQuiz2.add("Probability and Statistics");
        ArrayList<String> booking = new ArrayList<>();
//        booking.add("Student1|Petrosains Science Discovery Centre|03/02/2022");
//        booking.add("Student1|Petrosains Science Discovery Centre|03/02/2022");
//        booking.add("Student1|Petrosains Science Discovery Centre|03/02/2022");
        ArrayList<String> event1 = new ArrayList<>();
        event1.add("Spring Blossom Festival|04/02/2022");
        event1.add("Spring Blossom Festival|06/02/2022");
        ArrayList<String> event2 = new ArrayList<>();
        event2.add("Artisan Craft Fair|05/02/2022");
        event2.add("Artisan Craft Fair|09/02/2022");
        Student s1 = new Student("Student1", "sdfh@gmail.com", PasswordHashing.get("Anxious0"),
                "123, -34", 72, new ArrayList<>(Collections.singletonList("Abigail")),
                event1, new ArrayList<>(), friends1, finishedQuiz1);
//        Thread.sleep(1000);
        Student s2 = new Student("Student2", "yutk@gmail.com", PasswordHashing.get("Anxious0"),
                "123, -34", 72, new ArrayList<>(),
                event2, new ArrayList<>(), friends1, finishedQuiz1);
        ArrayList<Student> request1 = new ArrayList<>();
        request1.add(s1);
        request1.add(s2);
        Student s3 = new Student("Student3", "gfjj@gmail.com", PasswordHashing.get("Anxious0"),
                "123, -34", 71, new ArrayList<>(Arrays.asList("Robert", "Ashli")),
                event1, new ArrayList<>(), new ArrayList<>(), finishedQuiz1);
//        ArrayList<Student> request2 = new ArrayList<>();
//        request2.add(s3);
        Student s4 = new Student("Student4", "cvbn@gmail.com", PasswordHashing.get("Anxious0"),
                "63, -8", 45, new ArrayList<>(Arrays.asList("Bob", "Ryan")),
                event2, new ArrayList<>(), new ArrayList<>(), finishedQuiz2);

//        EventFilterService.getAvailableTimeSlots(s4).forEach(System.out::println);

        ArrayList<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);

        Parent p1 = new Parent("Parent1", "mwiy@gmail.com", PasswordHashing.get("Anxious0"), students, booking);
        Teacher t1 = new Teacher("Teacher1", "vqer@gmail.com", PasswordHashing.get("Anxious0"));
        Teacher t2 = new Teacher("Teacher2", "iytu@gmail.com", PasswordHashing.get("Anxious0"));

        /*
        * Test 1
        * Role: Parent
        * Username: Bob
        * Email: 12345@gmail.com
        * Password: Anxious0
        * */

        ArrayList<User> userList = new ArrayList<>(Arrays.asList(s1, s2, s3, s4, p1, t1, t2));

//        UserFilterService.setUser("vjup@gmail.com");
//        System.out.println(UserFilterService.currentUser);

        KryoSerializer.serialize(KryoSerializer.USER_DATA_PATH, userList);
//        System.out.println(UserFilterService.getLeaderboard());
//        System.out.println(UserFilterService.setUser("sdfh@gmail.com"));
//        DestinationService.get().forEach((k, v) -> System.out.println(k + " -> " + v));
//        System.out.println(KryoSerializer.getUserList().stream().filter(u -> u.getID() == User.Role.TEACHER).toList());

        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(
                "Spring Blossom Festival", "Join us for a celebration of spring's arrival with live music, food stalls featuring seasonal delicacies, and a showcase of local artisans. Don't miss the stunning cherry blossom display!", "City Park",
                "03/02/2022", "10:00 AM - 6:00 PM"));
        events.add(new Event(
                "Sustainable Living Workshop", "Learn practical tips for sustainable living at this interactive workshop. From reducing waste to energy conservation, discover ways to make a positive impact on the environment.", "Green Living Center",
                "03/02/2022", "10:00 AM - 12:00 PM"));
        events.add(new Event(
                "Cultural Food Festival", "Experience a culinary journey around the world! Sample diverse cuisines from different cultures, accompanied by live music and cultural performances.", "City Plaza",
                "03/02/2022", "5:00 PM - 9:00 PM"));
        events.add(new Event(
                "Fitness Bootcamp Challenge", "Get ready to sweat in this high-intensity fitness challenge! Join us for a full-body workout led by expert trainers, suitable for all fitness levels.", "Central Park",
                "03/02/2022", "7:00 AM - 9:00 AM"));
        events.add(new Event(
                "Artisan Craft Fair", "Explore handmade treasures at our artisan craft fair. Browse unique artworks, jewelry, pottery, and more created by local artisans. Support small businesses and find one-of-a-kind gifts.", "Community Center Courtyard",
                "03/02/2022", "11:00 AM to 4:00 PM"));
        events.add(new Event(
                "Tech Talk Series: AI in Healthcare", "Delve into the role of artificial intelligence in revolutionizing healthcare. Experts will discuss AI applications in diagnostics, patient care, and medical research, followed by a Q&A session.", "Innovation Hub Auditorium",
                "03/02/2022", "2:00 PM to 4:00 PM"));
        events.add(new Event(
                "Charity Gala Dinner", "Support a worthy cause while indulging in an elegant evening of fine dining and entertainment. Proceeds from this gala dinner will benefit local charities dedicated to community welfare.", "Grand Ballroom, Riverside Hotel",
                "04/02/2022", "6:30 PM to 11:00 PM"));

        KryoSerializer.serialize(KryoSerializer.EVENT_PATH, events);

//        System.out.println(EventFilterService.getTodayList());
//        System.out.println(EventFilterService.getNextList());
//        EventFilterService.addEvent(new Event("Fitness Bootcamp Challenge", "Get ready to sweat in this high-intensity fitness challenge! Join us for a full-body workout led by expert trainers, suitable for all fitness levels.", "Central Park",
//                "06/02/2022", "7:00 AM - 9:00 AM"));
//        System.out.println(EventFilterService.getTodayList());
//        System.out.println(EventFilterService.getNextList());

//        events.forEach(e -> Resource.fileWriter(e, Resource.EventPath));
//        Resource.fileWriter(s2, Resource.UserDataPath);
//        System.out.println(Resource.roleLoader("sdfh@gmail.com"));

//        events.forEach(event -> Resource.fileWriter(event, Resource.EventPath));

//        System.out.println(Resource.eventLoader());
//        System.out.println(Event.getDateList(events, "02/02/2022"));
//        System.out.println(Resource.fileReader(User.Role.STUDENT));

        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(new Quiz(Quiz.Theme.SCIENCE, "The Periodic Table Challenge", "Test your knowledge of the periodic table of elements.", "null"));
        quizzes.add(new Quiz(Quiz.Theme.SCIENCE, "Renewable Energy Sources", "Assess your understanding of different renewable energy technologies.", "null"));
        quizzes.add(new Quiz(Quiz.Theme.ENGINEERING, "Engineering Design Process", "Explore the steps involved in the engineering design process.", "null"));
        quizzes.add(new Quiz(Quiz.Theme.ENGINEERING, "Structural Engineering Principles", "Explore the fundamental concepts in structural engineering.", "null"));
        quizzes.add(new Quiz(Quiz.Theme.MATHEMATICS, "Geometry Essentials", "Test your knowledge of basic geometric concepts.", "null"));
        quizzes.add(new Quiz(Quiz.Theme.MATHEMATICS, "Probability and Statistics", "Test your understanding of basic probability and statistical concepts.", "null"));
        quizzes.add(new Quiz(Quiz.Theme.TECHNOLOGY, "Coding Fundamentals", "Assess your understanding of basic programming concepts.", "null"));
        quizzes.add(new Quiz(Quiz.Theme.TECHNOLOGY, "Computer Hardware Components", "Test your knowledge of the key components in a computer system.", "null"));

        KryoSerializer.serialize(KryoSerializer.QUIZ_PATH, quizzes);

//        System.out.println(UserFilterService.setUser("yutk@gmail.com"));
//        System.out.println(QuizFilterService.getFilteredQuizzes(List.of(Quiz.Theme.SCIENCE, Quiz.Theme.MATHEMATICS)));

//        quizzes.forEach(event -> Resource.fileWriter(event, Resource.QuizPath));
//        System.out.println(Resource.quizLoader());
//        System.out.println(QuizFilterService.getFilteredQuizzes(Resource.quizLoader(), List.of(Quiz.Theme.SCIENCE, Quiz.Theme.MATHEMATICS)));
    }
}