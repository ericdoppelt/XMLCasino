package xml.xmlvalidator;

public class PlayerValidator extends Validator {

    private static final String VALIDATION_FILE = "data/schema/deckschema.xsd";

    public PlayerValidator() {
        super(VALIDATION_FILE);
    }
}