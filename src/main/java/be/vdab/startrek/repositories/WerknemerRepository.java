package be.vdab.startrek.repositories;

import be.vdab.startrek.domain.Werknemer;
import be.vdab.startrek.exceptions.WerknemerNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class WerknemerRepository {
    private final JdbcTemplate jdbcTemplate;
    public WerknemerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Werknemer> mapper = (result, rowNum) -> new Werknemer(
            result.getLong("id"),
            result.getString("voornaam"),
            result.getString("familienaam"),
            result.getBigDecimal("budget")
    );
    public List<Werknemer> findAll(){
        var sql = """
                    select id, voornaam, familienaam, budget
                    from werknemers
                    order by voornaam
                    """;
        return jdbcTemplate.query(sql, mapper);
    }
    public Optional<Werknemer> findById(long id){
        try {
        var sql = """
                select id, voornaam, familienaam, budget
                from werknemers
                where id = ?
                """;
        return Optional.of(jdbcTemplate.queryForObject(sql, mapper, id));
        } catch(IncorrectResultSizeDataAccessException ex){
            return Optional.empty();
        }
    }
    public Optional<Werknemer> findEnLockById(long id){
        try {
            var sql = """
                select id, voornaam, familienaam, budget
                from werknemers
                where id = ?
                for update
                """;
            return Optional.of(jdbcTemplate.queryForObject(sql, mapper, id));
        } catch(IncorrectResultSizeDataAccessException ex){
            return Optional.empty();
        }
    }
    public void updateBudget(long id, BigDecimal bedrag){
        var sql = """
            update werknemers
            set budget = budget - ?
            where id = ?
            """;
        if (jdbcTemplate.update(sql, bedrag, id) == 0){
            throw new WerknemerNietGevondenException(id);
        }
    }
}
