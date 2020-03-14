package com.hammadltd.mainservicetest;

import com.hammadltd.mainservice.App;
import com.hammadltd.mainservice.controllers.MainController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=App.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    @Test
    void homePage() {
        MainController mainController = new MainController();
        String result = mainController.homePage();

        String expected = "Hey there! Try this endpoint: POST /info";
        System.out.println("Returned: " + result);

        assertNotNull(result);
        assertEquals(expected, result.replace("\n", ""));
    }
}