
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Qualifier implements Serializable {

    private static final long serialVersionUID = 1L;

    private Serializable value;

    private String toStringText;

    private String containsText;

    private Qualifier(String toStringText) {
        this.toStringText = toStringText;
    }

    public static Serializable greateThan(Serializable i) {
        Qualifier q = new Qualifier("greaterThan " + i);
        q.value = i;
        return q;
    }

    public static Serializable lessThan(Serializable i) {
        Qualifier q = new Qualifier("lessThan " + i);
        q.value = i;
        return q;
    }

    public boolean isGreaterThan(Serializable v) {
        return invokeCompareTo(v) >= 1;
    }

    public boolean isLessThan(Serializable v) {
        return invokeCompareTo(v) <= -1;
    }

    public boolean isEqualTo(Serializable v) {
        return invokeCompareTo(v) == 0;
    }

//    public static boolean isContainsText(Serializable v) {
//        Qualifier q = new Qualifier("isContainsText " + v);
//        q.value = v;
//        return q;
//    }

    private int invokeCompareTo(Serializable v) {
        Class<?> c = v.getClass();
        try {
            Method main = c.getMethod("compareTo", Object.class);
            Object r = main.invoke(v, value);
            return (Integer) r;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    public static void main(String[] args) {
        Qualifier q = (Qualifier) Qualifier.greateThan(10.5);
        System.out.println(q.isGreaterThan(1.5));
        System.out.println(q.isEqualTo(10.5));
        System.out.println(q.isLessThan(10.0));
//      System.out.println(q.isContainsText("DDDD"));

    }

    @Override
    public boolean equals(Object obj) {
        
    }
    
    @Override
    public String toString() {
        return toStringText;
    }

    public static Serializable contains(String containsText) {
        Qualifier q = new Qualifier("containsText " + containsText);
        q.containsText = containsText;
        return q;
    }

}
