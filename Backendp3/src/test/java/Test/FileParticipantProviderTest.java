package Test;

import Classes.Participant;
import Controller.FileParticipantProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileParticipantProviderTest {
    private FileParticipantProvider provider;
    private String activity;

    @BeforeEach
    void setUp() {
    activity = "Badminton Night11";
    provider = new FileParticipantProvider()
    {
        @Override
        public List<Participant> getParticipants(String activityName){
                List<Participant> participants = new ArrayList<>();
        for(int i= 1; i<=12; i++){
            participants.add(new Participant(

                    "User" + i,
                    "Test",
                    "user" + i +"@mail.com",
                    activityName

            ));

            }
            return participants;

    }
    };
    }


    @AfterEach
    void tearDown() {
    }



    @Test
    void indexOfParticipant() {
        Participant target = new Participant(
                "User5",
                "Test",
                "user5@mail.com",
                activity
        );

        int index = provider.indexOfParticipant(activity, target);

        assertEquals(4, index);
    }

    @Test
    void hasWaitingListPromotion() {

        boolean result = provider.hasWaitingListPromotion(
                activity,
                4,
                10,
                true
        );

        assertTrue(result);
    }

    @Test
    void getPromotedParticipant() {
        Participant promoted = provider.getPromotedParticipant(activity, 10);

        assertNotNull(promoted);
        assertEquals("User10", promoted.getFirstName());
    }




    @Test
    void getParticipants_readsExistingProductionMockFile() {
        FileParticipantProvider repo = new FileParticipantProvider();

        List<Participant> participants = repo.getParticipants("Badminton Night11");

        assertFalse(participants.isEmpty(), "Expected participants to be read from file");

        Participant p = participants.get(0);
        assertNotNull(p.getFirstName());
        assertNotNull(p.getEmail());
    }



}