package com.fauzancode.capstoneapp

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.fauzancode.capstoneapp.ui.auth.LoginActivity
import com.fauzancode.capstoneapp.ui.auth.RegisterActivity

fun spannable(view: TextView, text: String, spanStart: Int, spanEnd: Int, context: Context, intent: Intent){
    val ss = SpannableString(text)
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            context.startActivity(intent)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }
    ss.setSpan(clickableSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    view.text = ss
    view.movementMethod = LinkMovementMethod.getInstance()
}