package org.androidtown.pushpush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

  private static int GRID_WIDTH = 0;
  private static int GRID_HEIGHT = 0;

  float stageWidth;
  float stageHeight;
  float unit;

  FrameLayout container;
  Stage stage;
  Player player;
  GameMap gameMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initGame();
    initStage();
    initPlayer();
  }

  public void initGame() {
    DisplayMetrics metrics = getResources().getDisplayMetrics();
    stageWidth = metrics.widthPixels;
    stageHeight = stageWidth;
    // 전체 격자 사이즈 설정
    setGridSize(10, 10);
    unit = getUnitSize(stageWidth, stageHeight);
    gameMap = new GameMap();
  }

  private void initStage() {
    container = findViewById(R.id.container);
    stage = new Stage(this);
    stage.setConfig(GRID_WIDTH, unit);
    stage.setMap(gameMap.map1);
    container.addView(stage);

  }

  private void initPlayer() {
    player = new Player();
    stage.addPlayer(player);
  }

  public void setGridSize(int x, int y) {
    GRID_WIDTH = x;
    GRID_HEIGHT = y;
  }

  public float getUnitSize(float x, float y) {
    return x / GRID_WIDTH;
  }

  public void control(View v) {
    switch (v.getId()) {
      case R.id.btnUp: stage.move(stage.DIRECTION_UP); break;
      case R.id.btnDown: stage.move(stage.DIRECTION_DOWN); break;
      case R.id.btnLeft: stage.move(stage.DIRECTION_LEFT); break;
      case R.id.btnRight: stage.move(stage.DIRECTION_RIGHT); break;
    }
  }
}