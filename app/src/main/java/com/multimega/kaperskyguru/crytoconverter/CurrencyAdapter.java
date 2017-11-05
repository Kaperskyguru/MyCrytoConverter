package com.multimega.kaperskyguru.crytoconverter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kaperskyguru on 10/27/2017.
 */

public class CurrencyAdapter extends ArrayAdapter<allCurrencies> {


    public CurrencyAdapter(Activity activity, List<allCurrencies> currencies) {
        super(activity, 0, currencies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_currency_list_item, parent, false);
        }

        // Find the devs at the given position in the list of earthquakes
        allCurrencies currentCurrencies = getItem(position);


        TextView btcView = (TextView) listItemView.findViewById(R.id.btc_view);

        assert currentCurrencies != null;
        btcView.setText(String.valueOf(currentCurrencies.getBtc()));


        TextView ethView = (TextView)listItemView.findViewById(R.id.etherum_view);

        ethView.setText(String.valueOf(currentCurrencies.getEtherum()));


        TextView currView = (TextView)listItemView.findViewById(R.id.currency_view);

        currView.setText(currentCurrencies.getCurrency());

        return listItemView;
    }

}
