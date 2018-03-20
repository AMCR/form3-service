package form3.service;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AppServiceTest {

    @Test
    public void checkMath(){
        assertThat(1+1, equalTo(2));
    }
}
