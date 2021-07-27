package com.dbrocato.datacapproject.paymentprocessor;

import com.dbrocato.datacapproject.rule.Rule;
import com.dbrocato.datacapproject.rule.RuleValidator;
import com.dbrocato.datacapproject.rule.StandardRuleApplier;

import java.util.ArrayList;
import java.util.List;

public class PaymentProcessorRegistry
{
    // Payment Processor singletons are defined here. More can be defined here, just like these.
    private static final PaymentProcessor TSYS = new PaymentProcessor(
            "TSYS",
            new Rule(RuleValidator.LESS_THAN_FIFTY_USD, new StandardRuleApplier(0.1f, 0.01f)),
            new Rule(RuleValidator.GREATER_THAN_OR_EQUAL_TO_FIFTY_USD, new StandardRuleApplier(0.1f, 0.02f))
    );

    private static final PaymentProcessor FIRST_DATA = new PaymentProcessor(
            "First Data",
            new Rule(RuleValidator.LESS_THAN_FIFTY_USD, new StandardRuleApplier(0.08f, 0.0125f)),
            new Rule(RuleValidator.GREATER_THAN_OR_EQUAL_TO_FIFTY_USD, new StandardRuleApplier(0.9f, 0.01f))
    );

    private static final PaymentProcessor EVO = new PaymentProcessor(
            "EVO",

            /* While a pre-defined RuleValidator the StandardRuleApplier could have been used for each of these Rules as
            well, I wanted to demonstrate how a user could leverage my design to quickly write one-off RuleValidators and
            RuleAppliers using lambdas. Refer to the README for more information. */
            new Rule((transaction) -> transaction.getAmount() < 50f, (transaction) -> 0.09f + transaction.getAmount() * 0.011f),
            new Rule((transaction) -> transaction.getAmount() >= 50f, (transaction) -> 0.2f + transaction.getAmount() * 0.015f)
    );

    // Maintains a list of all Payment Processors included when creating the ranking.
    public static final List<PaymentProcessor> PAYMENT_PROCESSORS = new ArrayList<PaymentProcessor>()
    {{
        add(TSYS);
        add(FIRST_DATA);
        add(EVO);
    }};
}
