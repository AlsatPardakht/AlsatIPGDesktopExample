package com.alsatpardakht;

import com.alsatpardakht.alsatipgcore.domain.model.PaymentSignResult;
import com.alsatpardakht.alsatipgcore.domain.model.PaymentType;
import com.alsatpardakht.alsatipgcore.domain.model.PaymentValidationResult;
import com.alsatpardakht.alsatipgcore.domain.model.TashimModel;
import com.alsatpardakht.alsatipgdesktop.AlsatIPG;
import com.alsatpardakht.alsatipgdesktop.util.Observer;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;
import static com.alsatpardakht.alsatipgcore.domain.model.PaymentType.*;

public class Main {

    private static final String API = "ENTER YOUR API KEY HERE";
    private static final AlsatIPG alsatIPG = AlsatIPG.getInstance(false);
    private static PaymentType paymentType = Mostaghim;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        testSign();
        Thread.sleep(600_000);// in order to keep the console alive
        removeObservers();
    }

    private static void testSign() {
        alsatIPG.getPaymentSignStatus().observeForever(new Observer<PaymentSignResult>() {
            @Override
            public void onChanged(PaymentSignResult paymentSignResult) {
                if (paymentSignResult.isLoading()) {
                    System.out.println("loading");
                } else if (paymentSignResult.isSuccessful()) {
                    System.out.println("successful");
                    System.out.println(paymentSignResult);

                    try {
                        if (
                                Desktop.isDesktopSupported() &&
                                        Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) &&
                                        paymentSignResult.getUrl() != null
                        ) {
                            Desktop.getDesktop().browse(new URI(paymentSignResult.getUrl()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    System.out.println(">> validation <<");
                    System.out.print("enter tref : ");
                    String tref = scanner.nextLine();
                    System.out.print("enter iN : ");
                    String iN = scanner.nextLine();
                    System.out.print("enter iD : ");
                    String iD = scanner.nextLine();
                    System.out.print("enter PayId : ");
                    String PayId = scanner.nextLine();

                    testValidation(tref, iN, iD, PayId);
                    scanner.nextLine();
                } else {
                    if (paymentSignResult.getError() != null)
                        System.out.println("error : " + paymentSignResult.getError().getMessage());
                }
            }
        });

        switch (paymentType) {
            case Mostaghim: {
                alsatIPG.signMostaghim(
                        10_000,//Amount
                        API,//Api
                        "123",//InvoiceNumber
                        "http://eample.com"//RedirectAddress
                );
                break;
            }
            case Vaset: {
                alsatIPG.signVaset(
                        20_000,//Amount
                        API,//Api
                        "http://eample.com",//RedirectAddress
                        "123",//InvoiceNumber
                        new ArrayList<TashimModel>()//Tashim
                );
                break;
            }
        }
    }

    private static void testValidation(String tref, String iN, String iD, String PayId) {
        alsatIPG.getPaymentValidationStatus().observeForever(new Observer<PaymentValidationResult>() {
            @Override
            public void onChanged(PaymentValidationResult paymentValidationResult) {
                if (paymentValidationResult.isSuccessful()) {
                    System.out.println("successful");
                    System.out.println("payment Validation Success data = " + paymentValidationResult.getData());
                    if (
                            (paymentValidationResult.getData() != null) &&
                                    (paymentValidationResult.getData().getPSP().getIsSuccess()) &&
                                    (paymentValidationResult.getData().getVERIFY().getIsSuccess())
                    ) {
                        System.out.println("money transferred");
                    } else {
                        System.out.println("money has not been transferred");
                    }
                } else if (paymentValidationResult.isLoading()) {
                    System.out.println("loading");
                } else if (paymentValidationResult.getError() != null) {
                    System.out.println("error : " + paymentValidationResult.getError().getMessage());
                }
            }
        });
//      URI data = URI("http://eample.com/?tref=637829347727645295&iN=123&iD=2022/03/15%2009:52:54")
        switch (paymentType) {
            case Mostaghim: {
                alsatIPG.validationMostaghim(
                        API,//Api
//                      data//data
                        tref,//tref
                        iN,//iN
                        iD,//iD
                        PayId//PayId
                );
                break;
            }
            case Vaset: {
                alsatIPG.validationVaset(
                        API,//Api
//                      data//data
                        tref,//tref
                        iN,//iN
                        iD,//iD
                        PayId//PayId
                );
                break;
            }
        }
    }

    private static void removeObservers() {
        alsatIPG.getPaymentSignStatus().removeAllObservers();
        alsatIPG.getPaymentValidationStatus().removeAllObservers();
    }
}