package edu.hitsz.aircraft;

import static org.junit.jupiter.api.Assertions.*;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HeroAircraftTest {
    private HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getInstance();
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @Test
    @DisplayName("Test getInstance method")
    void getInstance_test() {
        HeroAircraft heroAircraft_1 = HeroAircraft.getInstance();
        assertSame(heroAircraft, heroAircraft_1);
        int hashCode_heroAircraft = System.identityHashCode(heroAircraft);
        int hashCode_heroAircraft_1 = System.identityHashCode(heroAircraft_1);
        System.out.println("HeroAircraft memory address: " + Integer.toHexString(hashCode_heroAircraft));
        System.out.println("HeroAircraft_1 memory address: " + Integer.toHexString(hashCode_heroAircraft_1));
        assertEquals(hashCode_heroAircraft, hashCode_heroAircraft_1);
    }

    @Test
    @DisplayName("Test shoot method")
    void shoot_test() {
        System.out.println("--Test shoot Method --");
        assertFalse(heroAircraft.shoot().isEmpty(), "bullet list should not be empty");
        assertEquals(1, heroAircraft.shoot().size());
        System.out.println("bullet list size:" + heroAircraft.shoot().size());
    }
    @Test
    @DisplayName("Test decreaseHp method")
    void decreaseHp_test(){
        System.out.println("--Test decreaseHp Method --");
        System.out.println("heroAircraft hp " + heroAircraft.getHp());
        int decrease = 10;
        heroAircraft.decreaseHp(decrease);
        assertEquals(90,heroAircraft.getHp());
        System.out.println("heroAircraft hp " + heroAircraft.getHp());
    }

    @Test
    @DisplayName("Test crash method")
    void crash_test(){
        System.out.println("--Test crash Method --");
        BaseBullet bullet = new EnemyBullet(heroAircraft.getLocationX(), heroAircraft.getLocationY(),1,1,1 );
        assertTrue(heroAircraft.crash(bullet));
        System.out.println(heroAircraft.crash(bullet));
    }
}