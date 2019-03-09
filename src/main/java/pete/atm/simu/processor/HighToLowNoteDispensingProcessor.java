package pete.atm.simu.processor;

import pete.atm.simu.entity.CashReport;
import pete.atm.simu.exception.ATMException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HighToLowNoteDispensingProcessor implements DispensingProcessor{
    @Override
    public List<CashReport> process(int dispensingAmount, List<CashReport> availableCashReports) throws ATMException {

        if (dispensingAmount <= 0){
            throw new ATMException("");
        }

        Stack<CashReport> cashReportStack = new Stack<>();
        List<CashReport> cashResultReport = new ArrayList<>();

        for (CashReport avaiableCashReport : availableCashReports ){
            int expectedNumOfNotes = dispensingAmount / avaiableCashReport.getValue();
            int actualNumOfNotes = avaiableCashReport.getAvailableNotes() - expectedNumOfNotes;

            CashReport newCashReport = new CashReport();
            newCashReport.setNoteName(avaiableCashReport.getNoteName());
            newCashReport.setValue(avaiableCashReport.getValue());

            if (actualNumOfNotes >= 0){
                newCashReport.setAvailableNotes(expectedNumOfNotes);
                dispensingAmount -= avaiableCashReport.getValue() * expectedNumOfNotes;
            }else{
                newCashReport.setAvailableNotes(avaiableCashReport.getAvailableNotes());
                dispensingAmount -= avaiableCashReport.getValue() * avaiableCashReport.getAvailableNotes();
            }

            cashReportStack.push(newCashReport);
        }

        if (dispensingAmount > 0){
            restackNotes(cashReportStack, dispensingAmount, availableCashReports);
        }

        while(!cashReportStack.isEmpty()){
            cashResultReport.add(cashReportStack.pop());
        }

        return cashReportStack;
    }

    private void restackNotes(Stack<CashReport> cashSlotStack, int total, List<CashReport> availableCashReports) throws ATMException {
        if (cashSlotStack.isEmpty() && total != 0){
            throw new ATMException("");
        }

        // TODO Recalculate the notes.
    }
}
