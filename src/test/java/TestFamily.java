import com.example.Family;
import com.example.Geektrust;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Paths;

import static com.example.Constants.INIT_FILE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestFamily {

    private static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
    private static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";
    private static final String SATYA = "Satya";

    private static Family family;

    @BeforeAll
    static void setUpBeforeClass() {
        family = new Family();
        Geektrust sol = new Geektrust();
        String pathInitFile = Paths.get(INIT_FILE_PATH).toAbsolutePath().toString();
        sol.fileToProcess(family);
    }

    @Test
    public void addChildAllNullValues() {
        assertEquals(PERSON_NOT_FOUND, family.addChild(null, null, null));
    }

    @Test
    public void addChildNameNullValues() {
        assertEquals(CHILD_ADDITION_FAILED, family.addChild(SATYA, null, null));
    }

    @Test
    public void addChildGenderNullValues() {
        assertEquals(CHILD_ADDITION_FAILED, family.addChild(SATYA, "Ketu", null));
    }

    @Test
    public void addChildThroughFather() {
        assertEquals(CHILD_ADDITION_FAILED, family.addChild("Aras", "Ketu", "Male"));
    }

    @Test
    public void addChildThroughAbsentMember() {
        assertEquals(PERSON_NOT_FOUND, family.addChild("Aries", "Ketu", "Male"));
    }

    @Test
    public void addChildSuccess() {
        assertEquals("CHILD_ADDITION_SUCCEEDED", family.addChild(SATYA, "Ketu", "Male"));
    }

    @Test
    public void getRelationshipAllParamsNull() {
        assertEquals(PERSON_NOT_FOUND, family.getRelationship(null, null));
    }

    @Test
    public void getRelationshipRelationNull() {
        assertEquals("PROVIDE VALID RELATION", family.getRelationship(SATYA, null));
    }

    @ParameterizedTest
    @CsvSource({
            "Satya,WIFE,NONE",
            "Satya,Paternal-Uncle,NONE",
            "Kriya,Paternal-Uncle,Asva",
            "Asva,Maternal-Uncle,Chit Ish Vich Aras",
            "Tritha,Paternal-Aunt,Satya",
            "Yodhan,Maternal-Aunt,Tritha",
            "Satvy,Sister-In-Law,Atya",
            "Vyas,Siblings,Asva Atya",
            "Queen Anga,Son,Chit Ish Vich Aras",
            "Queen Anga,Daughter,Satya",
            "Chit,Siblings,Ish Vich Aras Satya"
    })
    public void getRelationshipValuesFromCsvFile(String memberName, String relation, String expected) {
        String actual = family.getRelationship(memberName, relation);
        assertEquals(expected, actual);
    }
}