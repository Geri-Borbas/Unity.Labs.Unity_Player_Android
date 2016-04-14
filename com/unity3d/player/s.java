/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.text.Editable
 *  android.text.Selection
 *  android.text.Spannable
 *  android.text.TextWatcher
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package com.unity3d.player;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.unity3d.player.UnityPlayer;

public final class s
extends Dialog
implements TextWatcher,
View.OnClickListener {
    private Context a = null;
    private UnityPlayer b = null;
    private static int c = -570425344;
    private static int d = 1627389952;
    private static int e = -1;

    public s(Context context, UnityPlayer unityPlayer, String string, int n2, boolean bl, boolean bl2, boolean bl3, String string2) {
        super(context);
        this.a = context;
        this.b = unityPlayer;
        this.getWindow().setGravity(80);
        this.getWindow().requestFeature(1);
        this.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        this.setContentView(this.createSoftInputView());
        this.getWindow().setLayout(-1, -2);
        this.getWindow().clearFlags(2);
        context = (EditText)this.findViewById(1057292289);
        unityPlayer = (Button)this.findViewById(1057292290);
        this.a((EditText)context, string, n2, bl, bl2, bl3, string2);
        unityPlayer.setOnClickListener((View.OnClickListener)this);
        context.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public final void onFocusChange(View view, boolean bl) {
                if (bl) {
                    s.this.getWindow().setSoftInputMode(5);
                }
            }
        });
    }

    private void a(EditText editText, String string, int n2, boolean bl, boolean bl2, boolean bl3, String string2) {
        editText.setImeOptions(6);
        editText.setText((CharSequence)string);
        editText.setHint((CharSequence)string2);
        editText.setHintTextColor(d);
        editText.setInputType(s.a(n2, bl, bl2, bl3));
        editText.addTextChangedListener((TextWatcher)this);
        editText.setClickable(true);
        if (!bl2) {
            editText.selectAll();
        }
    }

    public final void afterTextChanged(Editable editable) {
        this.b.reportSoftInputStr(editable.toString(), 0, false);
    }

    public final void beforeTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
    }

    public final void onTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
    }

    private static int a(int n2, boolean bl, boolean bl2, boolean bl3) {
        bl = (bl ? 32768 : 0) | (bl2 ? 131072 : 0) | (bl3 ? 128 : 0);
        if (n2 < 0 || n2 > 7) {
            return (int)bl ? 1 : 0;
        }
        int[] arrn = new int[]{1, 16385, 12290, 17, 2, 3, 97, 33};
        return bl | arrn[n2];
    }

    private void a(String string, boolean bl) {
        Selection.removeSelection((Spannable)((EditText)this.findViewById(1057292289)).getEditableText());
        this.b.reportSoftInputStr(string, 1, bl);
    }

    public final void onClick(View view) {
        this.a(this.a(), false);
    }

    public final void onBackPressed() {
        this.a(this.a(), true);
    }

    protected final View createSoftInputView() {
        RelativeLayout relativeLayout = new RelativeLayout(this.a);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        relativeLayout.setBackgroundColor(e);
        EditText editText = new EditText(this.a){

            public final boolean onKeyPreIme(int n2, KeyEvent keyEvent) {
                if (n2 == 4) {
                    s.this.a(s.this.a(), true);
                    return true;
                }
                if (n2 == 84) {
                    return true;
                }
                return super.onKeyPreIme(n2, keyEvent);
            }

            public final void onWindowFocusChanged(boolean bl) {
                super.onWindowFocusChanged(bl);
                if (bl) {
                    ((InputMethodManager)s.this.a.getSystemService("input_method")).showSoftInput((View)this, 0);
                }
            }
        };
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(15);
        layoutParams.addRule(0, 1057292290);
        editText.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        editText.setTextColor(c);
        editText.setId(1057292289);
        relativeLayout.addView((View)editText);
        editText = new Button(this.a);
        editText.setText(this.a.getResources().getIdentifier("ok", "string", "android"));
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(15);
        layoutParams.addRule(11);
        editText.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        editText.setId(1057292290);
        editText.setBackgroundColor(0);
        editText.setTextColor(c);
        relativeLayout.addView((View)editText);
        editText = relativeLayout;
        ((EditText)editText.findViewById(1057292289)).setOnEditorActionListener(new TextView.OnEditorActionListener(){

            public final boolean onEditorAction(TextView textView, int n2, KeyEvent keyEvent) {
                if (n2 == 6) {
                    s.this.a(s.this.a(), false);
                }
                return false;
            }
        });
        editText.setPadding(16, 16, 16, 16);
        return editText;
    }

    private String a() {
        EditText editText = (EditText)this.findViewById(1057292289);
        if (editText == null) {
            return null;
        }
        return editText.getText().toString().trim();
    }

    public final void a(String string) {
        EditText editText = (EditText)this.findViewById(1057292289);
        if (editText != null) {
            editText.setText((CharSequence)string);
            editText.setSelection(string.length());
        }
    }

}

