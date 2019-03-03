package string;

public class stepTransform {

    /**
     * 某业务格式转换convert
     */
    public static void main(String[] args) {
        String stepValueText = "价值0~11 政策值为15@价值12~23 政策值为18@价值>=24 政策值为11";
        String stepValueText1 = "0 ~999 -0%|1000 ~1999 -2%|2000 ~2999 -3%| >= 3000 -4% |";
        String stepValueText2 = "0 ~2099 -5%| >= 2100 -10% |";
        String stepValueText3 = "0 ~15 -15%| >= 16 -99% |";

//        transform1(stepValueText);
        transform2(stepValueText3);
    }

    private static void transform1(String stepValueText){
        System.out.println(">>>>>>>>>>start1");
        String[] values = stepValueText.replace(" ","").replace("价值","").replace(">=","").split("@");
        System.out.println(values.toString());

        for (String stepValue : values){

            String[] values2 = stepValue.split("政策值为");
            System.out.print(values2[0].split("~")[0] + "\t");
            System.out.println(values2[1]);
        }
        System.out.println("<<<<<<<<<<end1\n");
    }

    private static void transform2(String stepValueText){
        System.out.println(">>>>>>>>>>start2");
        String newStepValues =  "价值" + stepValueText.replace(" ","").replace("|","@价值").replace("-"," 政策值为");
        newStepValues = newStepValues.substring(0, newStepValues.length()-3);
        System.out.println(newStepValues);

        String[] values = newStepValues.replace(" ","").replace("价值","").replace(">=","").split("@");
        System.out.println(values.toString());

        for (String stepValue : values){

            String[] values2 = stepValue.split("政策值为");
            System.out.print(values2[0].split("~")[0] + "\t");
            System.out.println(values2[1]);
        }
        System.out.println("<<<<<<<<<<end2\n");
    }
}
