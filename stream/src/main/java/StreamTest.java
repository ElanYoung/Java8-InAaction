import java.util.*;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(7, 6, 9, 4, 11, 6);
        List<Person> personList = initPersonList();
        List<String> strList = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        traverseStreamTest(intList);
        filterStreamTest(personList);
        polymerizeStreamTest(intList, strList, personList);
        reduceStreamTest(intList, personList);
        countStreamTest(personList);
        joiningStreamTest(personList);
        mapStreamTest(personList);
        sortStreamTest(personList);
    }

    /**
     * 初始化员工数据
     *
     * @return 数据
     */
    public static List<Person> initPersonList() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7900, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));
        personList.add(new Person("Sherry", 9000, 24, "female", "New York"));
        return personList;
    }

    /**
     * 遍历/匹配：（foreach/find/match）
     * <p>
     * 筛选出Integer集合中大于7的元素，并打印出来
     *
     * @param intList 数值列表
     */
    public static void traverseStreamTest(List<Integer> intList) {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> first = list.stream().filter(x -> x > 6).findFirst();
        // 匹配任意（适用于并行流）
        Optional<Integer> any = list.parallelStream().filter(x -> x > 6).findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x > 6);
        System.out.println("匹配第一个值：" + first.get());
        System.out.println("匹配任意一个值：" + any.get());
        System.out.println("是否存在大于6的值：" + anyMatch);
    }

    /**
     * 筛选：员工中工资高于8000的人，并形成新的集合
     *
     * @param personList 员工列表
     */
    public static void filterStreamTest(List<Person> personList) {
        List<String> filterList = personList.stream()
                .filter(x -> x.getSalary() > 8000)
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("工资高于8000的员工姓名：" + filterList);
    }

    /**
     * 聚合：（max/min/count)
     *
     * @param personList 员工列表
     */
    public static void polymerizeStreamTest(List<Integer> intList, List<String> strList, List<Person> personList) {
        System.out.println("获取String集合中最长的元素");

        Optional<String> maxStr = strList.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + maxStr.get());

        System.out.println("获取Integer集合中的最大值");
        Optional<Integer> maxInt = intList.stream().max(Integer::compareTo);
        System.out.println("最大值：" + maxInt.get());

        System.out.println("获取员工工资最高的人");
        Optional<Person> maxSalary = personList.stream().max(Comparator.comparing(Person::getSalary));
        System.out.println("员工工资最大值：" + maxSalary.get().getSalary());

        System.out.println("计算Integer集合中大于6的元素个数");
        long count = intList.stream().filter(x -> x > 6).count();
        System.out.println("大于6的元素个数：" + count);
    }

    /**
     * 归约
     * <p>
     * 求Integer集合的元素之和、乘积和最大值
     * <p>
     * 求所有员工的工资之和最高工资
     *
     * @param personList 员工列表
     */
    public static void reduceStreamTest(List<Integer> intList, List<Person> personList) {
        System.out.println("求Integer集合的元素之和、乘积和最大值");
        Integer sum = intList.stream().reduce(0, Integer::sum);
        Optional<Integer> product = intList.stream().reduce((x, y) -> x * y);
        Integer max = intList.stream().reduce(1, Integer::max);
        System.out.println("intList求和：" + sum);
        System.out.println("intList求积：" + product.get());
        System.out.println("intList求和：" + max);

        System.out.println("求所有员工的工资之和最高工资");
        Optional<Integer> sumSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        // 求最高工资
        Integer salary = personList.stream()
                .reduce(0, (s, p) -> s > p.getSalary() ? s : p.getSalary(), Integer::max);
        Optional<Integer> salaryByMax = personList.stream().map(Person::getSalary).max(Integer::compare);
        System.out.println("工资之和：" + sumSalary.get());
        System.out.println("最高工资：" + salaryByMax.get());
    }

    /**
     * 统计员工人数、平均工资、工资总额、最高工资
     *
     * @param personList 员工列表
     */
    public static void countStreamTest(List<Person> personList) {
        System.out.println("统计员工人数、平均工资、工资总额、最高工资");
        // 求总数
        long employeeCount = personList.size();
        // 求平均工资
        Double averageSalary = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        // 求工资之和
        int salaryBySum = personList.stream().mapToInt(Person::getSalary).sum();
        // 一次性统计所有信息
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));
        System.out.println("员工总数：" + employeeCount);
        System.out.println("员工平均工资：" + averageSalary);
        System.out.println("员工工资总和：" + salaryBySum);
        System.out.println("员工工资所有统计：" + collect);
    }

    /**
     * 分组：partitioningBy/groupingBy
     * <p>
     * 将员工按薪资是否高于8000分组
     * <p>
     * 将员工按性别和地区分组
     *
     * @param personList 员工列表
     */
    public static void mapStreamTest(List<Person> personList) {
        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> part = personList.stream()
                .collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));
        // 将员工按性别分组
        Map<String, List<Person>> sexGroup = personList.stream().collect(Collectors.groupingBy(Person::getSex));
        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Person>>> sexAndAreaGroup = personList.stream()
                .collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + sexGroup);
        System.out.println("员工按性别、地区：" + sexAndAreaGroup);
    }

    /**
     * 接合：输出所有员工姓名
     *
     * @param personList 员工列表
     */
    public static void joiningStreamTest(List<Person> personList) {
        String names = personList.stream().map(Person::getName)
                .collect(Collectors.joining(","));
        System.out.println("所有员工的姓名：" + names);
    }

    /**
     * 排序
     *
     * @param personList 员工列表
     */
    public static void sortStreamTest(List<Person> personList) {
        // 按工资增序排序
        List<String> sortBySalaryAsc = personList.stream()
                .sorted(Comparator.comparing(Person::getSalary))
                .map(Person::getName)
                .collect(Collectors.toList());
        // 按工资倒序排序
        List<String> sortBySalaryDesc = personList.stream()
                .sorted(Comparator.comparing(Person::getSalary).reversed())
                .map(Person::getName)
                .collect(Collectors.toList());
        // 先按工资再按年龄自定义排序（从大到小）
        List<String> sortBySalaryCustom = personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary().equals(p2.getSalary())) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Person::getName).collect(Collectors.toList());
        System.out.println("按工资自然排序：" + sortBySalaryAsc);
        System.out.println("按工资降序排序：" + sortBySalaryDesc);
        System.out.println("先按工资再按年龄自定义降序排序：" + sortBySalaryCustom);
    }
}