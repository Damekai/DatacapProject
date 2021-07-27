package com.dbrocato.datacapproject;

import com.dbrocato.datacapproject.paymentprocessor.PaymentProcessor;
import com.dbrocato.datacapproject.paymentprocessor.PaymentProcessorRegistry;
import com.dbrocato.datacapproject.transaction.Transaction;
import com.dbrocato.datacapproject.transaction.TransactionType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class XMLProcesser
{
    public static List<Document> openXMLFiles(String... filenames)
    {
        List<Document> infiles = new ArrayList<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        // Transform filenames to a list of files.
        List<File> files = Arrays.stream(filenames).map(File::new).collect(Collectors.toList());

        try
        {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            for (File file : files)
            {
                /* Verify that the file exist and are readable. If it's not, an error message will be printed to the
                console and the file will be removed from processing. */

                if (file.exists() && file.canRead())
                {
                    Document infile = documentBuilder.parse(file);
                    infile.getDocumentElement().normalize();

                    infiles.add(infile);
                }
                else
                {
                    System.out.println("WARN: File " + file.getName() + " either does not exist or cannot be read. Its contents will be ignored.");
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException exception)
        {
            exception.printStackTrace();
        }

        return infiles;
    }

    public static List<Transaction> parseXMLFiles(List<Document> infiles)
    {
        List<Transaction> transactions = new ArrayList<>();

        for (Document infile : infiles)
        {
            NodeList fileTransactions = infile.getElementsByTagName("tran");

            for (int i = 0; i < fileTransactions.getLength(); i++)
            {
                Node node = fileTransactions.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element fileTransaction = (Element) node;

                    if (isValidTransactionFormat(fileTransaction, i))
                    {
                        TransactionType type = fileTransaction.getElementsByTagName("type").item(0).getTextContent().equalsIgnoreCase("sale") ? TransactionType.SALE : TransactionType.VOID;
                        float amount = Float.parseFloat(fileTransaction.getElementsByTagName("amount").item(0).getTextContent());
                        int referenceNumber = Integer.parseInt(fileTransaction.getElementsByTagName("refNo").item(0).getTextContent());

                        transactions.add(new Transaction(type, amount, referenceNumber));
                    }
                }
            }
        }

        return transactions;
    }

    /**
     * Separates the Sales from the Voids, and cross-references them to enact the valid voids and ignore the invalid
     * ones.
     */
    public static List<Transaction> processVoids(List<Transaction> transactions)
    {
        // Split transactions into two lists, one for each type.
        List<Transaction> sales = transactions.stream().filter((transaction) -> transaction.getType() == TransactionType.SALE).collect(Collectors.toList());
        List<Transaction> voids = transactions.stream().filter((transaction) -> transaction.getType() == TransactionType.VOID).collect(Collectors.toList());

        // Translate list of sales into a map (reference number -> transaction) for faster future access.
        Map<Integer, Transaction> salesMap = sales.stream().collect(Collectors.toMap(Transaction::getReferenceNumber, (transaction) -> transaction));

        // For each void transaction, use its reference number to access the appropriate sale.
        for (Transaction voidTransaction : voids)
        {
            int referenceNumber = voidTransaction.getReferenceNumber();
            Transaction saleTransaction = salesMap.get(referenceNumber);
            // If the transaction amounts match, proceed with the void.
            if (voidTransaction.getAmount() == saleTransaction.getAmount())
            {
                salesMap.remove(referenceNumber);
            }
            // If the transaction amounts don't match, provide a descriptive error and ignore the void.
            else
            {
                System.out.println("WARN: Void for transaction reference number " + referenceNumber + " ignored; void amount does not match sale amount.");
            }
        }

        return new ArrayList<>(salesMap.values());
    }

    /**
     * Accepts a list of transactions and runs that list through each Payment Processor to determine the net fees
     * incurred by each one.
     */
    public static String calculateProcessorFees(List<Transaction> transactions)
    {
        // Run all transactions through each Payment Processor and determine the net fees incurred by each one.
        Map<String, Float> processorFees = new HashMap<>();
        PaymentProcessorRegistry.PAYMENT_PROCESSORS.forEach((paymentProcessor) -> processorFees.put(paymentProcessor.getName(), paymentProcessor.processTransactions(transactions)));

        // Sort the map entries in decreasing value order to generate a ranking.
        List<Map.Entry<String, Float>> rankedProcessorFees = processorFees.entrySet().stream()
                .sorted((left, right) -> Float.compare(right.getValue(), left.getValue()))
                .collect(Collectors.toList());

        // Build the result as a string to be returned for GUI display.
        StringBuilder result = new StringBuilder();
        rankedProcessorFees.forEach((entry) ->
        {
            result.append(entry.getKey())
                    .append(": $")
                    .append(PaymentProcessor.USD_DECIMAL_FORMAT.format(entry.getValue()))
                    .append("\n");
        });
        return result.toString();
    }

    /**
     * Error checking for XML-formatted transactions to ensure they match the standard. Prints a warning message
     * if something specific is formatted incorrectly.
     */
    private static boolean isValidTransactionFormat(Element fileTransaction, int i)
    {
        if (fileTransaction.getElementsByTagName("type").getLength() == 0)
        {
            System.out.println("WARN: Transaction element no. " + i + " is missing a type. It will be skipped.");
            return false;
        }
        else if (fileTransaction.getElementsByTagName("amount").getLength() == 0)
        {
            System.out.println("WARN: Transaction element no. " + i + " is missing an amount. It will be skipped.");
            return false;
        }
        else if (fileTransaction.getElementsByTagName("refNo").getLength() == 0)
        {
            System.out.println("WARN: Transaction element no. " + i + " is missing a reference number. It will be skipped.");
            return false;
        }

        return true;
    }
}
