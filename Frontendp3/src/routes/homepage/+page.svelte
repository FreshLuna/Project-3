<script lang="ts">
  import { onMount } from "svelte";
  import { Styles } from "@sveltestrap/sveltestrap";

  import CheckboxDropdown from "$lib/CheckboxDropdown.svelte";
  import { locations, weekdays, ages, genders, tags } from "../filterStores";

  import { writable, get } from "svelte/store";
  import { selectedTags } from "$lib/selectedTags";
  import { loadActivities } from "$lib/utils/LoadActivities";
  import type { Activity } from "$lib/types/Activities";

  // activities store
  export const activities = writable<Activity[]>([]);

  // Load activities from backend
  onMount(async () => {
    try {
      const loaded = await loadActivities(); // returns Activity[]
      activities.set(loaded);
    } catch (e) {
      console.error("Failed to load activities", e);
    }
  });

  // FILTER SUBMIT
  function handleSubmit(event: any) {
    event.preventDefault();

    const selectedLocations = $locations.filter((i) => i.checked);
    const selectedWeekdays = $weekdays.filter((i) => i.checked);
    const selectedAges = $ages.filter((i) => i.checked);
    const selectedGenders = $genders.filter((i) => i.checked);
    const selectedTags = $tags.filter((i) => i.checked);

    console.log("Locations:", selectedLocations);
    console.log("Weekdays:", selectedWeekdays);
    console.log("Ages:", selectedAges);
    console.log("Genders:", selectedGenders);
    console.log("Tags:", selectedTags);
  }

  // Server-side filtering
  async function showFiltered() {
    const tags = get(selectedTags);

    const res = await fetch("https://localhost:8443/server/activities/filter", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ selectedTags: tags })
    });

    const data = await res.json();
    activities.set(data);
  }
</script>

<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" />
</head>

<div class="ActivityList">
  <h1>Alle Aktivititer</h1>

  <div class="filtersRow">
    <Styles />
    <form onsubmit={handleSubmit}>
      <div class="filters">
        <CheckboxDropdown store={locations} label="Lokation" />
        <CheckboxDropdown store={weekdays} label="Ugedage" />
        <CheckboxDropdown store={ages} label="Alder" />
        <CheckboxDropdown store={genders} label="Køn" />
        <CheckboxDropdown store={tags} label="Tags" />

        <button type="submit" class="submit-btn" onclick={showFiltered}>
          Vis filtrerede
        </button>
      </div>
    </form>
  </div>
</div>

<!-- ACTIVITIES -->
<div>
  <section class="dynamicActivities">
    {#if $activities.length === 0}
      <p>Loading dynamic activities...</p>
    {:else}
      <div class="ActivityList">
        {#each $activities as a}
          <a href={`/${a.id}`} class="activity-link">
           <div class="b">
              {#if a.imgUrl}
                <img class="img" src={a.imgUrl} alt={a.title} />
              {:else}
               <div class="img" style="background:#ddd;border-radius:12px;height:220px;margin-bottom:.75rem;"></div>
              {/if}

              <h3>{a.title}</h3>
              <h5>{a.organization}</h5>
              <p>🗓️{a.formattedDate}</p>
              <p>🎂{a.age}</p>
            </div>
          </a>
        {/each}
      </div>
    {/if}
  </section>
</div>

<style>
  .filtersRow {
    padding-top: 30px;
    padding-bottom: 30px;
    display: flex;
  }

  .filters {
    display: flex;
    gap: 1rem;
    align-items: center;
    flex-wrap: wrap;
  }

  .submit-btn {
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

  /* global font is handled by src/app.css */

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
    transition: background-color 180ms ease, transform 140ms ease,
      box-shadow 180ms ease;
  }

  .b .img {
    border-radius: 12px;
    width: 100%;
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
