package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private long loanId;
    private Double amount;
    private int payments;
    private String toAccountNumber;

    public LoanApplicationDTO(long loanId, Double amount, int payments, String toAccountNumber){
        this.loanId = loanId;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.payments = payments;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
