package com.dbrocato.datacapproject.rule;

import com.dbrocato.datacapproject.transaction.Transaction;

/**
 * Single-function interface (to support lambda usage) that enacts a Rule by accepting a transaction and determining
 * the Payment Processor's fee from it.
 */
public interface RuleApplier
{
    Float applyRule(Transaction transaction);
}
