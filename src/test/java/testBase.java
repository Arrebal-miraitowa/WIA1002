import com.weijie.core.common.PasswordHashing;

import java.util.ArrayList;

public class testBase {
    public static void main(String[] args) {
//        ArrayList<Integer> al = null;
//        al.clear();
//        IntStream.range(0, 0).forEach(System.out::println);
        Stu t = new Stu("jack", 21);
        ArrayList<Stu> arrayList = new ArrayList<>();
        arrayList.add(t);
//        Stu s = arrayList.get(0);
//        s.setAge(12);
//        arrayList.forEach(stu -> stu.setAge(12));
//        arrayList.forEach(System.out::println);

        String hashedPassword1 = PasswordHashing.get("Anxious0");
        String hashedPassword2= PasswordHashing.get("Anxious0");
        System.out.println(hashedPassword1);
        System.out.println(hashedPassword2);
    }
}
