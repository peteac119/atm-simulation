package pete.atm.simu.processor;
/**
 * This class can be modified to support multiple cash dispensing
 *  processors if you have different logic.
 *
 *  Currently, I have only one logic to do cash dispensing, so
 *  I just create only one type.
 */

public class DispensingProcessorSelector {

    private DispensingProcessorSelector(){}

    public static DispensingProcessor getDispensingProcessor(){
        return new HighToLowNoteDispensingProcessor();
    }
}