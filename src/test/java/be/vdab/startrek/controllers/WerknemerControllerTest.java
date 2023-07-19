package be.vdab.startrek.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("/werknemers.sql")
@AutoConfigureMockMvc

class WerknemerControllerTest extends AbstractTransactionalJUnit4SpringContextTests {


    private final MockMvc mockMvc;
    public WerknemerControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    private final static String WERKNEMERS = "werknemers";

    private long idVanTest1Werknemer (){
        return jdbcTemplate.queryForObject("select id from werknemers where voornaam = 'test1'", Long.class);
    }

    @Test
    void findall() throws Exception {
        mockMvc.perform(get("/werknemers"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(countRowsInTable(WERKNEMERS)));
    }
}