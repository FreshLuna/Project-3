<script lang="ts">
import { page } from '$app/stores';
import { onMount, tick } from 'svelte';
import { loadActivity } from '$lib/utils/SingleActivity';
import type { Activity } from '$lib/types/Activities';


let firstName = '';
let lastName = '';
let dateOfBirth = '';
let email = '';
let tosAccept = false;
let infoSendAccept = false;

let isPopUpOpen = false;
let activity: Activity | null = null;
let loading = true;
let error: string | null = null;
let formattedDate = "loading"

// Reactive slug from route param
let slug = Number($page.params.activity); // convert to number
console.log('$page.params.activity:', $page.params.activity, 'slug:', slug);

onMount(async () => {
    try {
        if (!isNaN(slug)) {
            activity = await loadActivity(slug);
            const date = activity.date.toString();
            const year = date.slice(0, 4);    // 2025
            const month = date.slice(4, 6);   // 03
            const day = date.slice(6, 8);     // 10
            const hour = date.slice(8, 10);   // 10
            const minute = date.slice(10, 12);// 30
            formattedDate = `${year}Y ${month}M ${day}D ${hour}:${minute}`;
        } else {
            throw new Error('Invalid activity ID');
        }
    } catch (err) {
        console.error(err);
        error = 'Failed to load activity.';
    } finally {
        loading = false;
    }
});


async function submitHandler() {
    if (!activity) return;

    const payload = {
        firstname: firstName,
        lastname: lastName,
        dateOfBirth,
        email,
        tosAccept,
        infoSendAccept,
        activity: activity.title+activity.id
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

        alert('Sign-up sent successfully!');
        firstName = lastName = dateOfBirth = email = '';
        tosAccept = infoSendAccept = false;
    } catch (err) {
        console.error(err);
        alert('Network error sending sign-up.');
    }
}

    let copied = false;

    async function copyLink() {
        try {
            const currentUrl = window.location.href; // Get current page URL
            await navigator.clipboard.writeText(currentUrl);
            copied = true;
            await tick(); // Wait for DOM update
            setTimeout(() => {
                copied = false;
            }, 2000); // Hide message after 2 seconds
        } catch (err) {
            console.error("Failed to copy: ", err);
        }
    }

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
        <img class="activityImage" src="{activity ? activity.imgUrl : 'Indlæser...'}" alt="activityImage"/>
    </div>

    <!-- Right half: Activity info and Description box -->
    <div class="halfPageBox">
        <div class="nameOrganizerInviteBox">

            <!-- Left: Name + Organizer -->
            <div class="noiRightBox">
                <h2>{activity ? activity.title : 'Indlæser...'} </h2>
                <p>{activity ? activity.organization : 'Indlæser...'}</p>
            </div>

            <!-- Right: Invite button -->
            <div class="noiLeftBox">
                <button on:click={copyLink}>{copied ? 'Link kopieret!' : 'Invitér en ven'}</button>
</div>
        </div>

        <div class="activityInfoTable">
            <table>
                <tbody>
                    <tr>
                        <td>
                            <b>Aktivitet</b><br>
                            {activity ? activity.title : 'Indlæser...'}
                        </td>
                        <td>
                            <b>Instruktør</b><br>
                            {activity ? activity.instructors : 'Indlæser...'}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Tidspunkt</b><br>
                            {activity ? activity.formattedDate : 'Indlæser...'}
                        </td>
                        <td>
                            <b>Adresse og mødested</b><br>
                            {activity ? activity.location : 'Indlæser...'}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Køn</b><br>
                            {activity ? activity.genderGroup : 'Indlæser...'}
                        </td>
                        <td>
                            <b>Aldersgruppe</b><br>
                            {activity ? activity.age : 'Indlæser...'}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- DESCRIPTION BOX (outside) -->
        <div class="innerBox">
            <h3>Beskrivelse</h3>
            <p>{activity ? activity.description : 'Indlæser...'}</p>
        </div>

        <!-- SIGN UP BUTTON (outside) -->
        
            <button class="signUpBtn" on:click={handleOpenPopUp}>Tilmeld til aktivitet</button> <!-- When the button is clicked, the pop up will open -->
        

        <!-- POP UP BOX (inside) -->
        <div class="popUpBox" class:open={isPopUpOpen}> <!-- to connect to the css -->
            <div class="popUpSignUp">

            <div class="innerBox">
                
                    <button class="closeBtn" on:click={handleClosePopUp}>Luk</button> <!-- When the button within the popup is clicked, the pop up will close -->

                    <div class="popUpTitle">
                        <h2>Tilmeld dig gratis til <b>{activity ? activity.title : 'Indlæser...'}</b>!</h2>
                    </div>
                
                <form class="formLayout" on:submit|preventDefault={submitHandler}>

                    <div class="formRowNameDOB">
                        <div class="formField">
                            <label for="activityName">Tidspunkt</label>
                            <b>{activity ? activity.formattedDate : 'Indlæser...'}</b>
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
