import org.junit.Test;
import org.springframework.util.AntPathMatcher;

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

    @Test
    public void antmatch() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
//        boolean aaa = antPathMatcher.match("a", "aaa");
//        System.out.println(aaa);

        System.out.println(antPathMatcher.match("/**/list/**","/jkjl/jjj/lis"));

    }

    @Test
    public void name() {
        String category="meta_category@OBJECT_EVENT@默认@meta_category@系统";
        String[] split = category.split("meta_category@");
        System.out.println(split[0]);
        System.out.println(split[1]);
        System.out.println(split[2]);
        System.out.println(category.replaceAll("meta_category@","").replaceAll("@","/"));
    }
}
