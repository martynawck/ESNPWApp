package com.wiacek.martyna.esnpwapp.ViewEnhancements;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ScrollView;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;

/**
 * Created by Martyna on 2015-04-29.
 */
public class PreparedDocumentView extends ScrollView {

    Context mContext;
    DocumentView documentView;

    /*public PreparedDocumentView (Context context) {
        mContext = context;
        documentView = new DocumentView(context, DocumentView.PLAIN_TEXT);
        setDocumentView();
    }*/
    public PreparedDocumentView (Context context, AttributeSet attrs) {
        super(context, attrs);
        documentView = new DocumentView(context, DocumentView.PLAIN_TEXT);
        //setDocumentView();

    }

    public PreparedDocumentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        documentView = new DocumentView(context, DocumentView.PLAIN_TEXT);
       // setDocumentView();

    }
    public PreparedDocumentView(Context context) {
        super(context);
        documentView = new DocumentView(context, DocumentView.PLAIN_TEXT);
       // setDocumentView();
    }

    public DocumentView setDocumentView() {

        documentView.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        // documentView.getDocumentLayoutParams().setTextColor(000000);
        documentView.getDocumentLayoutParams().setTextTypeface(Typeface.DEFAULT);
        documentView.getDocumentLayoutParams().setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        //   documentView.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        documentView.getDocumentLayoutParams().setInsetPaddingLeft(30f);
        documentView.getDocumentLayoutParams().setInsetPaddingRight(30f);
        documentView.getDocumentLayoutParams().setInsetPaddingTop(30f);
        documentView.getDocumentLayoutParams().setInsetPaddingBottom(30f);
        documentView.getDocumentLayoutParams().setLineHeightMultiplier(1f);
        documentView.setFadeInDuration(800);
        documentView.setFadeInAnimationStepDelay(30);

        return documentView;
    }

    public void setDocumentViewText (String s) {

        documentView.setText(s);
    }


}
