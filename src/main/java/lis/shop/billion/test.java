package lis.shop.billion;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;

@Data
public class test {
    @Value("${jwt.secret}")
    private String secretKey;

    public static void main(String[] args) {
        String s = "ROLE_CUSTOMER";
        System.out.println(parseRole(s));


    }

    // Удаляет ROLE_ из строки для токена, работает только, для формата из двух слов (напр. ROLE_CUSTOMER).
    private static String parseRole(String s) {
        String[] split = s.split("_");
        return split[1];
    }

    private static void parseRole() {
        test test = new test();
//        String secretKey = Arrays.toString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
        System.out.println(Base64.getEncoder().encodeToString("Global AuthenticationManager configured".getBytes()));
        System.out.println(test.getSecretKey());
    }
}
