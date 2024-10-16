package edu.unac;

import edu.unac.exception.ConsecutiveCharacterException;
import edu.unac.exception.DuplicateSaltException;
import edu.unac.exception.InvalidLengthException;
import edu.unac.exception.RepeatedCharacterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class SaltGeneratorTest {

    private SaltGenerator saltGenerator;

    @BeforeEach
    void setUp() {
        SimpleRandomProvider random = mock(SimpleRandomProvider.class);
        saltGenerator = new SaltGenerator(random);

    }

    @Test
    void testGenerateSaltIfLengthIsLessOf2() throws InvalidLengthException {


        InvalidLengthException noLegalLengthException = assertThrows(InvalidLengthException.class, () -> {
            saltGenerator.generateSalt(1);
        });

        assertEquals("The length must be greater than 2.", noLegalLengthException.getMessage());
    }

    @Test
    void testRepeatedCharacter() throws RepeatedCharacterException, ConsecutiveCharacterException, InvalidLengthException, DuplicateSaltException {

        when(saltGenerator.generateSalt(4)).thenReturn("AAAA");


        RepeatedCharacterException RepeatedCharacter = assertThrows(RepeatedCharacterException.class, () -> {
            saltGenerator.generateSalt(7);

        });

        assertEquals("The salt contains 3 consecutive characters: A", RepeatedCharacter.getMessage());
    }

    @Test
    void testDuplicatedSalt() throws DuplicateSaltException, ConsecutiveCharacterException, InvalidLengthException, RepeatedCharacterException {

        when(saltGenerator.generateSalt(4)).thenReturn("AAAA", "BBBB", "CCCC");

        DuplicateSaltException DuplicatedSalt = assertThrows(DuplicateSaltException.class, () -> {
            saltGenerator.generateSalt(4);
            saltGenerator.generateSalt(4);
            saltGenerator.generateSalt(4);
            saltGenerator.generateSalt(4);

        });

        assertEquals("The salt contains 3 consecutively repeated characters: ", DuplicatedSalt.getMessage());
    }







}
