<script>
    // stupid comment
    import { page } from "$app/stores";
</script>

<!-- <h1>Aalborg Try Out: Activity Test!!! (vi prøver igen)</h1> -->

<div class="leftSide">
    <div class="nameOrganizerInviteBox">
        <!-- name above organizer on left half, button on right half -->
        <div class="noiRightBox">
            <h2>Name of activity ({$page.params.activity})</h2>
            <p>Organizer</p>
        </div>
        <div class="noiLeftBox">
            <button>Invite a friend</button>
        </div>
    </div>

    <div class="infoTable">
        <table>
            <tbody>
                <tr>
                    <!-- what and who -->
                    <td>Activity</td>
                    <td>Instructor</td>
                </tr>
                <tr>
                    <!-- when and where -->
                    <td>Time and date</td>
                    <td>Place</td>
                </tr>
                <tr>
                    <!-- for who -->
                    <td>Gender</td>
                    <td>Age group</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<div class="rightSide">
    <div class="signUpForm">
        <h3>Sign up for <b>FREE</b> to the activity</h3>
        <p>Optional custom message to display</p>
        <div>
            <div>
                Choose a time of day
            </div>
            <div>
                First name
                Last name
                Date of birth
            </div>
            <div>
                Email
                Phone number
            </div>
            <div>
                Place of education
            </div>
        </div>
        <div>
            <input
                type="checkbox"
                class="tosAccept"
                name="tosAccept">
            <label for="tosAccept">I accept Aalborg Try Out's Terms of Service</label>
        </div>
        <div>
            <input
                type="checkbox"
                class="infoSendAccept"
                name="infoSendAccept">
            <label for="infoSendAccept">Allow Aalborg Try Out to send me information such as notifications</label>
        </div>
        <button class="signUpButton">Sign up for waiting list</button>
    </div>
    <div class="description">
        <h3>Description</h3>
        <p>Placeholder description</p>
        <button>Read more</button>
        <h4>Difficulty</h4>
        <p>How difficult the activity is</p>
    </div>
</div>

<br/><br/>

her er også lidt tekst under det hele

Hvilken aktivitet er dette? Det er [{$page.params.activity}]!!!! <!-- aktivitet1, aktivitet2, aktivitet3 -->

<style> /* change to global stylesheet **later** */
    .leftSide {
        background-color: rgba(255, 0, 100, 0.3);
        width: 47%;
        margin: 1%;
        position: relative;
        display: inline-block;
    }
    .rightSide {
        background-color: rgba(0, 200, 255, 0.3);
        width: 47%;
        margin: 1%;
        position: relative;
        display: inline-block;
    }
    .signUpButton {
        width: 90%;
        height: 20%;
        text-align: center;
    }
</style>