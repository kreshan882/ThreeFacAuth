package mcs.kreshan.utill;

/**
 * Created by kreshan88 on 11/19/2017.
 */

public class ResponceCode {

    //#### finger authondication code ####
    //Called when a recoverable error has been encountered during authentication.
    public static final int RECOVERABLE_ERROR = 843;

    //Called when an unrecoverable error has been encountered and the operation is complete.
    public static final int NON_RECOVERABLE_ERROR = 566;

    //Called when a fingerprint is valid but not recognized.
    public static final int CANNOT_RECOGNIZE_ERROR = 456;
}