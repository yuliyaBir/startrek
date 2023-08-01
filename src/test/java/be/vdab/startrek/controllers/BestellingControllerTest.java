package be.vdab.startrek.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Sql({"/werknemers.sql", "/bestellingen.sql"})
@AutoConfigureMockMvc
class BestellingControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final MockMvc mockMvc;
    private final String WERKNEMERS = "werknemers";
    private final String BESTELLINGEN = "bestellingen";
    private final Path TEST_RESOURCES = Path.of("src/test/resources");


    public BestellingControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    private long idVanTest1Werknemer (){ return jdbcTemplate.queryForObject("select id from werknemers where voornaam = 'test1'", Long.class);}

    @Test
    void findByWerknemerId() throws Exception {
        var id = idVanTest1Werknemer();
        mockMvc.perform(get("/bestellingen/{werknemerId}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(countRowsInTableWhere(BESTELLINGEN, "werknemerId = " + id)));
    }

    @Test
    void create() throws Exception {
        var id = idVanTest1Werknemer();
        var jsonData = Files.readString(TEST_RESOURCES.resolve("correcteBestelling.json"));
        var responseBody = mockMvc.perform(post("/bestellingen/{werknemerId}/nieuweBestelling", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(
                        status().isOk()
                )
                .andReturn().getResponse().getContentAsString();
        assertThat(countRowsInTableWhere(BESTELLINGEN, "werknemerId = " + id + " and id = " + responseBody)).isOne();
        assertThat(countRowsInTableWhere(WERKNEMERS, "id = " + id + " and budget = " + 8));
    }
}