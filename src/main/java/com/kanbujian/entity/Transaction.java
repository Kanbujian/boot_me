package com.kanbujian.entity;


import com.kanbujian.converter.MapConverter;
import com.kanbujian.converter.TransactionDataConverter;
import com.kanbujian.payment.ChargeDispatcher;

import javax.annotation.PreDestroy;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    @Convert(converter = MapConverter.class)
    private Map<String, Object> extra;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, mappedBy = "transaction")
    private List<TransactionLog> transactionLogs;

    private String notifyUrl;

    private String readableNumber;

    private String gateway;

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

    public List<TransactionLog> getTransactionLogs() {
        return transactionLogs;
    }

    public void setTransactionLogs(List<TransactionLog> transactionLogs) {
        this.transactionLogs = transactionLogs;
    }

    public Map getExtra() {
        return extra;
    }

    public void setExtra(Map extra) {
        this.extra = extra;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReadableNumber() {
        return readableNumber;
    }

    public void setReadableNumber(String readableNumber) {
        this.readableNumber = readableNumber;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    enum OrderAction{
        charge,
        refund
    }

}
