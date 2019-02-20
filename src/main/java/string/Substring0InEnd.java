package string;

/**
 * 删除字符串末尾  *.0000
 */
public class Substring0InEnd {

    public static void main(String[] args) {
        System.out.println(SubstringRate("1000.00"));
    }

    static String SubstringRate(String rate){
        int lastIndex = 0;
        for(int i = 0; i < rate.length(); i++){
            //标记最后一个非零数位置
            if(rate.charAt(i) > 48 && rate.charAt(i) < 58){
                lastIndex = i;
            }
            // *0.00000 的情况
            if(rate.charAt(i) == 46){
                lastIndex = i - 1;
            }
        }
        return rate.substring(0,lastIndex+1);
    }
}
