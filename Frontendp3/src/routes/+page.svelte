<script lang="ts">
  import { onMount, onDestroy } from "svelte";
  import {
    // Dropdown,
    // DropdownToggle,
    // DropdownMenu,
    // DropdownItem,
    Styles,
    // NavItem,
  } from "@sveltestrap/sveltestrap"; // need to install @sveltestrap/sveltestrap via "npm install @sveltestrap/sveltestrap"
  import { writable } from "svelte/store";
  // import { get, type Writable } from 'svelte/store'; // for reusable toggleItem + reusable checkbox dropdown
  // import Select from "svelte-select"; // only version 1

  import CheckboxDropdown from "$lib/CheckboxDropdown.svelte";
  import closeDropdownOnClickOutside from "$lib/CheckboxDropdown.svelte";
  import { locations, weekdays, ages, genders, tags } from "./stores";
  // import { updated } from "$app/state";
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

  // new handleSubmit function
  function handleSubmit() {
    const selectedLocations = $locations.filter((i) => i.checked);
    console.log("Locations:", selectedLocations);

    const selectedWeekdays = $weekdays.filter((i) => i.checked);
    console.log("Weekdays:", selectedWeekdays);

    const selectedAges = $ages.filter((i) => i.checked);
    console.log("Ages:", selectedAges);

    const selectedGenders = $genders.filter((i) => i.checked);
    console.log("Genders:", selectedGenders);

    const selectedTags = $tags.filter((i) => i.checked);
    console.log("Tags:", selectedTags);
  }

  // every dropdown is a writable store (which lets us use a reusable toggleItem)
</script>

<head>
  <!-- where do we use bootstrap? -->
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
  />
</head>

<div class="ActivityList">
  <h1>Alle Aktivititer</h1>


  <div class="filtersRow">
    <!-- <input class="searchbar" type="text" placeholder="Search activities..." /> -->
    <Styles />
    <form on:submit|preventDefault={handleSubmit}>
      <div class="filters">
        <CheckboxDropdown store={locations} label="Lokation" />
        <CheckboxDropdown store={weekdays} label="Ugedage" />
        <CheckboxDropdown store={ages} label="Alder" />
        <CheckboxDropdown store={genders} label="Køn" />
        <CheckboxDropdown store={tags} label="Tags" />

        <button type="submit" class="submit-btn">Vis filtrerede</button>
      </div>
    </form>
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
  .filtersRow {
    padding-top: 30px;
    padding-bottom: 30px;
    display: flex;
  }

  .filters {
    display: flex;
    gap: 1rem; /* optional spacing */
    align-items: center;
    flex-wrap: wrap; /* optional, allows wrapping on small screens */
  }

  .submit-btn {
    /* padding: 0.5rem 1rem; */
    margin-left: auto;
    background: #6e479b;
    color: white;
    border: 0.2rem solid #6e479b;
  }
  .submit-btn:hover {
      background: white;
      border: 0.2rem solid #6e479b;
      color: #6e479b;
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
</style>
