package Models;

import java.time.LocalDateTime;

public class Statements {

    int StatementID;
    String StatementDescription, OutMoney, InMoney, StatementRemarks;
    LocalDateTime DateTime;

    public Statements(int StatementID,String StatementDescription, String OutMoney, String InMoney, String StatementRemarks, LocalDateTime DateTime) {
        this.StatementID = StatementID;
        this.StatementDescription = StatementDescription;
        this.OutMoney = OutMoney;
        this.InMoney= InMoney;
        this.StatementRemarks = StatementRemarks;
        this.DateTime= DateTime;
    }

    public int getStatementID() {
        return StatementID;
    }

    public void setStatementID(int StatementID) {
        this.StatementID = StatementID;
    }

    public String getStatementDescription() {
        return StatementDescription;
    }

    public void setStatementDescription(String StatementDescription) {this.StatementDescription = StatementDescription;}

    public String getOutMoney() {
        return OutMoney;
    }

    public void setOutMoney(String OutMoney) {
        this.OutMoney = OutMoney;
    }

    public String getInMoney() {
        return InMoney;
    }

    public void setInMoney(String InMoney) {
        this.InMoney = InMoney;
    }

    public String getStatementRemarks() {
        return StatementRemarks;
    }

    public void setStatementRemarks(String StatementRemarks) {
        this.StatementRemarks = StatementRemarks;
    }

    public LocalDateTime getDateTime() {
        return DateTime;
    }

    public void setDateTime(LocalDateTime DateTime) {
        this.DateTime = DateTime;
    }




}

