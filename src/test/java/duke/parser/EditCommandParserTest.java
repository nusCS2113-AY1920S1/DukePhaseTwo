package duke.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import duke.command.EditCommand;
import duke.exception.DukeException;

class EditCommandParserTest {

    @Test
    public void filterParseTest_success() throws DukeException {
        EditCommandParser editCommandParser = new EditCommandParser();

        KeywordAndField keywordAndField1 = new KeywordAndField("description", "muji pen");
        KeywordAndField keywordAndField2 = new KeywordAndField("r", "weekly");

        ArrayList<KeywordAndField> keywordAndFields = new ArrayList<KeywordAndField>();
        keywordAndFields.add(keywordAndField1);
        keywordAndFields.add(keywordAndField2);

        EditCommand expectedEditCommand = new EditCommand(Optional.of("cs"), 4, keywordAndFields);
        EditCommand actualEditCommand = editCommandParser.parse(Optional.of("cs"), "4 -description muji pen -r weekly");
        // assertEquals(expectedEditCommand, actualEditCommand);
    }

    @Test
    public void getIndexFromCommand_nonNumericalIndex_failure() {
        EditCommandParser editCommandParser = new EditCommandParser();
        ArrayList<KeywordAndField> keywordAndFields = new ArrayList<KeywordAndField>();
        assertThrows(DukeException.class, () -> {
            editCommandParser.parse(Optional.of("cs"), "he -description muji pen -r weekly");
        });
    }
}
