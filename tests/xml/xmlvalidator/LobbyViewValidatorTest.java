package xml.xmlvalidator;

import exceptions.ValidatorException;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class LobbyViewValidatorTest {

    @Test
    void validate() {
        Validator lobbyViewValidator = new LobbyViewValidator();
//        String lobbyViewPath = "data/xml/lobbyViews/standard.xml";
        String lobbyViewPath = "data/xml/lobbyView/lobbyView_v1.xml";
        File lobbyViewFile = new File(lobbyViewPath);
        boolean result = lobbyViewValidator.validate(lobbyViewFile);
        boolean expected = true;
        assertEquals(expected, result);
    }

    @Test
    void validateError() {
        Validator lobbyViewValidator = new LobbyViewValidator();
//        String lobbyViewPath = "data/xml/lobbyViews/standard.xml";
        String lobbyViewPath = "data/xml/lobbyViews/bad_lobbyView.xml";
        File lobbyViewFile = new File(lobbyViewPath);
        try {
            lobbyViewValidator.validate(lobbyViewFile);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

}