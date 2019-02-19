package string;

/**
 * 删除字符串末尾  *.0000
 */
public class Substring0InEnd {

    public static void main(String[] args) {
        System.out.println(SubstringRate("0.00"));
    }

    static String SubstringRate(String rate){
        int lastIndex = 0;
        for(int i = 0; i < rate.length(); i++){
            if(rate.charAt(i) > 48 && rate.charAt(i) < 58){
                lastIndex = i;
            }
        }
        return rate.substring(0,lastIndex+1);
    }
}
