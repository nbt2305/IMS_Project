package com.example.g2_se1630_swd392.common.helper;

import java.util.Date;
import java.util.Random;

public class CheckLogic {
    public static Boolean checkLogicInputDate(Date fromDate, Date toDate) {
        return toDate.after(fromDate);
    }
}
