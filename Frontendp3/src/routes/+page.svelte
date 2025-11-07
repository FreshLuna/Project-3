<head>
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
  />
</head>

<script>
    import aalborgtryout_frontpic from '$lib/assets/aalborgtryout_frontpic.png';
    import {Dropdown, DropdownItem, DropdownMenu, DropdownToggle, Styles } from '@sveltestrap/sveltestrap'; // items from 'sveltestrap' package - will need to download with "npm install @sveltestrap/sveltestrap" (think thats the one)
    import activity1 from '$lib/assets/activity1.avif'; // adjust extension/name
    import activity2 from '$lib/assets/activity2.avif';
    import activity3 from '$lib/assets/activity3.avif';
    import activity4 from '$lib/assets/activity4.avif';
    import activity5 from '$lib/assets/activity5.avif';
    import activity6 from '$lib/assets/activity6.avif';
    import activity7 from '$lib/assets/activity7.avif';
    import activity8 from '$lib/assets/activity8.avif';
    import activity9 from '$lib/assets/activity9.avif';
    import activity10 from '$lib/assets/activity10.avif';
</script>

<div class="homepage">
    <div class="frontPictureContainer">
        <div>
            <img class="frontPicture" src={aalborgtryout_frontpic} alt="Aalborg Try Out front">
        </div>
    </div>


    <div class="activityMenu">
        <h1>Aalborg Try Out aktiviteter</h1>

        <div class="searchAndFilters">
            <input class="searchbar" type="text" placeholder="Search activities..." />
            
            <div class="column">
                <div class="dropdownContainer dropdownBordered">
                    <div class="dropdownToggle clickDropdown"> 
                        Dropdown menu
                    </div>
                    <div class="dropdownMenu">
                        <ul>
                            <li>Filter 1</li>
                            <li>Filter 2</li>
                            <li>Filter 3</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
</div>

<!-- LIST OF ACTIVITIES-->
<!--Row 1-->
<div class="ActivityList">
    <div class="b">
        <img class="img" src={activity1} alt="Activity 1">
        <h3>Juleklip for Mænd</h3>
        <h5>Mandehørm</h5>
        <p>🗓️ torsdag, 11. dec. kl. 12.00-17.00</p>
        <p>🎂 15+</p>
    </div>
    <div class="b">
        <img class="img" src={activity2} alt="Activity 2">
        <h3>Vinter Natteløb</h3>
        <h5>Speedy Bambino</h5>
        <p>🗓️ onsdag, 19. nov. kl. 20.00-23.00</p>
        <p>🎂 16+</p>
    </div>
    <div class="b">
        <img class="img" src={activity3} alt="Activity 3">
        <h3>Oplæsning af Ringenes Herrer</h3>
        <h5>Aalborg Bibliotek</h5>
        <p>🗓️ onsdag, 19. nov. kl. 14.00-16.00</p>
        <p>🎂 14+</p>
    </div>
    <div class="b">
        <img class="img" src={activity4} alt="Activity 4">
        <h3>Amatør Blomsterbinding</h3>
        <h5>Interflora</h5>
        <p>🗓️ torsdag, 27. nov. kl. 16.00-19.00</p>
        <p>🎂 17+</p>
    </div>
    <div class="b">
        <img class="img" src={activity5} alt="Activity 5">
        <h3>Name of Activity</h3>
        <h5>Activity Holder</h5>
        <p>Date information</p>
        <p>Age</p>
    </div>

<!--Row 2-->
    <div class="b">
        <img class="img" src={activity6} alt="Activity 6">
        <h3>Name of Activity</h3>
        <h5>Activity Holder</h5>
        <p>Date information</p>
        <p>Age</p>
    </div>
    <div class="b">
        <img class="img" src={activity7} alt="Activity 7">
        <h3>Name of Activity</h3>
        <h5>Activity Holder</h5>
        <p>Date information</p>
        <p>Age</p>
    </div>
    <div class="b">
        <img class="img" src={activity8} alt="Activity 8">
        <h3>Name of Activity</h3>
        <h5>Activity Holder</h5>
        <p>Date information</p>
        <p>Age</p>
    </div>
    <div class="b">
        <img class="img" src={activity9} alt="Activity 9">
        <h3>Name of Activity</h3>
        <h5>Activity Holder</h5>
        <p>Date information</p>
        <p>Age</p>
    </div>
    <div class="b">
        <img class="img" src={activity10} alt="Activity 10">
        <h3>Name of Activity</h3>
        <h5>Activity Holder</h5>
        <p>Date information</p>
        <p>Age</p>
    </div>
</div>
</div>

<!-- CSS STYLE -->
<style>
    .frontPicture{
        height: 300px;
        display: block; 
    }

    .frontPictureContainer {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 1rem;
    }
    .searchAndFilters {  
        padding-top: 30px;
        padding-bottom: 30px; 
        display: flex;
    }

    .homepage {
        padding-left: 50px;
        padding-right: 50px;
        padding-top: 30px; 
        align-items: center;
    }

    .searchbar {
        flex: 1;
        padding: 10px;
        font-size: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
        margin-right: 20px; 
    }
    
    /* font stack applied globally */
    :global(body) {
        font-family: Inter, ui-sans-serif, system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
        margin: 0;
        padding: 0;
    }

/* Dropdown menu CSS start*/
    .column {
    max-width: 100%;
    width: 100%;
    padding: 0 15px;
     flex: 0 0 100%;          
    }

    .dropdownContainer {
      margin: 0 auto;
      margin-bottom: 20px;
    }

    @media (min-width: 767px) {
      flex: 0 0 50%;
      max-width: 50%;
    }

    @media (min-width: 992px) {
      flex: 0 0 25%;
      max-width: 25%;
    }



/* Dropdown menu CSS slut*/

    .b {
        border-radius: 16px;
        display: inline-block;
        position: relative;
        margin: 1%;
        float: left;
        width: 18%;
        min-height: 420px;
        background-color: #6E479B;
        color: #ffffff; /* make text inside the card white */
        padding: 1rem;
        box-sizing: border-box;
    }

    .b .img {
        border-radius: 12px;
        width: 100%;
        /* enforce a consistent image height so all cards look the same */
        height: 220px;
        display: block;
        margin-bottom: 0.75rem;
        object-fit: cover; /* crop/scale image to fill the box */
    }

    .b h3,
    .b h5,
    .b p {
        color: #ffffff;
        margin: 0.25rem 0;
        font-weight: 500;
    }

    .b h3 { font-size: 1.05rem; }
    .b h5 { font-size: 0.9rem; opacity: 0.95; }

/* hover highlight; add a small lift and shadow for emphasis */
    .b {
        transition: background-color 180ms ease, transform 140ms ease, box-shadow 180ms ease;
    }
    .b:hover,
    .b:focus-within {
        background-color: #5e3b85ff;
        transform: translateY(-6px);
        box-shadow: 0 8px 20px rgba(0,0,0,0.18);
        cursor: pointer;
    }

    @media (max-width: 800px) {
        .b { width: 48%; }
    }
    @media (max-width: 480px) {
        .b { width: 100%; float: none; transform: none; }
    }
</style>