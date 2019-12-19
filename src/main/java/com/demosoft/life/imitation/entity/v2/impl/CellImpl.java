package com.demosoft.life.imitation.entity.v2.impl;

import static com.demosoft.life.imitation.entity.type.LandscapeType.LANDSCAPE_TYPE_EMPTY;

import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.Human;
import com.demosoft.life.imitation.entity.Landscape;
import com.demosoft.life.imitation.entity.Plant;
import com.demosoft.life.imitation.entity.impl.UcfCoder;
import com.demosoft.life.logic.math.XMath;
import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
@Data
@NoArgsConstructor
public class CellImpl implements Cell {

    private int x;
    private int y;
    private Human human;
    private Plant plant;
    private Landscape landscape = new LandscapeImpl(LANDSCAPE_TYPE_EMPTY);

    public Optional<Human> getHuman() {
        return Optional.ofNullable(human);
    }

    public Optional<Plant> getPlant() {
        return Optional.ofNullable(plant);
    }

    public Landscape getLandscape() {
        return landscape;
    }
}
