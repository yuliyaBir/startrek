package be.vdab.startrek.repositories;

import be.vdab.startrek.domain.Bestelling;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BestellingRepository {
    private final JdbcTemplate jdbcTemplate;

    public BestellingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Bestelling> bestellingMapper = (result, rowNum) -> new Bestelling(
            result.getLong("id"),
            result.getLong("werknemerId"),
            result.getString("omschrijving"),
            result.getBigDecimal("bedrag"),
            result.getObject("moment", LocalDateTime.class));


    public List<Bestelling> findByWerknemerId(long werknemerId){
        var sql = """
                select id, werknemerId, omschrijving, bedrag, moment
                from bestellingen
                where werknemerId = ?
                order by id
                """;
        return jdbcTemplate.query(sql, bestellingMapper, werknemerId);
    }

    public long create(Bestelling bestelling) {
        var sql = """
                insert into bestellingen (werknemerId, omschrijving, bedrag, moment)
                values (?, ?, ?, ?)
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, bestelling.getWerknemerId());
            statement.setString(2, bestelling.getOmschrijving());
            statement.setBigDecimal(3, bestelling.getBedrag());
            statement.setObject(4, bestelling.getMoment());
            return statement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
