import lombok.Data;

/**
 * @ClassName PACKAGE_NAME.PjCls
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/13 20:21
 * @Version 1.0
 **/
@Data
public class PjCls {
    private Integer id;
    private String name;

    public static void main(String[] args) {
        PjCls pjCls = new PjCls();
        pjCls.setId(101);
        pjCls.setName("abc123");
        System.out.println(pjCls.toString());
    }
}

