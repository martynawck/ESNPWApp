package com.wiacek.martyna.esnpwapp.ViewEnhancements;

import android.text.Html;
import android.text.SpannableStringBuilder;

/**
 * Created by Martyna on 2015-04-29.
 */
public class ArticleBuilder extends SpannableStringBuilder {

    public ArticleBuilder append(CharSequence text, boolean newline, Object... spans) {
        this.append(Html.fromHtml(text + "<br/>" + (newline ? "<br/>" : "")));
        return this;
    }
}