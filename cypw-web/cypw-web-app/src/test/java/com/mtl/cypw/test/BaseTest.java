package com.mtl.cypw.test;

import com.mtl.cypw.web.TktStarWebAppBoot;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: tang
 * @Date: 2019-10-18 14:40
 */
@SpringBootTest(classes = {TktStarWebAppBoot.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"file:C:\\Users\\admin\\Desktop\\MTL\\cypw-global.properties","file:C:\\Users\\admin\\Desktop\\MTL\\cypw-tktstar.properties"})
public class BaseTest {

}
