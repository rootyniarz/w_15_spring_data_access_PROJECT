package pl.zajavka.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.PurchaseRepository;
import pl.zajavka.domain.Purchase;
import pl.zajavka.infrastructure.configuration.DatabaseConfiguration;

@Slf4j
@Repository
@AllArgsConstructor
public class PurchaseDatabaseRepository implements PurchaseRepository {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Override
    public Purchase create(Purchase purchase) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.PURCHASE_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.PURCHASE_TABLE_PKEY.toLowerCase());

        Number purchaseId = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(purchase));
        return purchase.withId((long) purchaseId.intValue());
    }
}
