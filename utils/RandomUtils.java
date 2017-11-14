package com.mygdx.game.martianrun.utils;

import com.mygdx.game.martianrun.enums.EnemyType;

import java.util.Random;

/**
 * @author swanf
 * date 17-11-9 下午10:03
 */

public class RandomUtils {

    public static EnemyType getRandomEnemyType() {
        RandomEnum<EnemyType> reandomEnum = new RandomEnum<EnemyType>(EnemyType.class);
        return reandomEnum.random();
    }

    private static class RandomEnum<E extends Enum> {
        private static final Random RND = new Random();
        private final E[] values;

        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        public E random() {
            return values[RND.nextInt(values.length)];
        }
    }
}
