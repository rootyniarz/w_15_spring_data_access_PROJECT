package pl.zajavka.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.OpinionRepository;
import pl.zajavka.domain.Opinion;
import pl.zajavka.domain.Purchase;
import pl.zajavka.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class OpinionDatabaseRepository implements OpinionRepository {

    public static final String DELETE_ALL = "DELETE FROM OPINION WHERE 1=1";
    private static final String DELETE_ALL_WHERE_CUSTOMER_EMAIL =
            "DELETE FROM OPINION WHERE CUSTOMER_ID IN" +
                    " (SELECT ID FROM CUSTOMER WHERE EMAIL = :email)";
    private static final String SELECT_ALL_WHERE_CUSTOMER_EMAIL = """
            SELECT * FROM PURCHASE AS OPN
            INNER JOIN CUSTOMER AS CUS ON CUS.ID=OPN.CUSTOMER_ID 
            WHERE CUS.EMAIL = :email
            ORDER BY DATE_TIME
            """;

    private final SimpleDriverDataSource simpleDriverDataSource;
    private final DatabaseMapper databaseMapper;

    @Override
    public Opinion create(Opinion opinion) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.OPINION_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.OPINION_TABLE_PKEY.toLowerCase());

//        Number opinionId = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(opinion));
        Map<String, ?> params = databaseMapper.mapCustomer(opinion);
        Number opinionId = jdbcInsert.executeAndReturnKey(params);

        return opinion.withId((long) opinionId.intValue());
    }

    @Override
    public void removeAll() {
        new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
    }

    @Override
    public void remove(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_ALL_WHERE_CUSTOMER_EMAIL, Map.of("email", email));
    }

    @Override
    public List<Opinion> findAll(String email) {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
            return jdbcTemplate.query(SELECT_ALL_WHERE_CUSTOMER_EMAIL, Map.of("email",email),databaseMapper::mapOpinion);
    }
}
