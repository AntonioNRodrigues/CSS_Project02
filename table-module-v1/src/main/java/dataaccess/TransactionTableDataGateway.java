package dataaccess;

import business.TransactionType;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionTableDataGateway extends TableDataGateway {

    /**
     * Columns' labels
     */
    private static final String ID = "ID";
    private static final String SALE_ID = "SALE_ID";
    private static final String TYPE = "TRANSACTION_TYPE";
    private static final String VALUE = "TRANSACTION_VALUE";
    private static final String CREATED_AT = "CREATED_AT";

    /**
     * Strings used to register the types of a transaction
     */
    private static final int CREDIT = 1;
    private static final int DEBIT = 0;

    TransactionTableDataGateway(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * The insert sale SQL statement
     */
    private static final String INSERT_TRANSACTION_SQL =
            "insert into sale (id, " + SALE_ID + ", " + TYPE + ", " + VALUE + ", " +
                    CREATED_AT + ") " +
                    "values (DEFAULT, ?, ?, ?, ?)";

    public int newTransaction(int saleId, double value, Date date, String description, TransactionType type) throws PersistenceException {
        try (PreparedStatement statement = dataSource.prepareGetGenKey(INSERT_TRANSACTION_SQL)) {
            // set statement arguments
            statement.setInt(1, saleId);
            statement.setInt(2, type == TransactionType.CREDIT ? CREDIT : DEBIT);
            statement.setDouble(3, value);
            statement.setDate(4, date);

            // execute SQL
            statement.executeUpdate();
            // Gets sale Id generated automatically by the database engine
            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new PersistenceException ("Internal error inserting a new sale", e);
        }
    }
}
