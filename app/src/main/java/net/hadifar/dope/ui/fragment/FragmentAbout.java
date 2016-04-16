package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.hadifar.dope.R;
import net.hadifar.dope.utils.IntentHelper;

/**
 * Amir Hadifar on 01/08/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class FragmentAbout extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.drawer_item_about);

        TextView mailTo = (TextView) view.findViewById(R.id.mailTo);
        mailTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentHelper.sendEmail(getActivity());
            }
        });

        TextView shareMe = (TextView) view.findViewById(R.id.shareMe);
        shareMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentHelper.shareOnSocials(getActivity());
            }
        });

    }
}
