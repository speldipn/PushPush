package org.androidtown.pushpush;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
  List<int[][]> mapList;
  private int mapIndex;

  int map[][][] = {
    {
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 9, 9, 9, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    },
    {
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 9, 2, 2, 2, 2, 9, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    },
    {
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {2, 0, 2, 2, 2, 2, 2, 2, 0, 0},
      {2, 0, 0, 0, 0, 0, 0, 2, 2, 2},
      {2, 2, 1, 2, 2, 2, 0, 0, 0, 2},
      {2, 0, 0, 0, 1, 0, 0, 1, 0, 2},
      {2, 0, 9, 9, 2, 0, 1, 0, 2, 2},
      {2, 2, 9, 9, 2, 0, 0, 0, 2, 0},
      {0, 0, 2, 2, 2, 2, 2, 2, 2, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    }
  };

  public GameMap() {
    this.mapList = new ArrayList<>();
    this.mapIndex = (-1);
    for (int i = 0; i < map.length; ++i) {
      mapList.add(map[i]);
    }
  }

  public int[][] nextMap() {
    if (++mapIndex >= mapList.size()) {
      return null;
    }
    return mapList.get(mapIndex);
  }

  public boolean hasNextMap() {
    if((mapIndex + 1) >= mapList.size()) {
      return false;
    }
    return true;
  }

}
