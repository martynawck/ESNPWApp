package com.wiacek.martyna.esnpwapp.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.JustifiedSpan;
import com.bluejamesbond.text.style.TextAlignment;
import com.wiacek.martyna.esnpwapp.Domain.RawFileReader;
import com.wiacek.martyna.esnpwapp.R;
import com.wiacek.martyna.esnpwapp.ViewEnhancements.ArticleBuilder;
import com.wiacek.martyna.esnpwapp.ViewEnhancements.MyLeadingMarginSpan2;

public class RentAHouseInfoFragment extends Fragment {

    ImageView ivIcon;
    TextView tvItemName;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    RawFileReader rfr;

    //TextView content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_documents, container,
                false);

        rfr = new RawFileReader(getActivity().getApplicationContext());

        DocumentView documentView = new DocumentView(getActivity().getApplicationContext(), DocumentView.FORMATTED_TEXT );
        documentView.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        documentView.getDocumentLayoutParams().setTextColor(0xff888888);
        documentView.getDocumentLayoutParams().setTextTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/roboto.ttf"));
        documentView.getDocumentLayoutParams().setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        documentView.getDocumentLayoutParams().setInsetPaddingLeft(50f);
        documentView.getDocumentLayoutParams().setInsetPaddingRight(50f);
        documentView.getDocumentLayoutParams().setInsetPaddingTop(50f);
        documentView.getDocumentLayoutParams().setInsetPaddingBottom(50f);
        documentView.getDocumentLayoutParams().setLineHeightMultiplier(1f);

        documentView.setFadeInDuration(800);
        documentView.setFadeInAnimationStepDelay(30);

        ArticleBuilder amb = new ArticleBuilder();
        amb.append(rfr.RentHouseRawResources().toString(), false,  new RelativeSizeSpan(10f), new JustifiedSpan(),new MyLeadingMarginSpan2(2, 100));

        try {

            documentView.setText(amb);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Problems: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.addView(documentView);

        LinearLayout articleList = (LinearLayout) view.findViewById(R.id.articleList);
        articleList.addView(linearLayout);

        return view;

    }
}