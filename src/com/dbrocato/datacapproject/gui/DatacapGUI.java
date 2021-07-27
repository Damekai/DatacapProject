package com.dbrocato.datacapproject.gui;

import com.dbrocato.datacapproject.transaction.Transaction;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;

public class DatacapGUI
{
    public static void DrawGUI(List<Transaction> transactions, String preVoidResults, String postVoidResults)
    {
        JFrame frame = new JFrame("Datacap Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Add a text area to display all the text data.
        JTextArea textArea = new JTextArea();

        // Ensure that the text area is scrollable, as the number of transactions will likely not fit on one page.
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(500, 500));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Add transactions to GUI.
        textArea.append("-------------------- Transactions --------------------\n");
        transactions.forEach((transaction) -> textArea.append(transaction.toString() + "\n"));

        // Add rankings.
        textArea.append("\n-------- Payment Processor Rankings (Pre-Void) --------\n");
        textArea.append(preVoidResults);
        textArea.append("\n-------- Payment Processor Rankings (Post-Void) --------\n");
        textArea.append(postVoidResults);

        JPanel panel = new JPanel();
        panel.add(scroll);
        frame.add(panel);

        frame.setVisible(true);
    }
}
