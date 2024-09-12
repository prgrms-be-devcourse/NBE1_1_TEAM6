import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        String[] date = LocalDateTime.now().toString().substring(0, 10).split("-");
        for(String s : date) System.out.println(s);
        String newDate = date[0].substring(2) + date[1] + date[2];
        System.out.println("date : " + newDate);

        UUID id1 = UUID.randomUUID();
        System.out.println("id1 : " + id1);
        String[] str = id1.toString().split("-");
        String uuid = newDate+ str[2] + str[1] + str[0] + str[3] +str[4];
        System.out.println("uuid : " + uuid);

    }
}