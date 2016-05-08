package dataaccess;

import business.TransactionType;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataaccess.TableData.Row;

/**
 * Table Data Gateway for the Transactions table
 */
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
    private static final int DEBIT = -1;

    /**
     *  Constructor
     * @param dataSource Datasource
     */
    TransactionTableDataGateway(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * The insert sale SQL statement
     */
    private static final String INSERT_TRANSACTION_SQL =
            "insert into sale_transaction (" + ID + ", " + SALE_ID + ", " + TYPE + ", " + VALUE + ", " +
                    CREATED_AT + ") " +
                    "values (DEFAULT, ?, ?, ?, ?)";

    /**
     * Inserts a new Transaction in the DB
     * @param saleId Sale Id that the Transaction will be associated with
     * @param value The value of the Transaction
     * @param type Type of Transaction
     * @return Returns the new Id of the inserted Transaction
     * @throws PersistenceException
     */
    public int newTransaction(int saleId, double value, TransactionType type) throws PersistenceException {
        try (PreparedStatement statement = dataSource.prepareGetGenKey(INSERT_TRANSACTION_SQL)) {
            // set statement arguments
            statement.setInt(1, saleId);
            statement.setInt(2, type == TransactionType.CREDIT ? CREDIT : DEBIT);
            statement.setDouble(3, value);
            statement.setDate(4, new Date(new java.util.Date().getTime()));

            // execute SQL
            statement.executeUpdate();

            // Gets transaction Id generated automatically by the database engine
            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new PersistenceException ("Internal error inserting a new Transaction", e);
        }
    }

    /**
     * The get all transactions by sale_id SQL statement
     */
    private static final String GET_ALL_TRANSACTIONS_FROM_SALE_ID_SQL =
            "select * from sale_transaction where " + SALE_ID + " = ?";

    /**
     * Get all Transactions from a Sale, with Id saleId
     * @param saleId The Id of the Sale that is associated with the Transactions to be returned
     * @return TableData with Rows that represent Transactions
     * @throws PersistenceException
     */
    public TableData getAllTransactions(int saleId) throws PersistenceException {
        try (PreparedStatement statement = dataSource.prepareGetGenKey(GET_ALL_TRANSACTIONS_FROM_SALE_ID_SQL)) {
            // set statement arguments
            statement.setInt(1, saleId);

            // Gets all transactions
            try (ResultSet rs = statement.executeQuery()) {
                TableData td = new TableData();
                td.populate(rs);
                return td;
            }
        } catch (SQLException e) {
            throw new PersistenceException ("Internal error getting all transaction ids", e);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads the ID property of a Row
     * @param row Row that has the Transaction data
     * @return the Id of the Transaction
     * @throws PersistenceException
     */
    public int readId(Row row) throws PersistenceException {
        return row.getInt(ID);
    }

    /**
     * Reads the Sale ID property of a Row
     * @param row Row that has the Transaction data
     * @return the Sale Id of the Transaction
     * @throws PersistenceException
     */
    public int readSaleId(Row row) throws PersistenceException {
        return row.getInt(SALE_ID);
    }

    /**
     * Reads the TransactionType property of a Row
     * @param row Row that has the Transaction data
     * @return the TransactionType of the Transaction
     * @throws PersistenceException
     */
    public TransactionType readType(Row row) throws PersistenceException {
        return row.getInt(TYPE) == CREDIT ? TransactionType.CREDIT : TransactionType.DEBIT;
    }

    /**
     * Reads the Date of createdAt property of a Row
     * @param row Row that has the Transaction data
     * @return the Data of when the Transaction was created
     * @throws PersistenceException
     */
    public Date readCreatedAt(Row row) throws PersistenceException {
        return row.getDate(CREATED_AT);
    }

    /**
     * Reads the value property of a Row
     * @param row Row that has the Transaction data
     * @return the value of the Transaction
     * @throws PersistenceException
     */
    public double readValue(Row row) throws PersistenceException {
        return row.getDouble(VALUE);
    }
}
