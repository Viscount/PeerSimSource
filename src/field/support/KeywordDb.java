package field.support;

/**
 * Created by TongjiSSE on 2015/8/12.
 */
public class KeywordDb {

    private static int num_keyword;

    public static int getNum_keyword() {
        return num_keyword;
    }

    public static void init(){
    }

    public static void generate(int num){
        num_keyword = num;
    }

}
