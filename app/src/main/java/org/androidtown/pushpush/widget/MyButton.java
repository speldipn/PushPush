package org.androidtown.pushpush.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends AppCompatButton {

  public MyButton(Context context) {
    super(context);
    setTheme(this);
  }

  public MyButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    setTheme(this);
    // attrs : xml에서 정의된 값들이 inflation되어 넘어온다.
  }

  public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setTheme(this);
  }

  private void setTheme(AppCompatButton button) {
    button.setBackgroundColor(Color.BLUE);
    button.setTextColor(Color.WHITE);
  }
}
