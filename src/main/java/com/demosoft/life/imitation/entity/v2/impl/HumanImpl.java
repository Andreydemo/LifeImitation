package com.demosoft.life.imitation.entity.v2.impl;

import static com.demosoft.life.imitation.entity.type.HumanType.HUMAN_TYPE_EMPTY;

import com.demosoft.life.imitation.entity.Human;
import com.demosoft.life.imitation.entity.type.HumanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanImpl implements Human {

    HumanType type = HUMAN_TYPE_EMPTY;
    int age;
    int energy;
    int satiety;
    int pregnancy;
    boolean active;

    Map<String, Integer> resources = new ConcurrentHashMap<>();

    @Override
    public void addResource(String name, Integer count) {
        Integer originalCount = resources.computeIfAbsent(name, s -> 0);
        originalCount += count;
        resources.put(name, originalCount);
    }

    @Override
    public void removeResource(String name, Integer count) {
        Integer originalCount = resources.computeIfAbsent(name, s -> 0);
        if (count > originalCount) throw new RuntimeException("invalid count");
        originalCount -= count;

        resources.put(name, originalCount);
    }
}
