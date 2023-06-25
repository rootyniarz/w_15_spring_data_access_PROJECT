package pl.zajavka.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.ProductRepository;
import pl.zajavka.domain.Product;
import pl.zajavka.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class ProductDatabaseRepository implements ProductRepository {

    private static final String SELECT_ALL="SELECT * FROM PRODUCT";
    private static final String DELETE_ALL="DELETE FROM PRODUCT WHERE 1=1";
    private static final String SELECT_WHERE_PRODUCT_CODE = """
            SELECT * FROM PRODUCT
            WHERE PRODUCT_CODE=:productCode
            """;
    private final SimpleDriverDataSource simpleDriverDataSource;
    private final DatabaseMapper databaseMapper;

    @Override
    public Product create(Product product) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.PRODUCT_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.PRODUCT_TABLE_PKEY.toLowerCase());

//        Number productId = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(product));
        Map<String,?> params = databaseMapper.mapCustomer(product);
        Number productId = jdbcInsert.executeAndReturnKey(params);

        return product.withId((long) productId.intValue());
    }
    @Override
    public List<Product> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL, databaseMapper::mapProduct);
    }

    @Override
    public void removeAll() {
        new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
    }

    @Override
    public Optional<Product> find(String productCode) {
        final var jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);

        Map<String, Object> params = Map.of("productCode", productCode);
        RowMapper<Product> rowMapper = (resultSet, rowNum) -> databaseMapper.mapProduct(resultSet, rowNum);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_WHERE_PRODUCT_CODE, params, rowMapper));
        } catch (Exception e)
        {
            log.warn("Trying to find non-existing product: [{}]",productCode);
            return Optional.empty();
        }
    }
}
