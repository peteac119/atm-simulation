package pete.atm.simu.processor;

/**
 * Usually, this class is not necessary if we have only one logic for dispensing back note.
 *
 * However, if UI allows user to select what solution it wants, then this might get a choice from UI
 * and select the most suitable solution for particular user's choice.
 */
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
