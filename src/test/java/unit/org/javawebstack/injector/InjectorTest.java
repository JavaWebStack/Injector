package unit.org.javawebstack.injector;

import org.javawebstack.injector.Inject;
import org.javawebstack.injector.SimpleInjector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InjectorTest {

    @Test
    public void testSimpleInjection(){
        SimpleInjector injector = new SimpleInjector();
        injector.setInstance(String.class, "Test");

        Test1 test = new Test1();
        injector.inject(test);

        assertNotNull(test.getA());
        assertEquals("Test", test.getA());
    }

    @Test
    public void testParentInjection(){
        SimpleInjector injector = new SimpleInjector();
        injector.setInstance(String.class, "Test");

        Test1 test = new Test1();
        injector.inject(test);

        assertNotNull(test.getB());
        assertEquals("Test", test.getB());
    }

    @Test
    public void testConstructorInjection() {
        SimpleInjector injector = new SimpleInjector();
        injector.setInstance(String.class, "Test");
        injector.setInstance(String.class, "named", "NamedTest");
        injector.setInstance(Integer.class, 1337);

        Test3 test = injector.make(Test3.class);

        assertEquals(test.getTest(), "Test");
        assertEquals(test.getNamedTest(), "NamedTest");
        assertEquals(test.getFieldInjection(), 1337);

        Test4 zArgsConstructorTest = injector.make(Test4.class);
        assertEquals(zArgsConstructorTest.getTest(), "Test");
    }

    private static class Test1 extends Test2 {
        @Inject
        private String a;
        public String getA() {
            return a;
        }
    }

    private static class Test2 {
        @Inject
        private String b;
        public String getB() {
            return b;
        }
    }

    private static class Test3 {
        private String test;
        private String namedTest;
        @Inject
        private Integer fieldInjection;

        @Inject
        public Test3(String test, @Inject("named") String namedTest) {
            this.test = test;
            this.namedTest = namedTest;
        }

        public String getTest() {
            return test;
        }

        public String getNamedTest() {
            return namedTest;
        }

        public Integer getFieldInjection() {
            return fieldInjection;
        }
    }

    private static class Test4 {
        @Inject
        private String test;

        public String getTest() {
            return test;
        }
    }
}
