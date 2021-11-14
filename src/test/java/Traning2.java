import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author toxicant123
 * @Description
 * @create 2021-11-14 21:33
 */
public class Traning2 {
    public static void main(String[] args) {
        String s = "jQuery3789730([{\"p\":\"1899.00\",\"op\":\"2299.00\",\"cbf\":\"0\",\"id\":\"J_100011473965\",\"m\":\"100000.00\"}]);\n";
//        String s = "4444";
        Pattern compile = Pattern.compile("(\"p\":\")(\\d{4})");
        Matcher matcher = compile.matcher(s);
        boolean b = matcher.find();
        String group = matcher.group(2);
        System.out.println(group);
    }
}
