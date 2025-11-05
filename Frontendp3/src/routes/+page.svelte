<head>
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
  />
</head>

<script>
    import aalborgtryout_frontpic from '$lib/assets/aalborgtryout_frontpic.png';
    import {Dropdown, DropdownItem, DropdownMenu, DropdownToggle, Styles } from '@sveltestrap/sveltestrap'; // items from 'sveltestrap' package - will need to download with "npm install sveltestrap" (think thats the one)
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

<div class="FrontPictureContainer">
    <div>
        <img class="FrontPicture" src={aalborgtryout_frontpic} alt="Aalborg Try Out front">
    </div>
</div>

<div class="ActivityList">
    <h1>Aalborg Try Out aktiviteter</h1>

    <div class="SearchAndFilters">
        <input type="text" placeholder="Search activities..." />

        <!-- SvelteKit dropdown submenu https://svelte.dev/playground/28dc6f65caf543d8934790ac1280b626?version=5.43.3 -->
        <Styles />
            <!-- Gender/Age drop down menu -->
            <Dropdown>
	            <DropdownToggle caret>Gender/Age</DropdownToggle>
                <DropdownMenu>
                    <Dropdown direction="down">
			            <DropdownToggle caret class="dropdown-item">
				            🧍🏻 Gender
			            </DropdownToggle>
			            <DropdownMenu>
                            <DropdownItem on:click={() => alert('All')}>All</DropdownItem>
				            <DropdownItem on:click={() => alert('Boys/Men')}>Boys/Men</DropdownItem>
				            <DropdownItem on:click={() => alert('Only for Boys/Men')}>Only for Boys/Men</DropdownItem>
				            <DropdownItem on:click={() => alert('Girls/Women')}>Girls/Women</DropdownItem>
                            <DropdownItem on:click={() => alert('Only for Girls/Women')}>Only for Girls/Women</DropdownItem>
			            </DropdownMenu>
		            </Dropdown>
                    <DropdownItem divider />
		            <Dropdown direction="down">
			            <DropdownToggle caret class="dropdown-item">
				            🎂 Age
			            </DropdownToggle>
			            <DropdownMenu>
				            <DropdownItem on:click={() => alert('Age')}>Age</DropdownItem>
				            <DropdownItem on:click={() => alert('0-5')}>0-5</DropdownItem>
				            <DropdownItem on:click={() => alert('6-10')}>6-10</DropdownItem>
			            </DropdownMenu>
		            </Dropdown>
                </DropdownMenu>
            </Dropdown>

            <!-- Category/Provider drop down menu -->
            <Dropdown>
	            <DropdownToggle caret>Category/Provider</DropdownToggle>
                <DropdownMenu>
                    <Dropdown direction="down">
			            <DropdownToggle caret class="dropdown-item">
				            Categories
			            </DropdownToggle>
			            <DropdownMenu>
                            <DropdownItem on:click={() => alert('Carate')}>Carate</DropdownItem>
                            <DropdownItem on:click={() => alert('Taekwondo')}>Taekwondo</DropdownItem>
			            </DropdownMenu>
		            </Dropdown>
                    <DropdownItem divider />
		            <Dropdown direction="down">
			            <DropdownToggle caret class="dropdown-item">
				            Providers
			            </DropdownToggle>
			            <DropdownMenu>
				            <DropdownItem on:click={() => alert('Aalborg Try Out')}>Aalborg Try Out</DropdownItem>
			            </DropdownMenu>
		            </Dropdown>
                </DropdownMenu>
            </Dropdown>
    </div>
</div>
<!--Row 1-->
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

<style>
    .FrontPicture{
        height: 300px;
        display: block; 
    }

    .FrontPictureContainer {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 1rem;
    }
    .SearchAndFilters {  
        padding-top: 30px; 
        display: flex;
    }

    .ActivityList {
        padding-left: 200px;
        padding-right: 200px;
        padding-top: 30px; 
        align-items: center;
    }

    /* font stack applied globally */
:global(body) {
    font-family: Inter, ui-sans-serif, system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
    margin: 0;
    padding: 0;
}

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