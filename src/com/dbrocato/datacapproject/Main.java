package com.dbrocato.datacapproject;

import com.dbrocato.datacapproject.gui.DatacapGUI;
import com.dbrocato.datacapproject.transaction.Transaction;
import org.w3c.dom.Document;

import java.util.*;

public class Main
{
    /**
     * Runs the program. Each argument is an input file that contains XML-formatted transactions.
     */
    public static void main(String[] args)
    {
        List<Document> infiles = XMLProcesser.openXMLFiles(args);

        List<Transaction> transactions = XMLProcesser.parseXMLFiles(infiles);
        String preVoidResults = XMLProcesser.calculateProcessorFees(transactions);

        List<Transaction> postVoidTransactions = XMLProcesser.processVoids(transactions);
        String postVoidResults = XMLProcesser.calculateProcessorFees(postVoidTransactions);

        // Print results to console in case the GUI fails to appear.
        System.out.println("\n-------- Payment Processor Rankings (Pre-Void) --------");
        System.out.println(preVoidResults);
        System.out.println("-------- Payment Processor Rankings (Post-Void) --------");
        System.out.println(postVoidResults);

        DatacapGUI.DrawGUI(transactions, preVoidResults, postVoidResults);
    }
}
