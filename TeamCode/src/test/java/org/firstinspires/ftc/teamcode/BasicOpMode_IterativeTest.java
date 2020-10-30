package org.firstinspires.ftc.teamcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class BasicOpMode_IterativeTest {

    private BasicOpMode_Iterative testObject = new BasicOpMode_Iterative();

    @Test
    public void testNoValues() {
        EngineValues engineValues = testObject.determineEnginePower(0, 0, 0);
        assertEquals(0, engineValues.leftBackPower, 0.0);
        assertEquals(0, engineValues.leftFrontPower, 0.0);
        assertEquals(0, engineValues.rightBackPower, 0.0);
        assertEquals(0, engineValues.rightFrontPower, 0.0);
    }

    @Test
    public void testGoForward() {
        EngineValues engineValues = testObject.determineEnginePower(0, 1, 0);
        assertEquals(1, engineValues.leftBackPower, 0.0);
        assertEquals(1, engineValues.leftFrontPower, 0.0);
        assertEquals(1, engineValues.rightBackPower, 0.0);
        assertEquals(1, engineValues.rightFrontPower, 0.0);
    }

    @Test
    public void testGoBackward() {
        EngineValues engineValues = testObject.determineEnginePower(0, -1, 0);
        assertEquals(-1, engineValues.leftBackPower, 0.0);
        assertEquals(-1, engineValues.leftFrontPower, 0.0);
        assertEquals(-1, engineValues.rightBackPower, 0.0);
        assertEquals(-1, engineValues.rightFrontPower, 0.0);
    }

    @Test
    public void testGoLeft() {
        EngineValues engineValues = testObject.determineEnginePower(-1, 0, 0);
        assertEquals(1, engineValues.leftBackPower, 0.0);
        assertEquals(-1, engineValues.leftFrontPower, 0.0);
        assertEquals(-1, engineValues.rightBackPower, 0.0);
        assertEquals(1, engineValues.rightFrontPower, 0.0);
    }

    @Test
    public void testGoRight() {
        EngineValues engineValues = testObject.determineEnginePower(1, 0, 0);
        assertEquals(-1, engineValues.leftBackPower, 0.0);
        assertEquals(1, engineValues.leftFrontPower, 0.0);
        assertEquals(1, engineValues.rightBackPower, 0.0);
        assertEquals(-1, engineValues.rightFrontPower, 0.0);
    }

    @Test
    public void testTurnLeft() {
        EngineValues engineValues = testObject.determineEnginePower(0, 0, -1);
        assertEquals(-1, engineValues.leftBackPower, 0.0);
        assertEquals(-1, engineValues.leftFrontPower, 0.0);
        assertEquals(1, engineValues.rightBackPower, 0.0);
        assertEquals(1, engineValues.rightFrontPower, 0.0);
    }


    @Test
    public void testTurnRight() {
        EngineValues engineValues = testObject.determineEnginePower(0, 0, 1);
        assertEquals(1, engineValues.leftBackPower, 0.0);
        assertEquals(1, engineValues.leftFrontPower, 0.0);
        assertEquals(-1, engineValues.rightBackPower, 0.0);
        assertEquals(-1, engineValues.rightFrontPower, 0.0);
    }
}