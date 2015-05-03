import org.junit.Test;
import spittr.Spitter;
import spittr.Spittle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shawnritchie on 02/05/15.
 */
public class ContraCoVariance {

    public static void covariance(List<? extends Object> takesAnything) {
        return;
    }

    public static void contravariance(List<? super Serializable> takesAnything) {
        return;
    }

    @Test
    public void covarianceTest()
    {
        covariance(new ArrayList<String>() {{ add("test"); add("test1"); add("test2"); }});
    }

    @Test
    public void contravarianceTest()
    {
        contravariance(new ArrayList<Serializable>() {{
            add(new Spittle(1L, new Spitter(1L, "username", "password", "fullName", "test@gmail.com", true), "test", new Date()));
        }});
    }
}
