package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dataaccess.TableData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents a CustomerAccount, which has all the transactions made by
 * a customer
 */
public class CustomerAccount {

    /**
     * List of Transactions in Rows
     */
    private List<TableData.Row> transactions;

    /**
     * Persistence class to have access to TableDataGateways
     */
    private Persistence persistence;

    /**
     * Constructor
     *
     * @param persistence Persistence with access to all Gateways
     */
    public CustomerAccount(Persistence persistence) {
        this.transactions = new ArrayList<>();
        this.persistence = persistence;
    }

    /**
     * Add Rows to list of transactions from the TableData ts
     * @param ts TableData with Rows of Transaction data
     */
    public void addTransactions(TableData ts) {
        Iterator<TableData.Row> iter = ts.iterator();
        while (iter.hasNext()) {
            TableData.Row row = iter.next();
            this.transactions.add(row);
        }
    }

    /**
     * Print the list of transactions
     * @return Textual representation of the list
     * @throws PersistenceException
     */
    public String print() throws PersistenceException {
        Transaction transaction = new Transaction(this.persistence);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (TableData.Row row : transactions) {
            i++;
            sb.append("\n" + i + ": " + transaction.print(row));
        }
        return sb.toString();
    }

    /**
     * Computes the total that the customer is due, by iterating the transactions
     * @return The total that the customer is due
     * @throws PersistenceException
     */
    public double computeTotal() throws PersistenceException {
        double sum = 0;
        for (TableData.Row row : this.transactions) {
            double value = this.persistence.transactionTableGateway.readValue(row);
            value *= this.persistence.transactionTableGateway.readType(row) == TransactionType.CREDIT ? 1 : -1;
            sum += value;
        }
        return sum;
    }

    /**
     * Print the textual representation of the row at index received from parameters
     * @param index The index where the row of the transaction we want to print is in
     * @return String that represents the transaction we want to print
     * @throws PersistenceException
     * @throws ApplicationException
     */
    public String printTransaction(int index) throws PersistenceException, ApplicationException {
        TableData.Row row = this.transactions.get(index - 1);
        TransactionType type = this.persistence.transactionTableGateway.readType(row);
        if (type == TransactionType.CREDIT) {
            return printCreditTransactionDetails(row);
        } else {
            return printDebitTransactionDetails(row);
        }
    }

    /**
     * Prints the Debit Transaction represented by the Row it receives in its parameters
     * @param row Row that represents the Transaction to be printed
     * @return String representation of the row
     * @throws PersistenceException
     * @throws ApplicationException
     */
    private String printDebitTransactionDetails(TableData.Row row) throws PersistenceException, ApplicationException {
        SaleProduct saleProduct = new SaleProduct(this.persistence);
        TableData td = saleProduct.getSaleProductsFromSale(this.persistence.transactionTableGateway.readSaleId(row));
        Iterator<TableData.Row> iter = td.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            sb.append(saleProduct.print(iter.next()));
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Prints the Credit Transaction represented by the Row it receives in its parameters
     * @param row Row that represents the Transaction to be printed
     * @return String representation of the row
     * @throws PersistenceException
     * @throws ApplicationException
     */
    private String printCreditTransactionDetails(TableData.Row row) throws PersistenceException, ApplicationException {
        Sale sale = new Sale(this.persistence);
        TableData td = sale.getSale(this.persistence.transactionTableGateway.readSaleId(row));
        return sale.print(td.iterator().next());
    }
}
