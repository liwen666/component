import java.net.URL;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class TestPath {

    public static void main(String[] args) {
        String os = System.getProperty("os.name");
        URL resource = TestPath.class.getResource("");
        String dataPath = resource.getPath();
        if(os.toLowerCase().startsWith("win")){

            System.out.println("window  path" +dataPath);
        }else{
            System.out.println("系统路径"+""+dataPath);
        }
    }
}
