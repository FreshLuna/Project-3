<script lang="ts">
  import { onMount } from "svelte";
  import {
    Dropdown,
    DropdownToggle,
    DropdownMenu,
    DropdownItem,
    Styles,
    NavItem,
  } from "@sveltestrap/sveltestrap"; // need to install @sveltestrap/sveltestrap via "npm install @sveltestrap/sveltestrap"
  import { writable } from "svelte/store";
  import type { Writable } from 'svelte/store'; // for universal toggleItem
  import Select from "svelte-select"; // only version 1
  import { updated } from "$app/state";
  // import { error } from "@sveltejs/kit";

  // test server request that gets a list of users
  //onMount(async () => {
  //const res = await fetch("https://localhost:8443/server");
  //const text = await res.text();
  //console.log("RAW:", text);
  //console.log("hej")
  //fetch("https://localhost:8443/server/users")
  //.then(response => response.json())
  //.then(data => console.log(data));
  //})

  // maybe not needed? we don"t use it anyway
  // -------- Dynamic activities (runtime fetch from static/activities.txt) --------
  interface activity {
    imgFile: string;
    imgUrl: string | null;
    title: string;
    organization: string;
    date: string;
    time: string;
    age: string;
  }
  //let activities: activity[] = [];
  export const activities = writable<activity[]>([]);
  /* Build a lookup table of image imgFile -> resolved URL.
    We use Vite"s import.meta.glob with { eager: true } so the modules
    are imported at build/SSR time and `mod.default` contains the final URL.
    Example: "../lib/assets/activity1.avif" -> { default: "/_app/immutable/..../activity1.abcd.avif" }*/
  // const imagesGlob = import.meta.glob("../lib/assets/*.avif", { eager: true });

  // Convert the glob result into a simple map: { "activity1.avif": "/url/to/activity1.avif", ... }
  // const imageMap = Object.fromEntries(
  //   // @ts-ignore
  //   Object.entries(imagesGlob).map(([path, mod]) => [
  //     path.split("/").pop(),
  //     mod.default, // what is mod???
  //   ])
  // );

  /*
   * Load activities from the static text file at /activities.txt
   * Expected file format (one activity per line):
   *   imageimgFile|Activityname|Organization|Date|Age
   * Example:
   *   activity1.avif|Summer Camp|John Doe|tirsdag, 11. nov.|18+
   *
   * This function fetches the file, splits it into lines, trims empty lines,
   * and converts each line into an object used by the UI. If an image imgFile
   * from the file matches a key in imageMap, we attach the resolved URL to imgUrl;
   * otherwise imgUrl is null and the UI will show a placeholder.
   */
  async function loadActivities() {
    try {
      const res = await fetch("https://localhost:8443/server/activities");
      console.log("JSON:", res);

      if (!res.ok) throw new Error("Could not fetch /activities.txt");

      const data = await res.json();

      const mappedActivities: activity[] = data.map((item: activity) => ({
        imgFile: item.imgFile ?? "",
        imgUrl: item.imgUrl ?? null, // only displaying image link
        title: item.title ?? "",
        organization: item.organization ?? "",
        date: item.date ?? "",
        time: item.time ?? "",
        age: item.age ?? "",
      }));

      activities.set(mappedActivities);
    } catch (err) {
      console.error("Error loading activities:", err);
    }
  }

  // Run the loader when the component mounts in the browser
  onMount(loadActivities);

  // old attempt at universal toggleItem (doesn't work, no checkbox retention)
  // function toggleItem(id:any , items: any) {
  //   items = items.map((item: any) =>
  //     item.id === id ? { ...item, checked: !item.checked } : item // rewrite to actual if because ? : is undreadable
  //   );
  // }

  // attempt 2
  export function toggleItem(
    id: number,
    store: Writable<{ id: number; checked: boolean}[]> // what is this?
  ) {
    store.update(list => // what happens here?
      list.map(item =>
        item.id === id
          ? {... item, checked: !item.checked }
          : item
      )
    );
  }

  function handleSubmit(items: any) {
    const selected = items.filter((i: any) => i.checked);
    console.log(selected);
  }

  // every dropdown is a writable store (which lets us use a universal toggleItem)

  // location dropdown script: Aalborg Centrum, Aalborg Øst, Hasseris, Skalborg, Gug, Aalborg Vestby
  let locationsOpen = false;

  export const locations = writable([
    { id: 1, label: "Aalborg Centrum", checked: false },
    { id: 2, label: "Aalborg Øst", checked: false },
    { id: 3, label: "Hasseris", checked: false },
    { id: 4, label: "Skalborg", checked: false },
    { id: 5, label: "Gug", checked: false },
    { id: 6, label: "Aalborg Vestby", checked: false },
  ]);

  // date (day) dropdown script: Mandag, Tirsdag, Onsdag, Torsdag, Fredag, Lørdag, Søndag
  let weekdayOpen = false;

  export const weekdays = writable([
    { id: 1, label: "Mandag", checked: false },
    { id: 2, label: "Tirsdag", checked: false },
    { id: 3, label: "Onsdag", checked: false },
    { id: 4, label: "Torsdag", checked: false },
    { id: 5, label: "Fredag", checked: false },
    { id: 6, label: "Lørdag", checked: false },
    { id: 7, label: "Søndag", checked: false }
  ]);

  // age dropdown script: 0+, 12+, 15+, 18+, 21+, 25+, 30+ 
  let ageOpen = false;

  export const ages = writable([
    { id: 1, label: "0+", checked: false },
    { id: 2, label: "12+", checked: false },
    { id: 3, label: "15+", checked: false },
    { id: 4, label: "18+", checked: false },
    { id: 5, label: "21+", checked: false },
    { id: 6, label: "25+", checked: false },
    { id: 7, label: "30+", checked: false }
  ]);

  // gender dropdown script: 
  let genderOpen = false;

  export const genders = writable([
    { id: 1, label: "Alle", checked: false },
    { id: 2, label: "Drenge/mænd", checked: false },
    { id: 3, label: "Piger/kvinder", checked: false }
  ]);

  // tags dropdown script:
  let tagOpen = false;

  export const tags = writable([
    { id: 1, label: "Kampsport", checked: false },
    { id: 2, label: "Vand", checked: false },
    { id: 3, label: "Ketchersport", checked: false }
  ]);

</script>

<head>
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
  />
</head>

<div class="ActivityList">
  <h1>Aalborg Try Out aktiviteter</h1>

  <div class="searchAndFilters">
    <!-- <input class="searchbar" type="text" placeholder="Search activities..." /> -->
    <Styles />
    <form on:submit|preventDefault={handleSubmit}>
      <div class="filters">

        <!-- dropdown: location - Aalborg Centrum, Aalborg Øst, Hasseris, Skalborg, Gug, Aalborg Vestby -->
        <div class="dropdown">
          <button type="button" on:click={() => (locationsOpen = !locationsOpen)}>
            Vælg lokation
          </button>

          {#if locationsOpen}
            <div class="menu">
              {#each locations as item}
                <label>
                  <input
                    type="checkbox"
                    checked={item.checked}
                    on:change={() => toggleItem(item.id, locations)}
                  />
                  {item.label}
                </label>
              {/each}
            </div>
          {/if}
        </div>

        <!-- dropdown + checkbox: weekdays - Mandag, Tirsdag, Onsdag, Torsdag, Fredag, Lørdag, Søndag -->
        <div class="dropdown">
          <button type="button" on:click={() => (weekdayOpen = !weekdayOpen)}>
            Vælg ugedag
          </button>

          {#if weekdayOpen}
            <div class="menu">
              {#each weekdays as item}
                <label>
                  <input
                    type="checkbox"
                    checked={item.checked}
                    on:change={() => toggleItem(item.id, weekdays)}
                  />
                  {item.label}
                </label>
              {/each}
            </div>
          {/if}
        </div>
        
        <!-- dropdown: age - 0+, 12+, 15+, 18+, 21+, 25+, 30+ -->
        <div class="dropdown">
          <button type="button" on:click={() => (ageOpen = !ageOpen)}>
            Vælg alder
          </button>

          {#if ageOpen}
            <div class="menu">
              {#each ages as item}
                <label>
                  <input
                    type="checkbox"
                    checked={item.checked}
                    on:change={() => toggleItem(item.id, ages)}
                  />
                  {item.label}
                </label>
              {/each}
            </div>
          {/if}
        </div>
        
        <!-- dropdown: gender - Alle, Drenge/mænd, Piger/kvinder -->
        <div class="dropdown">
          <button type="button" on:click={() => (genderOpen = !genderOpen)}>
            Vælg køn
          </button>

          {#if genderOpen}
            <div class="menu">
              {#each genders as item}
                <label>
                  <input
                    type="checkbox"
                    checked={item.checked}
                    on:change={() => toggleItem(item.id, genders)}
                  />
                  {item.label}
                </label>
              {/each}
            </div>
          {/if}
        </div>

        <!-- dropdown: tags -  -->
        <div class="dropdown">
          <button type="button" on:click={() => (tagOpen = !tagOpen)}>
            Vælg tags
          </button>

          {#if tagOpen}
            <div class="menu">
              {#each tags as item}
                <label>
                  <input
                    type="checkbox"
                    checked={item.checked}
                    on:change={() => toggleItem(item.id, tags)}
                  />
                  {item.label}
                </label>
              {/each}
            </div>
          {/if}
        </div>

      </div>

      <button type="submit">Submit</button>
    </form>

    <div>
      <form action="/action_page.php">
        <input
          type="checkbox"
          id="Solsikketilbud"
          name="Solsikketilbud"
          value="Solsikketilbud"
        />
        <label for="javascript">Solsikketilbud</label>
      </form>
    </div>
  </div>
</div>

<div>
  <!-- DYNAMIC ACTIVITIES -->
  <section class="dynamicActivities">
    {#if $activities.length === 0}
      <p>Loading dynamic activities...</p>
    {:else}
      <div class="ActivityList">
        {#each $activities as a}
          <div class="b">
            {#if a.imgUrl}
              <img class="img" src={a.imgUrl} alt={a.title} />
            {:else}
              <div
                class="img"
                style="background:#ddd;border-radius:12px;height:220px;margin-bottom:.75rem;"
              ></div>
            {/if}
            <h3>{a.title}</h3>
            <h5>{a.organization}</h5>
            <p>🗓️{a.date} {a.time}</p>
            <p>🎂{a.age}</p>
          </div>
        {/each}
      </div>
    {/if}
  </section>
</div>

<!-- CSS STYLE -->
<style>
  .searchAndFilters {
    padding-top: 30px;
    padding-bottom: 30px;
    display: flex;
  }

  .searchbar {
    flex: 1;
    padding: 10px;
    font-size: 15px;
    border: 1px solid #ccc;
    border-radius: 4px;
    margin-right: 20px;
    margin-left: 20px;
    max-width: 300px;
  }

  /* font stack applied globally */
  :global(body) {
    font-family:
      Inter,
      ui-sans-serif,
      system-ui,
      -apple-system,
      "Segoe UI",
      Roboto,
      "Helvetica Neue",
      Arial,
      sans-serif;
    margin: 0;
    padding: 0;
  }

  .b {
    border-radius: 16px;
    position: relative;
    margin: 1%;
    float: left;
    width: 18%;
    min-height: 420px;
    background-color: #6e479b;
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

  .b h3 {
    font-size: 1.05rem;
  }
  .b h5 {
    font-size: 0.9rem;
    opacity: 0.95;
  }

  /* hover highlight; add a small lift and shadow for emphasis */
  .b {
    transition:
      background-color 180ms ease,
      transform 140ms ease,
      box-shadow 180ms ease;
  }
  .b:hover,
  .b:focus-within {
    background-color: #5e3b85ff;
    transform: translateY(-6px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.18);
    cursor: pointer;
  }

  @media (max-width: 800px) {
    .b {
      width: 48%;
    }
  }
  @media (max-width: 480px) {
    .b {
      width: 100%;
      float: none;
      transform: none;
    }
  }

  .dropdown {
    position: relative;
    display: inline-block;
  }
  .menu {
    position: absolute;
    background: white;
    border: 1px solid #ccc;
    padding: 0.5rem;
    z-index: 10;
  }
</style>
