<script>
    import { page } from "$app/stores";

    // Form state
    let firstName = "";
    let lastName = "";
    let dateOfBirth = "";
    let email = "";
    let phoneNumber = "";
    let tosAccept = false;
    let infoSendAccept = false;

    // FIX: Correctly read params from $page store
    let activity = "";
    // @ts-ignore
    $: activity = $page.params.activity;

    async function submitHandler() {
        const payload = {
            firstname: firstName,
            lastname: lastName,
            dateOfBirth: dateOfBirth,
            email: email,
            phoneNumber: phoneNumber,
            tosAccept: tosAccept,
            infoSendAccept: infoSendAccept,
            activity: activity
        };

        try {
            const res = await fetch('https://localhost:8443/server/participants', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!res.ok) {
                alert('Failed to send sign-up: ' + res.status);
                return;
            }

            const text = await res.text();
            alert('Sign-up sent: ' + text);

            // Reset form
            firstName = "";
            lastName = "";
            dateOfBirth = "";
            email = "";
            phoneNumber = "";
            tosAccept = false;
            infoSendAccept = false;

        } catch (err) {
            console.error(err);
            alert("Network error sending sign-up. Check server and HTTPS settings.");
        }
    }
</script>


<!-- <h1>Aalborg Try Out: Activity Test!!! (vi prøver igen)</h1> -->
<div class="container">

    <!-- Left half: Activity info -->
    <div class="halfPageBox">
        <div class="nameOrganizerInviteBox">
            <!-- Left: Name + Organizer -->
            <div class="noiRightBox">
                <h2>Name of activity ({$page.params.activity})</h2>
                <p>Organizer</p>
            </div>

            <!-- Right: Invite button -->
            <div class="noiLeftBox">
                <button>Invite a friend</button>
            </div>
        </div>

        <div class="activityInfoTable">
            <table>
                <tbody>
                    <tr>
                        <td>
                            <b>Activity</b><br>
                            Example Activity
                        </td>
                        <td>
                            <b>Instructor</b><br>
                            Lars Larsen
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Time and Date</b><br>
                            17:00 10th November 2025
                        </td>
                        <td>
                            <b>Place</b><br>
                            Selma Lagerlöfsvej 300, 9220 Aalborg
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Gender</b><br>
                            Any
                        </td>
                        <td>
                            <b>Age Group</b><br>
                            15-25
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Right half: Sign-up & description -->
    <div class="halfPageBox">

        <div class="innerBox">
            <h3>Sign up for FREE to the activity</h3>
            <p>Optional custom message to display</p>

            <form class="formLayout" on:submit|preventDefault={submitHandler}>

                <!-- Row 1: two quarter-width + one half-width inputs -->
                <div class="formRowNameDOB">
                    <div class="formField quarter">
                        <label for="firstName">First Name</label>
                        <input type="text" id="firstName" name="firstName" bind:value={firstName}>
                    </div>

                    <div class="formField quarter">
                        <label for="lastName">Last Name</label>
                        <input type="text" id="lastName" name="lastName" bind:value={lastName}>
                    </div>

                    <div class="formField half">
                        <label for="dateOfBirth">Date of Birth</label>
                        <input type="text" id="dateOfBirth" name="dateOfBirth" bind:value={dateOfBirth}>
                    </div>
                </div>

                <!-- Row 2: two half-width inputs -->
                <div class="formRowEmailPN">
                    <div class="formField half">
                        <label for="email">Email</label>
                        <input type="text" id="email" name="email" required bind:value={email}>
                    </div>

                    <div class="formField half">
                        <label for="phoneNumber">Phone Number</label>
                        <input type="text" id="phoneNumber" name="phoneNumber" required bind:value={phoneNumber}>
                    </div>
                </div>

                <!-- Checkboxes -->
                <div class="checkboxRow">
                    <input type="checkbox" id="tosAccept" name="tosAccept" bind:checked={tosAccept} />
                    <label for="tosAccept">I accept Aalborg Try Out's Terms of Service</label>
                </div>

                <div class="checkboxRow">
                    <input type="checkbox" id="infoSendAccept" name="infoSendAccept" bind:checked={infoSendAccept} />
                    <label for="infoSendAccept">Allow Aalborg Try Out to send me information such as notifications</label>
                </div>

                <button type="submit" class="signUpButton">Sign up for activity</button>
            </form>
        </div>

        <div class="innerBox">
            <h3>Description</h3>
            <p>Placeholder description</p>
            <button>Read more</button>

            <h4>Difficulty</h4>
            <p>How difficult the activity is</p>
        </div>

    </div>
</div>

<br><br>

<p>Her er også lidt tekst under det hele. Hvilken aktivitet er dette? Det er [{$page.params.activity}]!!!!</p>

<style>
    /* General container */
    .container {
        display: flex;
        gap: 2%;
        margin-left: 10%;
        margin-right: 10%;
        font-family: 'Trebuchet MS'
    }

    .halfPageBox {
        flex: 1;
        padding: 1%;
        border-radius: 25px;
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }

    /* Invite & name box */
    .nameOrganizerInviteBox {
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 1rem;
    }

    .noiRightBox h2 {
        margin: 0;
    }

    .noiLeftBox button {
        padding: 0.5rem 1rem;
        border-radius: 6px;
        cursor: pointer;
    }

    /* Table styles */
    .activityInfoTable table {
        width: 100%;
        border-collapse: collapse;
        table-layout: fixed;
    }

    .activityInfoTable td {
        padding: 10px;
        vertical-align: top;
    }

    .activityInfoTable tr {
        border-bottom: 2px solid black;
    }

    .activityInfoTable tr:last-child {
        border-bottom: none;
    }

    /* Inner box (description & form container) */
    .innerBox {
        border-radius: 25px;
        border: 2px solid black;
        padding: 1rem;
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }

    /* Form layout */
    .formLayout {
        display: flex;
        flex-direction: column;
        gap: 1rem;
        width: 100%;
    }

    .formRowNameDOB {
        display: flex;
        flex-wrap: wrap;
        /* flex-shrink: 0; */
        /* gap: 1rem; */
        justify-content: space-between;
    }

    .formRowEmailPN {
        display: flex;
        flex-wrap: wrap;
        /* gap: 1rem; */
        justify-content: space-between;
    }

    .formField {
        display: flex;
        flex-direction: column;
        flex: 1;
    }

    .formField input,
    .formField select {
        padding: 0.5rem;
        border: 1px solid black;
        border-radius: 4px;
        box-sizing: border-box;
        min-width: 0;
    }

    /* Width classes */
    .formField.half {
        /* flex: 1 2;
        width: 50%; */
        /* flex: 0 0 50%; */

        min-width: 0;
        /* flex: 2; */

        /* flex: 0 0 calc(50% - 1rem); */
        flex: 0 0 calc((100% - 1rem) / 2);
    }

    .formField.quarter {
        /* flex: 1 1 2;
        width: 50%;
        flex-shrink: 0; */
        /* flex: 0 0 25%; */

        min-width: 0;
        /* flex: 1; */

        /* flex: 0 0 calc(25% - 0.5rem); */
        flex: 0 0 calc((100% - 2rem) / 4);
    }

    /* Checkbox rows */
    .checkboxRow {
        display: flex;
        align-items: center;
        gap: 0.5rem;
    }

    /* Sign-up button */
    .signUpButton {
        padding: 0.75rem 1.5rem;
        border-radius: 6px;
        cursor: pointer;
        align-self: flex-start;
    }

    /* Responsive adjustments */
    @media (max-width: 800px) {
        .container {
            flex-direction: column;
        }

        .formRow {
            flex-direction: column;
        }

        .formField.half,
        .formField.quarter {
            flex: 1 1 100%;
        }
    }

    button {
        font-family: 'Trebuchet MS'
    }

    /* REPLACE ALMOST ALL FLEX WITH GRID BECAUSE FLEX BAD */
    /* remember media queries */
</style>
