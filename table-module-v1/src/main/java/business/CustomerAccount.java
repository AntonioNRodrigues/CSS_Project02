package business;

import dataaccess.PersistenceException;
import dataaccess.TableData;
import dataaccess.TransactionTableDataGateway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerAccount {

    private List<TableData.Row> transactions;
    private TransactionTableDataGateway transactionDataGateway;

    public CustomerAccount(TransactionTableDataGateway transactionDataGateway) {
        this.transactions = new ArrayList<>();
        this.transactionDataGateway = transactionDataGateway;
    }

    public void addTransactions(TableData ts) {
        Iterator<TableData.Row> iter = ts.iterator();
        while (iter.hasNext()) {
            TableData.Row row = iter.next();
            this.transactions.add(row);
        }
    }

    public String print() throws PersistenceException {
        StringBuilder sb = new StringBuilder();
        sb.append("List of Transactions:");
        int i = 0;
        for (TableData.Row row : transactions) {
            i++;
            sb.append("\n" + i + ": " + this.transactionDataGateway.print(row));
        }
        sb.append("\n");
        sb.append("Your total is: " + computeTotal());
        sb.append("\n");
        sb.append("Which one do you want to see?");

        return sb.toString();
    }

    private double computeTotal() throws PersistenceException {
        double sum = 0;
        for (TableData.Row row : transactions) {
            double value = this.transactionDataGateway.readValue(row);
            value *= this.transactionDataGateway.readType(row) == TransactionType.CREDIT ? 1 : -1;
            sum += value;
        }
        return sum;
    }
}
