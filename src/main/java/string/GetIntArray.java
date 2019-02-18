package string;

import java.util.ArrayList;
import java.util.List;

public class GetIntArray {
    public static void main(String[] args) {
        String ss = "9.0ab23cdef14ghighij567klmno8pqrst90uvwxyz12";

        System.out.println(getIntArray(ss).toString());
    }

    static List<Integer> getIntArray(String remark){
        StringBuilder sb = new StringBuilder();
        int lastIndex = -2;

        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < remark.length(); i++){
            if(remark.charAt(i) > 47 && remark.charAt(i) < 58){
                lastIndex = i;
                sb.append(remark.charAt(i));
            }else {
                if(lastIndex + 1 == i){
                    result.add(Integer.valueOf(sb.toString()));
                }
                sb = new StringBuilder();
            }
        }
        if(lastIndex + 1 == remark.length()){
            result.add(Integer.valueOf(sb.toString()));
        }

        return result;
    }
}
