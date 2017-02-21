package com.demosoft.life.infra;

import com.demosoft.life.imitation.entity.Cell;

import static com.demosoft.life.imitation.entity.impl.MapFactoryImpl.mapSize;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
public class MapLoader {


    public static final String MAP_PATH = "/data/test-data.dat";

    public Cell[][] load() {
        long[][] data = new long[mapSize][mapSize];
        Cell[][] cells = new Cell[mapSize][mapSize];
       /* ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream((getClass().getResourceAsStream(MAP_PATH)));
            data = (long[][]) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                cells[x][y] = new CellImpl(data[x][y], x, y);
            }
        }*/
        return cells;
    }
}
