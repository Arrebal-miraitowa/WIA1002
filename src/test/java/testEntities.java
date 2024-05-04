import com.weijie.core.common.Resource;
import com.weijie.core.entities.Event;
import com.weijie.core.entities.Quiz;
import com.weijie.core.entities.Student;
import com.weijie.core.entities.User;

import java.util.ArrayList;
import java.util.List;

public class testEntities {
    public static void main(String[] args) {
        ArrayList<String> r = new ArrayList<>();
        r.add("114514");
        r.add("7546");
        r.add("1689");
        ArrayList<String> f = new ArrayList<>();
        f.add("qwert");
        f.add("biyk");
        f.add("zscre");
        Student s1 = new Student("Jack", "wahh@gmail.com", "Sa12345", 23,
                "Alex", "Peter",
                r, f);
        Student s2 = new Student("Jone", "sdfh@gmail.com", "345354", 72,
                "Bob", "Ashli",
                r, f);
        Student s3 = new Student("White", "apple_yummy@gmail.com", "Anxious0");
        ArrayList<User> as = new ArrayList<>();
        as.add(s1);
        as.add(s2);
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event("Spring Blossom Festival", "Join us for a celebration of spring's arrival with live music, food stalls featuring seasonal delicacies, and a showcase of local artisans. Don't miss the stunning cherry blossom display!", "City Park", "03/02/2022", "10:00 AM - 6:00 PM"));
        events.add(new Event("Sustainable Living Workshop", "Learn practical tips for sustainable living at this interactive workshop. From reducing waste to energy conservation, discover ways to make a positive impact on the environment.", "Green Living Center", "03/02/2022", "10:00 AM - 12:00 PM"));
        events.add(new Event("Cultural Food Festival", "Experience a culinary journey around the world! Sample diverse cuisines from different cultures, accompanied by live music and cultural performances.", "City Plaza", "03/02/2022", "5:00 PM - 9:00 PM"));
        events.add(new Event("Fitness Bootcamp Challenge", "Get ready to sweat in this high-intensity fitness challenge! Join us for a full-body workout led by expert trainers, suitable for all fitness levels.", "Central Park", "03/02/2022", "7:00 AM - 9:00 AM"));
//        events.add(new Event("Meow", "???", "cat cafe", "01/02/2022", "08:16"));
//        events.add(new Event("Java", "Terrible", "FSKTM", "04/02/2022", "17:45"));
//        events.forEach(e -> Resource.fileWriter(e, Resource.EventPath));
//        dateSort(events).forEach(System.out::println);
//        Parent p = new Parent("happy", "sadfasdf", as);
//        System.out.println(s3);
        Resource.fileWriter(s3, Resource.UserDataPath);

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
//        quizzes.forEach(event -> Resource.fileWriter(event, Resource.QuizPath));
//        System.out.println(Resource.quizLoader());
//        System.out.println(QuizFilterService.getFilteredQuizzes(Resource.quizLoader(), List.of(Quiz.Theme.SCIENCE, Quiz.Theme.MATHEMATICS)));
    }
}