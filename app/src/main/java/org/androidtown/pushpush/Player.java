package org.androidtown.pushpush;

import android.graphics.Color;
import android.graphics.Paint;

public class Player {
  Paint paint;
  int x, y;

  public Player() {
    paint = new Paint();
    paint.setColor(Color.RED);
    x = 0;
    y = 0;
  }

  public void up() {
      y -= 1;
  }

  public void down() {
    y += 1;
  }

  public void left() {
    x -= 1;
  }

  public void right() {
    x += 1;
  }
}
