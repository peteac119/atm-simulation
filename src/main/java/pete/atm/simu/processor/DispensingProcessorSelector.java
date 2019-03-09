package pete.atm.simu.processor;

public class DispensingProcessorSelector {

    private DispensingProcessorSelector(){}

    public static DispensingProcessor getDispensingProcessor(){
        /*
        This class can be modified to support multiple cash dispensing
        processors if you have different logic to do cash dispensing.

        Currently, I have only one logic to do cash dispensing, so
        I just create only one type.
         */

        return new HighToLowNoteDispensingProcessor();
    }
}
