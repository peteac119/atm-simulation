package pete.atm.simu.processor;

import pete.atm.simu.entity.CashReport;
import pete.atm.simu.exception.ATMException;
import pete.atm.simu.exception.UnsupportedDispensingAmountException;

import java.util.*;

public class HighToLowNoteDispensingProcessor implements DispensingProcessor{
    @Override
    public List<CashReport> process(int dispensingAmount, List<CashReport> availableCashReports) throws ATMException {

        Collections.sort(availableCashReports,
                Comparator.comparingInt(CashReport::getValue).reversed());

        Stack<CashReport> cashReportStack = new Stack<>();
        List<CashReport> cashResultReport = new ArrayList<>();

        for (CashReport availableCashReport : availableCashReports ){
            int expectedNumOfNotes = dispensingAmount / availableCashReport.getValue();
            int actualNumOfNotes = availableCashReport.getAvailableNotes() - expectedNumOfNotes;

            CashReport dispensedCashReport = new CashReport();
            dispensedCashReport.setNoteType(availableCashReport.getNoteType());
            dispensedCashReport.setValue(availableCashReport.getValue());

            if (actualNumOfNotes >= 0){
                dispensedCashReport.setAvailableNotes(expectedNumOfNotes);
                availableCashReport.setAvailableNotes(actualNumOfNotes);
                dispensingAmount -= availableCashReport.getValue() * expectedNumOfNotes;
            }else{
                dispensedCashReport.setAvailableNotes(availableCashReport.getAvailableNotes());
                availableCashReport.setAvailableNotes(0);
                dispensingAmount -= availableCashReport.getValue() * availableCashReport.getAvailableNotes();
            }

            cashReportStack.push(dispensedCashReport);
        }

        if (dispensingAmount > 0){
            restackNotes(cashReportStack, dispensingAmount, availableCashReports);
        }

        while(!cashReportStack.isEmpty()){
            cashResultReport.add(cashReportStack.pop());
        }

        return cashResultReport;
    }

    private void restackNotes(Stack<CashReport> cashSlotStack, int dispensingAmount, List<CashReport> availableCashReports) throws ATMException {

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
            lowestNote.setAvailableNotes(lowestNote.getAvailableNotes() + 1);
            if(availableLowestNote.getAvailableNotes() > 0){
                availableLowestNote.setAvailableNotes(availableLowestNote.getAvailableNotes() - 1);
                dispensingAmount -= lowestNote.getValue();
            }else{
                throw new UnsupportedDispensingAmountException();
            }
        }

        if (dispensingAmount < 0){
            throw new UnsupportedDispensingAmountException();
        }

        cashSlotStack.push(lowestNote);
    }
}
