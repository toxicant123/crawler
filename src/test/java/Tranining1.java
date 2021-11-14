import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author toxicant123
 * @Description
 * @create 2021-11-14 21:11
 */
public class Tranining1 {
    public static void main(String[] args) throws IOException {
        String json = "jQuery3789730([{\"p\":\"1899.00\",\"op\":\"2299.00\",\"cbf\":\"0\",\"id\":\"J_100011473965\",\"m\":\"100000.00\"}]);";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode jsonNode1 = jsonNode.get(0);
        JsonNode p = jsonNode1.get("p");
        double v = p.asDouble();
        System.out.println(v);
    }
}
