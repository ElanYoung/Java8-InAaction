import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringApples {
    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }

    public interface ApplePredicate {
        boolean test(Apple apple);
    }

    public interface AppleFormatter {
        String accept(Apple apple);
    }

    public interface Predicate<T> {
        boolean test(T t);
    }

    public class AppleHeavyWeightPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    public class AppleGreenColorPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }

    public static class AppleRedAndHeavyPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return "red".equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }

    public static class AppleFancyFormatter implements AppleFormatter {

        @Override
        public String accept(Apple apple) {
            String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
            return "A " + characteristic + " " + apple.getColor() + " apple";
        }
    }

    public static class AppleSimpleFormatter implements AppleFormatter {
        @Override
        public String accept(Apple apple) {
            return "An apple of " + apple.getWeight() + "g";
        }
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter formatter) {
        for (Apple apple : inventory) {
            String output = formatter.accept(apple);
            System.out.println(output);
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

        List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(redAndHeavyApples);

        prettyPrintApple(inventory, new AppleFancyFormatter());
        prettyPrintApple(inventory, new AppleSimpleFormatter());

        List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        System.out.println(redApples);

        List<Apple> result = filterApples(inventory, (Apple apple) -> "red".equals(apple.getColor()));
        System.out.println(result);

        List<Integer> numbers = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);
        System.out.println(evenNumbers);
    }
}
