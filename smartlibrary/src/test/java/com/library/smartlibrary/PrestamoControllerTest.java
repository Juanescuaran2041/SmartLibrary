package com.library.smartlibrary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PrestamoControllerTest {

    @Autowired
    MockMvc mvc;

    private ResultActions prestar(long estudianteId, long libroId) throws Exception {
        return mvc.perform(post("/api/prestamos").contentType(MediaType.APPLICATION_JSON)
                .content("{\"estudianteId\":" + estudianteId + ",\"libroId\":" + libroId + "}"));
    }

    @Test
    void prestaLibroDisponible() throws Exception {
        prestar(1, 1).andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists());
    }

    @Test
    void rechazaLibroYaPrestado() throws Exception {
        prestar(1, 2).andExpect(status().isCreated());
        prestar(2, 2).andExpect(status().isConflict()).andExpect(jsonPath("$.error").exists());
    }

    @Test
    void estudianteInexistente() throws Exception {
        prestar(999, 3).andExpect(status().isNotFound());
    }

    @Test
    void libroInexistente() throws Exception {
        prestar(1, 999).andExpect(status().isNotFound());
    }

    @Test
    void consultaPrestamos() throws Exception {
        prestar(1, 1).andExpect(status().isCreated());
        mvc.perform(get("/api/prestamos")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").exists());
    }
}
