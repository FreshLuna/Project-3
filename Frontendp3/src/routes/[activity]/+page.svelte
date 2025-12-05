<script lang="ts">
    import { page } from "$app/stores";
    import { onMount } from 'svelte';

    // Activity type based on activities.json
    interface Activity {
        ActivityID: number;
        ActivityName: string;
        ActivityOrganizer?: string;
        TypeOfActivity?: string;
        Instructors?: string;
        DateAndTime?: number;
        Location?: string;
        GenderGroup?: string;
        AgeGroup?: string;
        ActivityCapacity?: number;
        ActivityDescription?: string;
        ActivityDifficulty?: string;
        WaitingListEnabled?: boolean;
        WaitingListCapacity?: number;
        tags?: string[];
        imgUrl?: string | null;
        [key: string]: any;
    }

    // Form state
    let firstName: string = "";
    let lastName: string = "";
    let dateOfBirth: string = "";
    let email: string = "";
    let tosAccept: boolean = false;
    let infoSendAccept: boolean = false;

    // PopUp state
    let isPopUpOpen: boolean = false;

    // FIX: Correctly read params from $page store
    let activity: string = "";
    $: activity = String($page.params?.activity ?? "");

    // Loaded activity object from backend (matching by ID or name)
    let activityData: Activity | null = null;

    function formatDateTimeFromNumber(n: number | string | null | undefined): string {
        if (n == null) return '';
        const s = String(n);
        // Expect YYYYMMDDhhmm or similar
        if (s.length < 8) return s;
        const year = s.slice(0,4);
        const month = s.slice(4,6);
        const day = s.slice(6,8);
        const hour = s.length >= 10 ? s.slice(8,10) : '00';
        const minute = s.length >= 12 ? s.slice(10,12) : '00';
        return `${day}/${month}/${year} kl. ${hour}:${minute}`;
    }

    // --- Client-side validation & cleaning (match backend Verified.java) ---
    function isMissing(s: string | null | undefined): boolean {
        return s == null || String(s).trim() === "";
    }

    function cleanName(input: string | null | undefined): string | null {
        if (input == null) return null;
        const cleaned = String(input).trim().replace(/[^a-zA-Z]/g, "");
        if (cleaned === "") return null;
        return cleaned.charAt(0).toUpperCase() + cleaned.slice(1).toLowerCase();
    }

    function isAlpha(input: string | null | undefined): boolean {
        return input != null && String(input).trim().match(/^[a-zA-Z]+$/) !== null;
    }

    function cleanEmail(email: string | null | undefined): string | null {
        if (email == null) return null;
        return String(email).trim().toLowerCase();
    }

    function isValidEmail(email: string | null | undefined): boolean {
        if (email == null) return false;
        return String(email).trim().match(/^[\w.-]+@[\w.-]+\.[A-Za-z]{2,6}$/) !== null;
    }

    function isValidDateOfBirth(dob: string | null | undefined): boolean {
        if (dob == null) return false;
        return String(dob).match(/^\d{2}\/\d{2}\/\d{4}$/) !== null;
    }

    // Accepts dd/MM/yyyy, returns dd/MM/yyyy or null
    function formatDateToDDMMYYYY(d: string | null | undefined): string | null {
        if (!d) return null;
        const s = String(d).trim();
        if (s.match(/^\d{2}\/\d{2}\/\d{4}$/)) return s;
        const m = s.match(/^(\d{4})-(\d{2})-(\d{2})$/);
        if (m) {
            return `${m[3]}/${m[2]}/${m[1]}`;
        }
        return null;
    }

    //Fetch activities from backend
    onMount(async () => {
        try {
            const res = await fetch('https://localhost:8443/server/activities');
            if (!res.ok) return console.error('Failed to load activities');
            const list = (await res.json()) as Activity[];

            // try matching by numeric ID first
            const param = String($page.params?.activity ?? '');
            let found: Activity | undefined = undefined;
            const asNumber = Number(param);
            if (!isNaN(asNumber)) {
                found = list.find((it) => Number(it.ActivityID) === asNumber);
            }
            if (!found) {
                // try matching by name (decoded)
                const decoded = decodeURIComponent(param || '');
                found = list.find((it) => String(it.ActivityName) === decoded || String(it.ActivityName).toLowerCase() === decoded.toLowerCase());
            }

            activityData = found ?? null;
        } catch (err) {
            console.error('Error loading activity details', err);
        }
    });

    async function submitHandler(): Promise<void> {
        // Clean & validate inputs to match backend Verified
        const cleanedFirst = cleanName(firstName);
        const cleanedLast = cleanName(lastName);
        const cleanedEmail = cleanEmail(email);
        const dobFormatted = formatDateToDDMMYYYY(dateOfBirth);

        const errors: string[] = [];
        if (isMissing(cleanedFirst) || !isAlpha(cleanedFirst)) errors.push('Ugyldigt fornavn');
        if (isMissing(cleanedLast) || !isAlpha(cleanedLast)) errors.push('Ugyldigt efternavn');
        if (!isValidEmail(cleanedEmail)) errors.push('Ugyldig email');
        if (!isValidDateOfBirth(dobFormatted)) errors.push('Ugyldig fødselsdato (DD/MM/YYYY)');
        if (!tosAccept) errors.push('Du skal acceptere vilkårene');

        if (errors.length > 0) {
            alert('Valideringsfejl:\n' + errors.join('\n'));
            return;
        }

        const payload = {
            firstname: cleanedFirst,
            lastname: cleanedLast,
            dateOfBirth: dobFormatted,
            email: cleanedEmail,
            tosAccept: tosAccept,
            infoSendAccept: infoSendAccept,
            // ensure we send the activity name (not the numeric id)
            activity: activityData ? activityData.ActivityName : decodeURIComponent(activity)
        } as Record<string, unknown>;

        try {
            const res = await fetch('https://localhost:8443/server/participants', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            const text = await res.text();

            if (!res.ok) {
                alert('Tilmelding mislykkedes: ' + (text || res.status));
                return;
            }

            alert('Tilmelding sendt: ' + text);

            // Reset form
            firstName = "";
            lastName = "";
            dateOfBirth = "";
            email = "";
            tosAccept = false;
            infoSendAccept = false;

        } catch (err) {
            console.error(err);
            alert("Netværksfejl ved afsendelse. Tjek server og HTTPS-indstillinger.");
        }
    }

    // Sveltes own functions to open and close pop up
    // Instead of using document.getElementById etc, we use thise. 
    // These functions are called at line 145-154. When the buttons are clicked, the popup will be set to either true or false, meaning it will open or close. 
    function handleOpenPopUp() {
        isPopUpOpen = true;
    }

    function handleClosePopUp() {
        isPopUpOpen = false;
    }

</script>


<!-- <h1>Aalborg Try Out: Activity Test!!! (vi prøver igen)</h1> -->
<div class="container">

    <!-- Left half: Activity Image -->
    <div class="halfPageBox">
        {#if activityData}
            <!-- If you later add local assets, replace src with resolved local URL -->
            <img class="activityImage" src={activityData.imgUrl ?? "https://via.placeholder.com/800x400?text=Activity+image"} alt="{activityData.ActivityName}"/>
        {:else}
            <div class="activityImage" style="background:#ddd;border-radius:10px;height:100%;min-height:220px;"></div>
        {/if}
    </div>

    <!-- Right half: Activity info and Description box -->
    <div class="halfPageBox">
        <div class="nameOrganizerInviteBox">

            <!-- Left: Name + Organizer -->
            <div class="noiRightBox">
                <h2>{activityData ? activityData.ActivityName : $page.params.activity}</h2>
                <p>{activityData ? activityData.ActivityOrganizer : '(Organisator)'}</p>
            </div>

            <!-- Right: Invite button -->
            <div class="noiLeftBox">
                <button>Invitér en ven</button>
            </div>
        </div>

        <div class="activityInfoTable">
            <table>
                <tbody>
                    <tr>
                                <td>
                                    <b>Aktivitet</b><br>
                                    {activityData ? activityData.ActivityName : '—'}
                                </td>
                                <td>
                                    <b>Instruktør</b><br>
                                    {activityData ? activityData.Instructors : '—'}
                                </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Tidspunkt</b><br>
                            {activityData ? formatDateTimeFromNumber(activityData.DateAndTime) : '—'}
                        </td>
                        <td>
                            <b>Adresse og mødested</b><br>
                            {activityData ? activityData.Location : '—'}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Køn</b><br>
                            {activityData ? activityData.GenderGroup : '—'}
                        </td>
                        <td>
                            <b>Aldersgruppe</b><br>
                            {activityData ? activityData.AgeGroup : '—'}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- DESCRIPTION BOX (outside) -->
        <div class="innerBox">
                        <h3>Beskrivelse</h3>
                        <p>{activityData ? activityData.ActivityDescription : 'Beskrivelse ikke tilgængelig.'}</p>
            <div class="btn">Læs mere</div>
        </div>

        <!-- SIGN UP BUTTON (outside) -->
        
            <button class="signUpBtn" on:click={handleOpenPopUp}>Tilmeld til aktivitet</button> <!-- When the button is clicked, the pop up will open -->
        

        <!-- POP UP BOX (inside) -->
        <div class="popUpBox" class:open={isPopUpOpen}> <!-- to connect to the css -->
            <div class="popUpSignUp">

            <div class="innerBox">
                
                    <button class="closeBtn" on:click={handleClosePopUp}>Luk</button> <!-- When the button within the popup is clicked, the pop up will close -->

                    <div class="popUpTitle">
                        <h2>Tilmeld dig gratis til <b>{activityData ? activityData.ActivityName : decodeURIComponent(activity)}</b>!</h2>
                    </div>
                
                <form class="formLayout" on:submit|preventDefault={submitHandler}>

                    <div class="formRowNameDOB">
                        <div class="formField">
                            <label for="activityName">Vælg tidspunkt</label>
                            <!-- Show the scheduled activity date/time (read-only) so the activity id isn't overwritten -->
                            <input type="text" id="activityName" name="activityName" readonly value={activityData ? formatDateTimeFromNumber(activityData.DateAndTime) : ''} />
                        </div>
                    </div>

                <!-- Row 1: two quarter-width + one half-width inputs -->
                    <div class="formRowNameDOB">
                        <div class="formField half">
                            <label for="firstName">Fornavn</label>
                            <input type="text" id="firstName" name="firstName" required bind:value={firstName}>
                        </div>

                        <div class="formField half">
                            <label for="lastName">Efternavn</label>
                            <input type="text" id="lastName" name="lastName" required bind:value={lastName}>
                        </div>
                    </div>

                <!-- Row 2: two half-width inputs -->
                    <div class="formRowNameDOB">
                        <div class="formField half">
                            <label for="email">Email</label>
                            <input type="text" id="email" name="email" required bind:value={email}>
                        </div>

                        <div class="formField half">
                            <label for="dateOfBirth">Fødselsdato</label>
                            <input type="date" id="dateOfBirth" name="dateOfBirth" bind:value={dateOfBirth}>
                        </div>
                    </div> 

                 <!-- Checkboxes -->
                    <div class="checkboxRow">
                        <input type="checkbox" id="tosAccept" name="tosAccept" required bind:checked={tosAccept} />
                        <label for="tosAccept">Jeg accepterer Aalborg Try Out's Terms of Service</label>
                    </div>

                    <div class="checkboxRow">
                        <input type="checkbox" id="infoSendAccept" name="infoSendAccept" bind:checked={infoSendAccept} />
                        <label for="infoSendAccept">Tillad Aalborg Try Out at sende mig notifikationer med information om aktiviteten</label>
                    </div>

                    <button type="submit" class="btn">Tilmeld til aktivitet</button> <!--class="signUpButton"-->
                </form>
    
            </div>
            </div> 
        </div>


    </div>
</div>

<style>
    /* General container */
    .container {
        display: flex;
        gap: 1%;
        margin-top: 2%;
        margin-left: 10%;
        margin-right: 10%;
        font-family: 'Trebuchet MS'; 
    }
    
    .halfPageBox {
        flex: 1;
        padding: 1%;
        border-radius: 25px;
        display: absolute;
    }

    .activityImage{
        width: 100%;
        border-radius: 10px; 
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
        border-radius: 10px;
        border: 2px solid black;
        padding: 1rem;
        display: flex;
        flex-direction: column;
        gap: 1rem;
        /* margin-top: 1rem;
        margin-bottom: 1rem; */
    }

    .btn {
        background-color: #6E479B;
        border: none;
        color: white;
        padding: 10px 15px;
        text-align: center;
        font-size: 16px;
        cursor: pointer;
        border-radius: 5px;
        
    }

    .btn:hover {
        background-color: #5e3b85ff;
    }

    .closeBtn{
        background-color: #6E479B;
        border: none;
        color: white;
        padding: 10px 15px;
        text-align: right; 
        width: 8%; 
        position: relative; 
        left: 610px;
        font-size: 16px;
        cursor: pointer;
        border-radius: 5px;
       
    }

    .closeBtn:hover {
        background-color: #5e3b85ff;
    }

    .signUpBtn{
        background-color: #6E479B;
        border: none;
        border-radius: 5px;
        color: white;
        text-align: center;
        font-size: 16px;
        padding: 0.75rem 1.5rem;
        cursor: pointer;
        margin-top: 20px;
        margin-left: 33px; 
        width: 500px; 
        position: relative; 

    }

    .signUpBtn:hover {
        background-color: #5e3b85ff;
    }


/* **** POP UP WINDOW CSS STYLE **** */
    .popUpTitle{
        margin-top: -66px;
        margin-bottom: 20px;
    }

    .popUpBox{
        background-color: rgba(0, 0, 0, 3); 
        opacity: 0; 
        position: fixed; 
        top: 0; 
        left: 0; 
        right: 0; 
        bottom: 0; 
        transition: all 0.3s ease-in-out; 
        z-index: -1;

        /* to keep the inner box in the center */
        display: flex; 
        align-items: center; 
        justify-content: center; 
    }

    /* the pop up */
    .popUpBox.open{
        opacity: 1; 
        z-index: 999; 
    }

    .popUpSignUp{
        background-color: aliceblue;
        border-radius: 10px; 
        box-shadow: 0 1px 4px rgba(0, 0, 0, 3); 
        padding: 5px 5px; 
        text-align: center; 
        width: 700px; 
        height: auto; 
    }
/* ************************** */


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

    /* .formRowEmailPN {
        display: center;
        flex-wrap: wrap;
        /* gap: 1rem; */
        /* justify-content: space-between;
    } */

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
        margin: 0.5rem; 
    }

/* ----------------------------------- */

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
