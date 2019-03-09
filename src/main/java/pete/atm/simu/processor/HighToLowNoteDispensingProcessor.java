package pete.atm.simu.processor;

import pete.atm.simu.entity.CashReport;
import pete.atm.simu.exception.ATMException;
import pete.atm.simu.exception.DispensingAmountIsNegativeException;
import pete.atm.simu.exception.InsufficientNotesException;
import pete.atm.simu.exception.UnsupportedDispensingAmountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HighToLowNoteDispensingProcessor implements DispensingProcessor{
    @Override
    public List<CashReport> process(int dispensingAmount, List<CashReport> availableCashReports) throws ATMException {

        if (dispensingAmount <= 0){
            throw new DispensingAmountIsNegativeException("Dispensing amount must be positive integer.");
        }

        Stack<CashReport> cashReportStack = new Stack<>();
        List<CashReport> cashResultReport = new ArrayList<>();

        for (CashReport availableCashReport : availableCashReports ){
            int expectedNumOfNotes = dispensingAmount / availableCashReport.getValue();
            int actualNumOfNotes = availableCashReport.getAvailableNotes() - expectedNumOfNotes;

            CashReport newCashReport = new CashReport();
            newCashReport.setNoteType(availableCashReport.getNoteType());
            newCashReport.setValue(availableCashReport.getValue());

            if (actualNumOfNotes >= 0){
                newCashReport.setAvailableNotes(expectedNumOfNotes);
                availableCashReport.setAvailableNotes(actualNumOfNotes);
                dispensingAmount -= availableCashReport.getValue() * expectedNumOfNotes;
            }else{
                newCashReport.setAvailableNotes(availableCashReport.getAvailableNotes());
                availableCashReport.setAvailableNotes(0);
                dispensingAmount -= availableCashReport.getValue() * availableCashReport.getAvailableNotes();
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

    private void restackNotes(Stack<CashReport> cashSlotStack, int dispensingAmount, List<CashReport> availableCashReports) throws ATMException {
        if (cashSlotStack.isEmpty() && dispensingAmount != 0){
            throw new InsufficientNotesException("Insufficient note for this dispensing amount.");
        }

        CashReport lowestNote = cashSlotStack.pop();
        CashReport biggerNote = cashSlotStack.pop();

        if (biggerNote.getAvailableNotes() > 0){
            CashReport availableBiggerNote = availableCashReports.stream()
                    .filter(cashReport -> cashReport.getNoteType().equals(biggerNote.getNoteType()))
                    .findFirst().get();
            biggerNote.setAvailableNotes(biggerNote.getAvailableNotes() - 1);
            availableBiggerNote.setAvailableNotes(availableBiggerNote.getAvailableNotes() + 1);
            dispensingAmount += biggerNote.getValue();
        }

        cashSlotStack.push(biggerNote);

        CashReport availableLowestNote = availableCashReports.stream()
                .filter(cashReport -> cashReport.getNoteType().equals(lowestNote.getNoteType()))
                .findFirst().get();

        while(dispensingAmount > 0){
            availableLowestNote.setAvailableNotes(availableLowestNote.getAvailableNotes() - 1);
            lowestNote.setAvailableNotes(lowestNote.getAvailableNotes() + 1);
            if(availableLowestNote.getAvailableNotes() >= 0){
                dispensingAmount -= lowestNote.getValue();
            }else{
                throw new UnsupportedDispensingAmountException("Current notes that ATM has do not support this dispensing amount.");
            }
        }

        if (dispensingAmount < 0){
            throw new UnsupportedDispensingAmountException("Current notes that ATM has do not support this dispensing amount.");
        }

        cashSlotStack.push(lowestNote);
    }
}
