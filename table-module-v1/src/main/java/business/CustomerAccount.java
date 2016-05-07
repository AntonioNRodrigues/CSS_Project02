package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dataaccess.TableData;
import dataaccess.TransactionTableDataGateway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerAccount {

    private List<TableData.Row> transactions;
    private Persistence persistence;

    public CustomerAccount(Persistence persistence) {
        this.transactions = new ArrayList<>();
        this.persistence = persistence;
    }

    public void addTransactions(TableData ts) {
        Iterator<TableData.Row> iter = ts.iterator();
        while (iter.hasNext()) {
            TableData.Row row = iter.next();
            this.transactions.add(row);
        }
    }

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

    public double computeTotal() throws PersistenceException {
        double sum = 0;
        for (TableData.Row row : this.transactions) {
            double value = this.persistence.transactionTableGateway.readValue(row);
            value *= this.persistence.transactionTableGateway.readType(row) == TransactionType.CREDIT ? 1 : -1;
            sum += value;
        }
        return sum;
    }

    public String printTransaction(int index) throws PersistenceException, ApplicationException {
        TableData.Row row = this.transactions.get(index - 1);
        TransactionType type = this.persistence.transactionTableGateway.readType(row);
        if (type == TransactionType.CREDIT) {
            return printCreditTransactionDetails(row);
        } else {
            return printDebitTransactionDetails(row);
        }
    }

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

    private String printCreditTransactionDetails(TableData.Row row) throws PersistenceException, ApplicationException {
        Sale sale = new Sale(this.persistence);
        TableData td = sale.getSale(this.persistence.transactionTableGateway.readSaleId(row));
        return sale.print(td.iterator().next());
    }
}
