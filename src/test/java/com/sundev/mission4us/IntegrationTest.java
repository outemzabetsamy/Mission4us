package com.sundev.mission4us;

import com.sundev.mission4us.Mission4UsApp;
import com.sundev.mission4us.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { Mission4UsApp.class, TestSecurityConfiguration.class })
public @interface IntegrationTest {
}
