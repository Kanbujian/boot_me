package com.kanbujian.entity;


import com.kanbujian.converter.TransactionDataConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name= "transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    @NotNull
    private Integer amount;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private OrderAction orderAction;

    @Convert(converter = TransactionDataConverter.class)
    private TransactionData transactionData;

    public Transaction(){
    }

    public Transaction(String currency, Integer amount, String orderAction) {
        this.currency = currency;
        this.amount = amount;
        this.orderAction = OrderAction.valueOf(orderAction);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public OrderAction getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(OrderAction orderAction) {
        this.orderAction = orderAction;
    }

    public TransactionData getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(TransactionData transactionData) {
        this.transactionData = transactionData;
    }

    enum OrderAction{
        charge,
        refund
    }
}
