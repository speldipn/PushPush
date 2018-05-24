package org.androidtown.pushpush;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class Stage extends View {
  int gridCount;
  float unit;
  Player player;

  int currentMap[][];
  Paint gridPaint = new Paint();
  Paint goalPaint = new Paint();
  Paint barrierPaint = new Paint();
  Paint blockPaint = new Paint();
  Paint okPaint = new Paint();
  Paint tempPaint = null;
  GameChecker gameChecker;

  public Stage(Context context) {
    super(context);
    this.gameChecker = (MainActivity)context;
    // grid
    gridPaint.setColor(Color.GRAY); // 사각형의 색
    gridPaint.setStyle(Paint.Style.STROKE); // 사각형의 스타일
    gridPaint.setStrokeWidth(1); // 선두께
    // goal
    goalPaint.setColor(Color.YELLOW);
    // ok
    okPaint.setColor(Color.BLUE);
    // block
    blockPaint.setColor(Color.GREEN);
    // barrier
    barrierPaint.setColor(Color.BLACK);
  }

  public void setConfig(int gridCount, float unit) {
    this.gridCount = gridCount;
    this.unit = unit;
  }

  @Override
  protected void onDraw(Canvas canvas) {
//    super.onDraw(canvas);
    drawMap(canvas);
    drawPlayer(canvas);
  }

  public interface GameChecker {
    public void endOfGameCallback();
  }

  private void drawMap(Canvas canvas) {
    boolean exitFlag = true;
    for (int i = 0; i < currentMap.length; ++i) {
      for (int j = 0; j < currentMap[0].length; ++j) {
        int top = (int) (i * unit);
        int left = (int) (j * unit);
        int bottom = (int) (i * unit + unit);
        int right = (int) (j * unit + unit);
        if (currentMap[i][j] == 0) {
          tempPaint = gridPaint;
        } else if (currentMap[i][j] == 1) {
          tempPaint = blockPaint;
        } else if (currentMap[i][j] == 2) {
          tempPaint = barrierPaint;
        } else if (currentMap[i][j] == 9) {
          tempPaint = goalPaint;
          exitFlag = false;
        } else if (currentMap[i][j] == 5) {
          tempPaint = okPaint;
        }
        canvas.drawRect(left, top, right, bottom, tempPaint);
        // TODO: 골인 지점을 삼각형.
      }
    }
    if(exitFlag) {
      gameChecker.endOfGameCallback();
    }
  }

  public void addPlayer(Player player) {
    this.player = player;
  }

  private void drawPlayer(Canvas canvas) {
    int startPadingX = (int) unit / 2;
    int startPadingY = (int) unit / 2;
    canvas.drawCircle(
      player.x * unit + startPadingX,
      player.y * unit + startPadingY,
      unit / 2,
      player.paint
    );
  }

  public static final int DIRECTION_UP = 0;
  public static final int DIRECTION_DOWN = 1;
  public static final int DIRECTION_LEFT = 2;
  public static final int DIRECTION_RIGHT = 3;

  public void move(int direction) {
    if (player != null) {

      switch (direction) {
        case DIRECTION_UP:
          if (checkCollision(direction)) {
            if (player.y > 0) {
              player.up();
            }
          }
          break;
        case DIRECTION_DOWN:
          if (checkCollision(direction)) {
            if (player.y < (gridCount - 1)) {
              player.down();
            }
          }
          break;
        case DIRECTION_LEFT:
          if (checkCollision(direction)) {
            if (player.x > 0) {
              player.left();
            }
          }
          break;
        case DIRECTION_RIGHT:
          if (checkCollision(direction)) {
            if (player.x < (gridCount - 1)) {
              player.right();
            }
          }
          break;
      }
      invalidate();
    }
  }

  private boolean checkPos(int x, int y) {
    if (((x >= 0) && (x < gridCount))
      && ((y >= 0) && (y < gridCount))) {
      return true;
    }
    return false;
  }

  private boolean checkCollision(int direction) {
    boolean ok = true;
    int my_x = player.x;
    int my_y = player.y;
    int next_x = 0;
    int next_y = 0;
    switch (direction) {
      case DIRECTION_UP:
        next_x = my_y - 1;
        next_y = my_x;
        if (checkPos(next_x, next_y)) {
          if (currentMap[next_x][next_y] == 1 || currentMap[next_x][next_y] == 5) {
            if (checkPos(next_x - 1, next_y)
            && (currentMap[next_x - 1][next_y] == 1 || currentMap[next_x -1][next_y] == 2)) {
              // 블럭 이중 충돌
              ok = false;
            } else if (checkPos(next_x - 1, next_y) && currentMap[next_x - 1][next_y] == 9) {
              currentMap[next_x][next_y] = 0;
              currentMap[next_x - 1][next_y] = 5;
            } else {
              // 블럭 이동
              if (checkPos(next_x - 1, next_y)) {
                if (currentMap[next_x - 1][next_y] == 0) {
                  if (currentMap[next_x][next_y] == 0 || currentMap[next_x][next_y] == 1) {
                    currentMap[next_x][next_y] = 0;
                    currentMap[next_x - 1][next_y] = 1;
                  } else if (currentMap[next_x][next_y] == 5) {
                    currentMap[next_x][next_y] = 9;
                    currentMap[next_x - 1][next_y] = 1;
                  }
                }
              } else {
                ok = false;
              }
            }
          } else if (currentMap[next_x][next_y] == 2) {
            // 앞에 장매물이 있으면
            ok = false;
          }
        }
        break;
      case DIRECTION_DOWN:
        next_x = my_y + 1;
        next_y = my_x;
        if (checkPos(next_x, next_y)) {
          if (currentMap[next_x][next_y] == 1 || currentMap[next_x][next_y] == 5) {
            if (checkPos(next_x + 1, next_y)
              && (currentMap[next_x + 1][next_y] == 1 || currentMap[next_x + 1][next_y] == 2)) {
              // 이중 장애물
              ok = false;
            } else if (checkPos(next_x + 1, next_y) && currentMap[next_x + 1][next_y] == 9) {
              currentMap[next_x][next_y] = 0;
              currentMap[next_x + 1][next_y] = 5;
            } else {
              // 장애물 이동
              if (checkPos(next_x + 1, next_y)) {
                if (currentMap[next_x + 1][next_y] == 0) {
                  if (currentMap[next_x][next_y] == 0 || currentMap[next_x][next_y] == 1) {
                    currentMap[next_x][next_y] = 0;
                    currentMap[next_x + 1][next_y] = 1;
                  } else if (currentMap[next_x][next_y] == 5) {
                    currentMap[next_x][next_y] = 9;
                    currentMap[next_x + 1][next_y] = 1;
                  }
                }
              } else {
                ok = false;
              }
            }
          } else if (currentMap[next_x][next_y] == 2) {
            ok = false;
          }
        }
        break;
      case DIRECTION_LEFT:
        next_x = my_y;
        next_y = my_x - 1;
        if (checkPos(next_x, next_y)) {
          if (currentMap[next_x][next_y] == 1 || currentMap[next_x][next_y] == 5) {
            if (checkPos(next_x, next_y - 1)
              && (currentMap[next_x][next_y - 1] == 1 || currentMap[next_x][next_y - 1] == 2)) {
              // 이중 장애물
              ok = false;
            } else if (checkPos(next_x, next_y - 1) && currentMap[next_x][next_y - 1] == 9) {
              currentMap[next_x][next_y] = 0;
              currentMap[next_x][next_y - 1] = 5;
            } else {
              // 장애물 이동
              if (checkPos(next_x, next_y - 1)) {
                if (currentMap[next_x][next_y - 1] == 0) {
                  if (currentMap[next_x][next_y] == 0 || currentMap[next_x][next_y] == 1) {
                    currentMap[next_x][next_y] = 0;
                    currentMap[next_x][next_y - 1] = 1;
                  } else if (currentMap[next_x][next_y] == 5) {
                    currentMap[next_x][next_y] = 9;
                    currentMap[next_x][next_y - 1] = 1;
                  }
                }
              } else {
                ok = false;
              }
            }
          } else if (currentMap[next_x][next_y] == 2) {
            ok = false;
          }
        }
        break;
      case DIRECTION_RIGHT:
        next_x = my_y;
        next_y = my_x + 1;
        if (checkPos(next_x, next_y)) {
          if (currentMap[next_x][next_y] == 1 || currentMap[next_x][next_y] == 5) {
            if (checkPos(next_x, next_y + 1)
              && (currentMap[next_x][next_y + 1] == 1 || currentMap[next_x][next_y + 1] == 2)) {
              // 이중 장애물
              ok = false;
            } else if (checkPos(next_x, next_y + 1) && currentMap[next_x][next_y + 1] == 9) {
              currentMap[next_x][next_y] = 0;
              currentMap[next_x][next_y + 1] = 5;
            } else {
              // 장애물 이동
              if (checkPos(next_x, next_y + 1)) {
                if (currentMap[next_x][next_y + 1] == 0) {
                  if (currentMap[next_x][next_y] == 0 || currentMap[next_x][next_y] == 1) {
                    currentMap[next_x][next_y] = 0;
                    currentMap[next_x][next_y + 1] = 1;
                  } else if (currentMap[next_x][next_y] == 5) {
                    currentMap[next_x][next_y] = 9;
                    currentMap[next_x][next_y + 1] = 1;
                  }
                }
              } else {
                ok = false;
              }
            }
          } else if (currentMap[next_x][next_y] == 2) {
            ok = false;
          }
        }
        break;
    }

    return ok;
  }

  public void setMap(int[][] map1) {
    currentMap = map1;
  }
}