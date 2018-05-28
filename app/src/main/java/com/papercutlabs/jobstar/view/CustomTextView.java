package com.papercutlabs.jobstar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.papercutlabs.jobstar.R;

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        if (isInEditMode()) return;
        parseAttributes(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        parseAttributes(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;
        parseAttributes(attrs);
    }

    private void parseAttributes(AttributeSet attrs) {
        int typeface;
        if (attrs == null) { //Not created from xml
            typeface = Roboto.SFUI_REGULAR;
        } else {
            TypedArray values = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            typeface = values.getInt(R.styleable.CustomTextView_typeface, Roboto.SFUI_REGULAR);
            values.recycle();
        }
        setTypeface(getRoboto(typeface));
    }

    public void setRobotoTypeface(int typeface) {
        setTypeface(getRoboto(typeface));
    }

    private Typeface getRoboto(int typeface) {
        return getRoboto(getContext(), typeface);
    }

    public static Typeface getRoboto(Context context, int typeface) {
        switch (typeface) {
            case Roboto.SFUI_REGULAR:
                if (Roboto.sfuiRegular == null) {
                    Roboto.sfuiRegular = Typeface.createFromAsset(context.getAssets(), "font/sfui_regular.otf");
                }
                return Roboto.sfuiRegular;
            case Roboto.SFUI_MEDIUM:
                if (Roboto.sfuiMedium == null) {
                    Roboto.sfuiMedium = Typeface.createFromAsset(context.getAssets(), "font/sfui_medium.otf");
                }
                return Roboto.sfuiMedium;

            default:

                if (Roboto.sfuiRegular == null) {
                    Roboto.sfuiRegular = Typeface.createFromAsset(context.getAssets(), "font/sfui_regular.otf");
                }
                return Roboto.sfuiRegular;
        }
    }

    public static class Roboto {
        public static final int SFUI_REGULAR = 0;
        public static final int SFUI_MEDIUM = 1;

        private static Typeface sfuiRegular;
        private static Typeface sfuiMedium;
    }
}
