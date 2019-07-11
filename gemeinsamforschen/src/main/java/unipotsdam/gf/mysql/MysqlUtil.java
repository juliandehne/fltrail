package unipotsdam.gf.mysql;

import java.util.List;

public class MysqlUtil {

    public String createConcatenatedString(List<String> stringList) {
        String result = "";
        for (String s : stringList) {
            result += "'"+s + "',";
        }
        result = result.substring(0, result.length() -1);
        return result;
    }
}
