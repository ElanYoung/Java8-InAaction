import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    /**
     * 姓名
     */
    private String name;

    /**
     * 薪资
     */
    private Integer salary;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 地区
     */
    private String area;
}
