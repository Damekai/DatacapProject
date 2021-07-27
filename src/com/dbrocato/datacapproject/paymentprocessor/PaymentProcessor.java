package com.dbrocato.datacapproject.paymentprocessor;

import com.dbrocato.datacapproject.rule.Rule;
import com.dbrocato.datacapproject.transaction.Transaction;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Simple object class that defines a Payment Processor. Also defines the function that calculates the total fee given
 * a list of transactions. All Payment Processors can be found in PaymentProcessorRegistry.java.
 */
public class PaymentProcessor
{
    // USD formatting for use when rounding and displaying.
    public static DecimalFormat USD_DECIMAL_FORMAT = new DecimalFormat("0.00");

    private final String name;
    private final List<Rule> transactionRules;

    public PaymentProcessor(String name, Rule... transactionRules)
    {
        this.name = name;
        this.transactionRules = Arrays.asList(transactionRules);
    }

    public String getName()
    {
        return name;
    }

    public float processTransactions(List<Transaction> transactions)
    {
        float fee = 0f;
        for (Transaction transaction : transactions)
        {
            /* While this may seem like a convoluted method to deal with the imprecision of float addition, it was
            necessary to ensure that an accurate value was being recorded and totaled. I could have waited until the
            total fee was summed and then rounded, but that would not be an accurate value, since in reality, the
            individual fees are likely rounded to the nearest USD value (hundreths) after each transaction. */
            fee += Float.parseFloat(USD_DECIMAL_FORMAT.format(processTransaction(transaction)));
            fee = Float.parseFloat(USD_DECIMAL_FORMAT.format(fee));
        }

        return fee;
    }

    private float processTransaction(Transaction transaction)
    {
        float fee = 0f;
        for (Rule rule : transactionRules)
        {
            if (rule.isRuleApplicable(transaction))
            {
                fee += rule.applyRule(transaction);
            }
        }
        return fee;
    }
}
