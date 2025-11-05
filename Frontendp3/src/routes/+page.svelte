<head>
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
  />
</head>

<script>
    import aalborgtryout_frontpic from '$lib/assets/aalborgtryout_frontpic.png';
    import {Dropdown, DropdownItem, DropdownMenu, DropdownToggle, Styles } from '@sveltestrap/sveltestrap'; // items from 'sveltestrap' package - will need to download with "npm install sveltestrap" (think thats the one)
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
</style>