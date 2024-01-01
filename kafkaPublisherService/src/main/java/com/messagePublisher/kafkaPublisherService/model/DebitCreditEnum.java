package com.messagePublisher.kafkaPublisherService.model;

public enum DebitCreditEnum {
    DEBIT,
    CREDIT;

    @Override
    public String toString() {
        return name();
    }
}
