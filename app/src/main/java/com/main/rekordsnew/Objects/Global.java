package com.main.rekordsnew.Objects;

import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.Others.OtherModel;

public class Global {
    public static final String clientRef = "Clients";
    public static final String leafRef  = "LeafCollector";
    public static final String accountantRef = "Accountants";
    public static final String medicalRef = "MedicalStaff";

    public static final String collectionRef = "LeafCollected";


    public static ClientRoot SELECTED_CLIENT ;
    public static OtherModel SELECTED_LEAF_COLLECTOR;
    public static final String challansRef = "Challans";
    public static final String teaPriceRef="TeaPrice";
    public static final String vCodeRef = "VerificationCode";

    // Current User //
    public static  ClientRoot CURRENT_USER_CLIENT ;
    public static  String CURRENT_ADMIN_KEY ;
    public static OtherModel CURRENT_OTHER_USER ;

}
