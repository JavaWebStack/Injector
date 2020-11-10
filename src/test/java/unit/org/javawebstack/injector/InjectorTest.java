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

}
