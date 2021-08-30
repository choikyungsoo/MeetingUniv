package com.softcon.meetinguniv;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ChargeCashDialogFragment extends Fragment {
    private Context context;
    private Dialog dlg;
    private TextView chargeHeartNum, chargeHeartCash, onHandCash, chargeCashAmount, paymentAmount;
    private Button goToChargeCash_BTN;

    public ChargeCashDialogFragment(Context context) { this.context = context; }

    public void showChargeCashDialogFunction(String itemName, String itemPrice, String onHandCashString) {
        this.dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.fragment_charge_cash_dialog);
        dlg.show();

        this.chargeHeartNum = dlg.findViewById(R.id.chargeHeartNum);
        this.chargeHeartCash = dlg.findViewById(R.id.chargeHeartCash);
        this.onHandCash = dlg.findViewById(R.id.onHandCash);
        this.chargeCashAmount = dlg.findViewById(R.id.chargeCashAmount);
        this.paymentAmount = dlg.findViewById(R.id.paymentAmount);
        this.goToChargeCash_BTN = dlg.findViewById(R.id.goToChargeCash_BTN);

        this.chargeHeartNum.setText(itemName);
        this.chargeHeartCash.setText(itemPrice);
        this.onHandCash.setText(onHandCashString);

        String[] priceArr = itemPrice.split(" ");
        String[] onHandCashArr = onHandCashString.split(" ");

        int priceInt = Integer.parseInt(priceArr[0]);
        int onHandCashInt = Integer.parseInt(onHandCashArr[0]);

        if (priceInt > onHandCashInt) {
            int differenceCash = priceInt-onHandCashInt;
            if (differenceCash <= 1000) {
                this.chargeCashAmount.setText("1000 캐시");
                this.paymentAmount.setText("1200 원");
            } else if (differenceCash <= 2000) {
                this.chargeCashAmount.setText("2000 캐시");
                this.paymentAmount.setText("2400 원");
            } else if (differenceCash <= 3000) {
                this.chargeCashAmount.setText("3000 캐시");
                this.paymentAmount.setText("3600 원");
            } else if (differenceCash <= 5000) {
            this.chargeCashAmount.setText("5000 캐시");
                this.paymentAmount.setText("6000 원");
            }
        } else {
            this.chargeCashAmount.setText("0 캐시");
            this.paymentAmount.setText("0 원");
        }
    }
}