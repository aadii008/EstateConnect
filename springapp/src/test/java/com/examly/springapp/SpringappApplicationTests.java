package com.examly.springapp;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.lang.reflect.Field;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
class SpringappApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void backend_testGetAllProperties() throws Exception {
        mockMvc.perform(get("/api/properties")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(print())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$").isArray())
            .andReturn();
    }

    @Test
    @Order(2)
    public void backend_testGetAllFeedback() throws Exception {
        mockMvc.perform(get("/api/feedback")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(print())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$").isArray())
            .andReturn();
    }

    @Test
    public void backend_testFeedbackHasManyToOneAnnotation() {
        try {
            Class<?> feedbackClass = Class.forName("com.examly.springapp.model.Feedback");

            Field[] declaredFields = feedbackClass.getDeclaredFields();

            boolean hasManyToOne = false;
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(jakarta.persistence.ManyToOne.class)) {
                    hasManyToOne = true;
                    break;
                }
            }

            if (!hasManyToOne) {
                fail("No field with @ManyToOne annotation found in Feedback class.");
            }

        } catch (ClassNotFoundException e) {
            fail("Class not found: " + e.getMessage());
        }
    }

    // @Test
    // public void backend_testPropertyHasManyToOneAnnotation() {
    //     try {
    //         Class<?> propertyClass = Class.forName("com.examly.springapp.model.Property");

    //         Field[] declaredFields = propertyClass.getDeclaredFields();

    //         boolean hasManyToOne = false;
    //         for (Field field : declaredFields) {
    //             if (field.isAnnotationPresent(jakarta.persistence.ManyToOne.class)) {
    //                 hasManyToOne = true;
    //                 break;
    //             }
    //         }

    //         if (!hasManyToOne) {
    //             fail("No field with @ManyToOne annotation found in Property class.");
    //         }

    //     } catch (ClassNotFoundException e) {
    //         fail("Class not found: " + e.getMessage());
    //     }
    // }

    @Test
    public void backend_testPropertyInterfaceAndImplementation() {
        try {
            Class<?> interfaceClass = Class.forName("com.examly.springapp.service.PropertyService");
            Class<?> implementationClass = Class.forName("com.examly.springapp.service.PropertyServiceImpl");

            assertTrue(interfaceClass.isInterface(), "The specified class is not an interface");
            assertTrue(interfaceClass.isAssignableFrom(implementationClass), "Implementation does not implement the interface");
        } catch (ClassNotFoundException e) {
            fail("Interface or implementation not found");
        }
    }

    private void checkClassExists(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " does not exist.");
        }
    }

    @Test
    public void backend_testFeedbackControllerClassExists() {
        checkClassExists("com.examly.springapp.controller.FeedbackController");
    }

    @Test
    public void backend_testPropertyControllerClassExists() {
        checkClassExists("com.examly.springapp.controller.PropertyController");
    }

    @Test
    public void backend_testAuthControllerClassExists() {
        checkClassExists("com.examly.springapp.controller.AuthController");
    }

    @Test
    public void backend_testFeedbackModelClassExists() {
        checkClassExists("com.examly.springapp.model.Feedback");
    }

    @Test
    public void backend_testPropertyModelClassExists() {
        checkClassExists("com.examly.springapp.model.Property");
    }

    @Test
    public void backend_testUserModelClassExists() {
        checkClassExists("com.examly.springapp.model.User");
    }
}
