import java.util.Date;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/25  21:04
 */
public class Test {

    @org.junit.Test
    public void name() {
        Date parse = DateUtils.parse("2021-03-25 21:00:19");
        System.out.println(parse.getTime());
    }

    @org.junit.Test
    public void name2() {
        Date d = new Date(1616677219859l);
        String format = DateUtils.format(d, DateUtils.YYYY_MM_DD_HH_MM_SS);
        System.out.println(format);
    }

    @org.junit.Test
    public void name3() {
        Date d = new Date(1616677984142l);
        String format = DateUtils.format(d, DateUtils.YYYY_MM_DD_HH_MM_SS);
        System.out.println(format);
    }
}
